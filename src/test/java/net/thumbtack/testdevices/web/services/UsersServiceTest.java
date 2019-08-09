package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.core.models.Authority;
import net.thumbtack.testdevices.core.models.AuthorityType;
import net.thumbtack.testdevices.core.models.User;
import net.thumbtack.testdevices.core.repositories.AuthoritiesDao;
import net.thumbtack.testdevices.core.repositories.UsersDao;
import net.thumbtack.testdevices.dto.request.UserRequest;
import net.thumbtack.testdevices.dto.response.UserResponse;
import net.thumbtack.testdevices.exceptions.ErrorCode;
import net.thumbtack.testdevices.exceptions.TestDevicesException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.*;

public class UsersServiceTest {
    private AuthoritiesDao mockAuthoritiesDao;
    private UsersDao mockUsersDao;
    private UsersServiceImpl usersService;
    private UsersServiceImpl spyUsersService;
    private User user;
    private UserRequest request;
    private UserResponse response;
    private Authority authority;

    @Before
    public void setup() {
        mockAuthoritiesDao = mock(AuthoritiesDao.class);
        mockUsersDao = mock(UsersDao.class);
        usersService = new UsersServiceImpl(mockAuthoritiesDao, mockUsersDao);
        spyUsersService = spy(usersService);
        user = new User(
                2L,
                "Vasiliy",
                "Pupkin",
                "+79923456789",
                "vasiliy.pupkin@mail.com",
                "123456"
        );
        request = new UserRequest(
                "Vasiliy",
                "Pupkin",
                "+79923456789",
                "vasiliy.pupkin@mail.com",
                "123456"
        );
        authority = new Authority(
                1L,
                AuthorityType.USER
        );
        response = mock(UserResponse.class);
    }

    @Test
    public void testUserRegistration() {
        doReturn(response).when(spyUsersService).registration(request, AuthorityType.USER);
        spyUsersService.userRegistration(request);
        verify(spyUsersService, times(1)).registration(request, AuthorityType.USER);
    }

    @Test
    public void testAdministratorRegistration() {
        doReturn(response).when(spyUsersService).registration(request, AuthorityType.ADMINISTRATOR);
        spyUsersService.administratorRegistration(request);
        verify(spyUsersService, times(1)).registration(request, AuthorityType.ADMINISTRATOR);
    }

    @Test
    public void testRegistration() {
        when(mockAuthoritiesDao.getByName(anyString())).thenReturn(authority);
        when(mockUsersDao.insert(anyLong(), anyObject())).thenReturn(user);

        usersService.registration(request, AuthorityType.USER);

        verify(mockAuthoritiesDao, times(1)).getByName(AuthorityType.USER.getAuthorityType());
        verify(mockUsersDao, times(1)).insert(eq(1L), anyObject());
    }

    @Test
    public void testRegistrationWithOutAuthority() throws TestDevicesException {
        Throwable thrown = assertThrows(TestDevicesException.class, () -> {
            usersService.registration(request, AuthorityType.USER);
        });

        TestDevicesException thrownException = (TestDevicesException) thrown;
        List<ErrorCode> list = thrownException.getErrorCode();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(ErrorCode.INVALID_AUTHORITY, list.get(0));
    }

    @Test
    public void testGetUserFromUserRequest() {
        User expectedUser = new User(
                "Vasiliy",
                "Pupkin",
                "+79923456789",
                "vasiliy.pupkin@mail.com",
                "123456"
        );
        User resultUser = usersService.getUserFromUserRequest(request);
        Assert.assertEquals(expectedUser, resultUser);
    }

    @Test
    public void testGetUserResponseFromUser() {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        UserResponse resultUserResponse = usersService.getUserResponseFromUser(user);
        UserResponse expectedUserResponse = new UserResponse(
                2L,
                "Vasiliy",
                "Pupkin",
                "+79923456789",
                "vasiliy.pupkin@mail.com",
                authorities
        );
        Assert.assertEquals(expectedUserResponse, resultUserResponse);
    }
}
