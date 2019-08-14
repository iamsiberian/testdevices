package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.core.models.Device;
import net.thumbtack.testdevices.core.models.DeviceType;
import net.thumbtack.testdevices.core.repositories.DeviceDao;
import net.thumbtack.testdevices.dto.request.DeviceRequest;
import net.thumbtack.testdevices.dto.response.DeviceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DevicesServiceImpl implements DevicesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevicesServiceImpl.class);
    private DeviceDao deviceDao;

    public DevicesServiceImpl(final DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    @Override
    public DeviceResponse addDevice(final DeviceRequest request) {
        LOGGER.debug("DevicesServiceImpl addDevice: {}", request);
        Device device = getDeviceFromDeviceRequest(request);
        return getDeviceResponseFromDevice(deviceDao.insert(device));
    }

    DeviceResponse getDeviceResponseFromDevice(final Device device) {
        return new DeviceResponse(
                device.getId(),
                device.getType(),
                device.getOwner(),
                device.getModel(),
                device.getOsType(),
                device.getDescription()
        );
    }

    Device getDeviceFromDeviceRequest(final DeviceRequest request) {
        return new Device(
                DeviceType.valueOf(request.getType()),
                request.getOwner(),
                request.getModel(),
                request.getOsType(),
                request.getDescription()
        );
    }
}
