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
import net.thumbtack.testdevices.web.converters.UserDtoToModelConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UsersServiceTest {
    private AuthoritiesDao mockAuthoritiesDao;
    private UsersDao mockUsersDao;
    private UserDtoToModelConverter mockUserDtoToModelConverter;
    private UsersServiceImpl usersService;
    private User user;
    private UserRequest request;
    private UserResponse response;
    private Authority authority;

    @Before
    public void setup() {
        mockAuthoritiesDao = mock(AuthoritiesDao.class);
        mockUsersDao = mock(UsersDao.class);
        mockUserDtoToModelConverter = mock(UserDtoToModelConverter.class);
        usersService = new UsersServiceImpl(mockAuthoritiesDao, mockUsersDao, mockUserDtoToModelConverter);
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
        when(mockAuthoritiesDao.getByName(anyString())).thenReturn(authority);
        when(mockUsersDao.insert(anyLong(), any(User.class))).thenReturn(user);
        when(mockUserDtoToModelConverter.getUserFromUserRequest(request)).thenReturn(user);
        when(mockUserDtoToModelConverter.getUserResponseFromUser(user)).thenReturn(response);

        usersService.userRegistration(request);

        verify(mockAuthoritiesDao, times(1)).getByName(AuthorityType.USER.getAuthorityType());
        verify(mockUsersDao, times(1)).insert(eq(1L), eq(user));
        verify(mockUserDtoToModelConverter, times(1)).getUserFromUserRequest(request);
        verify(mockUserDtoToModelConverter, times(1)).getUserResponseFromUser(user);
    }

    @Test
    public void testAdministratorRegistration() {
        when(mockAuthoritiesDao.getByName(anyString())).thenReturn(authority);
        when(mockUsersDao.insert(anyLong(), any(User.class))).thenReturn(user);
        when(mockUserDtoToModelConverter.getUserFromUserRequest(request)).thenReturn(user);
        when(mockUserDtoToModelConverter.getUserResponseFromUser(user)).thenReturn(response);

        usersService.administratorRegistration(request);

        verify(mockAuthoritiesDao, times(1)).getByName(AuthorityType.ADMINISTRATOR.getAuthorityType());
        verify(mockUsersDao, times(1)).insert(eq(1L), eq(user));
        verify(mockUserDtoToModelConverter, times(1)).getUserFromUserRequest(request);
        verify(mockUserDtoToModelConverter, times(1)).getUserResponseFromUser(user);
    }

    @Test
    public void testRegistrationWithOutAuthority() throws TestDevicesException {
        TestDevicesException thrown = assertThrows(TestDevicesException.class, () -> usersService.userRegistration(request));

        List<ErrorCode> list = thrown.getErrorCode();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(ErrorCode.INVALID_AUTHORITY, list.get(0));
    }
}
