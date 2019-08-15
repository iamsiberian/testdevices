package net.thumbtack.testdevices.web.converters;

import net.thumbtack.testdevices.core.models.Device;
import net.thumbtack.testdevices.core.models.DeviceType;
import net.thumbtack.testdevices.dto.request.DeviceRequest;
import net.thumbtack.testdevices.dto.response.DeviceResponse;

public class DeviceDtoToModelConverter {
    public DeviceResponse getDeviceResponseFromDevice(final Device device) {
        return new DeviceResponse(
                device.getId(),
                device.getType(),
                device.getOwner(),
                device.getModel(),
                device.getOsType(),
                device.getDescription()
        );
    }

    public Device getDeviceFromDeviceRequest(final DeviceRequest request) {
        return new Device(
                DeviceType.valueOf(request.getType()),
                request.getOwner(),
                request.getModel(),
                request.getOsType(),
                request.getDescription()
        );
    }
}
