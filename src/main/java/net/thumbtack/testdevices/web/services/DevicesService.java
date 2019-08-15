package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.dto.request.DeviceRequest;
import net.thumbtack.testdevices.dto.response.DeviceResponse;

public interface DevicesService {
    DeviceResponse addDevice(DeviceRequest request);
}
