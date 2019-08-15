package net.thumbtack.testdevices.web.converters;

import net.thumbtack.testdevices.core.models.Authority;
import net.thumbtack.testdevices.core.models.AuthorityType;
import net.thumbtack.testdevices.core.models.User;
import net.thumbtack.testdevices.dto.request.UserRequest;
import net.thumbtack.testdevices.dto.response.UserResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class UserDtoToModelConverterTest {
    private UserDtoToModelConverter userDtoToModelConverter;
    private User user;
    private UserRequest request;
    private Authority authority;

    @Before
    public void setup() {
        userDtoToModelConverter = new UserDtoToModelConverter();

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
        User resultUser = userDtoToModelConverter.getUserFromUserRequest(request);
        Assert.assertEquals(expectedUser, resultUser);
    }

    @Test
    public void testGetUserResponseFromUser() {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        UserResponse resultUserResponse = userDtoToModelConverter.getUserResponseFromUser(user);
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
