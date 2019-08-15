package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.dto.request.DeviceRequest;
import net.thumbtack.testdevices.dto.response.DeviceResponse;
import net.thumbtack.testdevices.dto.response.EmptyResponse;

public interface DevicesService {
    DeviceResponse addDevice(DeviceRequest request);

    EmptyResponse deleteDevice(long deviceId);
}
