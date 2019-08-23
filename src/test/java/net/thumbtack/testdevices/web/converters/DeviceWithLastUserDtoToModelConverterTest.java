package net.thumbtack.testdevices.web.converters;

import net.thumbtack.testdevices.core.models.Authority;
import net.thumbtack.testdevices.core.models.AuthorityType;
import net.thumbtack.testdevices.core.models.DeviceType;
import net.thumbtack.testdevices.core.models.DeviceWithLastUser;
import net.thumbtack.testdevices.core.models.User;
import net.thumbtack.testdevices.dto.response.DeviceWithLastUserResponse;
import net.thumbtack.testdevices.dto.response.UserResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeviceWithLastUserDtoToModelConverterTest {
    private UserDtoToModelConverter userDtoToModelConverter;
    private DeviceWithLastUserDtoToModelConverter deviceWithLastUserDtoToModelConverter;
    private DeviceWithLastUser deviceWithLastUser;
    private DeviceWithLastUser deviceWithLastUser2;
    private DeviceWithLastUserResponse deviceWithLastUserResponse;
    private DeviceWithLastUserResponse deviceWithLastUserResponse2;
    private User user;
    private User user2;
    private UserResponse userResponse;
    private UserResponse userResponse2;
    private Authority authority;

    @Before
    public void setup() {
        userDtoToModelConverter = mock(UserDtoToModelConverter.class);
        deviceWithLastUserDtoToModelConverter = new DeviceWithLastUserDtoToModelConverter(userDtoToModelConverter);

        user = new User(
                2L,
                "Vasiliy",
                "Pupkin",
                "+79923456789",
                "vasiliy.pupkin@mail.com",
                "123456"
        );
        user2 = new User(
                2L,
                "Doe",
                "John",
                "+79923456789",
                "john.doe@mail.com"
        );
        authority = new Authority(
                1L,
                AuthorityType.USER
        );
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        user2.setAuthorities(authorities);

        userResponse = new UserResponse(
                2L,
                "Vasiliy",
                "Pupkin",
                "+79923456789",
                "vasiliy.pupkin@mail.com",
                authorities
        );
        userResponse2 = new UserResponse(
                2L,
                "Doe",
                "John",
                "+79923456789",
                "john.doe@mail.com",
                authorities
        );

        deviceWithLastUser = new DeviceWithLastUser(
                1L,
                DeviceType.PHONE,
                "Apple",
                "iPhone 1337",
                "iOS",
                "abracadabra",
                user
        );
        deviceWithLastUser2 = new DeviceWithLastUser(
                1L,
                DeviceType.TABLET_PC,
                "Huawei",
                "Df123",
                "Android",
                "abracadabra",
                user2
        );

        deviceWithLastUserResponse = new DeviceWithLastUserResponse(
                1L,
                DeviceType.PHONE,
                "Apple",
                "iPhone 1337",
                "iOS",
                "abracadabra",
                userResponse
        );
        deviceWithLastUserResponse2 = new DeviceWithLastUserResponse(
                1L,
                DeviceType.TABLET_PC,
                "Huawei",
                "Df123",
                "Android",
                "abracadabra",
                userResponse2
        );
    }

    @Test
    public void testGetDeviceWithLastUserResponseFromDeviceWithLastUser() {
        when(userDtoToModelConverter.getUserResponseFromUser(user)).thenReturn(userResponse);

        Assert.assertEquals(deviceWithLastUserResponse, deviceWithLastUserDtoToModelConverter.getDeviceWithLastUserResponseFromDeviceWithLastUser(deviceWithLastUser));

        verify(userDtoToModelConverter, times(1)).getUserResponseFromUser(eq(user));
    }

    @Test
    public void testGetDeviceWithLastUserResponseFromDeviceWithLastUser_WithNullUser() {
        when(userDtoToModelConverter.getUserResponseFromUser(user)).thenReturn(userResponse);
        deviceWithLastUser.setUser(null);
        deviceWithLastUserResponse.setUserResponse(null);

        Assert.assertEquals(deviceWithLastUserResponse, deviceWithLastUserDtoToModelConverter.getDeviceWithLastUserResponseFromDeviceWithLastUser(deviceWithLastUser));

        verify(userDtoToModelConverter, times(0)).getUserResponseFromUser(eq(user));
    }

    @Test
    public void testGetDeviceWithLastUserResponseListFromDeviceWithLastUserList() {
        List<DeviceWithLastUser> deviceWithLastUserList = new ArrayList<>();
        deviceWithLastUserList.add(deviceWithLastUser);
        deviceWithLastUserList.add(deviceWithLastUser2);

        when(userDtoToModelConverter.getUserResponseFromUser(any(User.class))).thenReturn(userResponse).thenReturn(userResponse2);

        List<DeviceWithLastUserResponse> deviceWithLastUserResponseList = deviceWithLastUserDtoToModelConverter.getDeviceWithLastUserResponseListFromDeviceWithLastUserList(
                deviceWithLastUserList
        );

        verify(userDtoToModelConverter, times(2)).getUserResponseFromUser(any(User.class));
        Assert.assertEquals(2, deviceWithLastUserResponseList.size());
        Assert.assertTrue(deviceWithLastUserResponseList.contains(deviceWithLastUserResponse));
        Assert.assertTrue(deviceWithLastUserResponseList.contains(deviceWithLastUserResponse2));
    }
}
