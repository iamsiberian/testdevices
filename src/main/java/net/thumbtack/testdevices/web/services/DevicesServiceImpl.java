package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.core.models.Device;
import net.thumbtack.testdevices.core.repositories.DeviceDao;
import net.thumbtack.testdevices.dto.request.DeviceRequest;
import net.thumbtack.testdevices.dto.response.DeviceResponse;
import net.thumbtack.testdevices.web.converters.DeviceDtoToModelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DevicesServiceImpl implements DevicesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevicesServiceImpl.class);
    private DeviceDao deviceDao;
    private DeviceDtoToModelConverter deviceDtoToModelConverter;

    public DevicesServiceImpl(
            final DeviceDao deviceDao,
            final DeviceDtoToModelConverter deviceDtoToModelConverter
    ) {
        this.deviceDao = deviceDao;
        this.deviceDtoToModelConverter = deviceDtoToModelConverter;
    }

    @Override
    public DeviceResponse addDevice(final DeviceRequest request) {
        LOGGER.debug("DevicesServiceImpl addDevice: {}", request);
        Device device = deviceDtoToModelConverter.getDeviceFromDeviceRequest(request);
        return deviceDtoToModelConverter.getDeviceResponseFromDevice(deviceDao.insert(device));
    }

    @Override
    public void deleteDevice(final long deviceId) {
        LOGGER.debug("DevicesServiceImpl deleteDevice: {}", deviceId);
        deviceDao.deleteById(deviceId);
    }
}
