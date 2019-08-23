package net.thumbtack.testdevices.web.converters;

import net.thumbtack.testdevices.core.models.DeviceWithLastUser;
import net.thumbtack.testdevices.dto.response.DeviceWithLastUserResponse;

import java.util.ArrayList;
import java.util.List;

public class DeviceWithLastUserDtoToModelConverter {
    private UserDtoToModelConverter userDtoToModelConverter;

    public DeviceWithLastUserDtoToModelConverter(final UserDtoToModelConverter userDtoToModelConverter) {
        this.userDtoToModelConverter = userDtoToModelConverter;
    }

    public List<DeviceWithLastUserResponse> getDeviceWithLastUserResponseListFromDeviceWithLastUserList(
            final List<DeviceWithLastUser> deviceWithLastUserList
    ) {
        List<DeviceWithLastUserResponse> deviceWithLastUserResponseList = new ArrayList<>();
        for (DeviceWithLastUser deviceWithLastUser : deviceWithLastUserList) {
            deviceWithLastUserResponseList.add(getDeviceWithLastUserResponseFromDeviceWithLastUser(deviceWithLastUser));
        }
        return deviceWithLastUserResponseList;
    }

    public DeviceWithLastUserResponse getDeviceWithLastUserResponseFromDeviceWithLastUser(
            final DeviceWithLastUser deviceWithLastUser
    ) {
        return new DeviceWithLastUserResponse(
                deviceWithLastUser.getId(),
                deviceWithLastUser.getType(),
                deviceWithLastUser.getOwner(),
                deviceWithLastUser.getModel(),
                deviceWithLastUser.getOsType(),
                deviceWithLastUser.getDescription(),
                deviceWithLastUser.getUser() == null ? null : userDtoToModelConverter.getUserResponseFromUser(deviceWithLastUser.getUser())
        );
    }
}
