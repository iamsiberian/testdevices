package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.dto.request.DeviceRequest;
import net.thumbtack.testdevices.dto.response.DeviceResponse;
import net.thumbtack.testdevices.dto.response.DeviceWithLastUserResponse;

import java.util.List;

public interface DevicesService {
    DeviceResponse addDevice(DeviceRequest request);

    void deleteDevice(long deviceId);

    List<DeviceResponse> getAll();

    List<DeviceWithLastUserResponse> getDevicesWithLastUserWhoTakenDevice(String search);
}
