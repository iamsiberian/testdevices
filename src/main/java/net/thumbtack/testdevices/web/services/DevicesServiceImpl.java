package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.core.models.Device;
import net.thumbtack.testdevices.core.models.DeviceWithLastUser;
import net.thumbtack.testdevices.core.repositories.DeviceDao;
import net.thumbtack.testdevices.dto.request.DeviceRequest;
import net.thumbtack.testdevices.dto.response.DeviceResponse;
import net.thumbtack.testdevices.dto.response.DeviceWithLastUserResponse;
import net.thumbtack.testdevices.web.converters.DeviceDtoToModelConverter;
import net.thumbtack.testdevices.web.converters.DeviceWithLastUserDtoToModelConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DevicesServiceImpl implements DevicesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevicesServiceImpl.class);
    private DeviceDao deviceDao;
    private DeviceDtoToModelConverter deviceDtoToModelConverter;
    private DeviceWithLastUserDtoToModelConverter deviceWithLastUserDtoToModelConverter;

    public DevicesServiceImpl(
            final DeviceDao deviceDao,
            final DeviceDtoToModelConverter deviceDtoToModelConverter,
            final DeviceWithLastUserDtoToModelConverter deviceWithLastUserDtoToModelConverter
    ) {
        this.deviceDao = deviceDao;
        this.deviceDtoToModelConverter = deviceDtoToModelConverter;
        this.deviceWithLastUserDtoToModelConverter = deviceWithLastUserDtoToModelConverter;
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

    @Override
    public List<DeviceResponse> getAll() {
        LOGGER.debug("DevicesServiceImpl getAll");
        List<Device> deviceList = deviceDao.getAll();
        return deviceDtoToModelConverter.getDeviceListResponseFromDeviceList(deviceList);
    }

    @Override
    public List<DeviceWithLastUserResponse> getDevicesWithLastUserWhoTakenDevice(final String search) {
        LOGGER.debug("DevicesServiceImpl getDevicesWithLastUserWhoTakenDevice, search: {}", search);
        List<DeviceWithLastUser> deviceWithLastUserList = deviceDao.getDevicesWithLastUserWhoTakenDevice(getSearch(search));
        return deviceWithLastUserDtoToModelConverter.getDeviceWithLastUserResponseListFromDeviceWithLastUserList(deviceWithLastUserList);
    }

    @Override
    public List<DeviceResponse> getMyDevices(final long userId) {
        LOGGER.debug("DevicesServiceImpl getMyDevices: by user id : {}", userId);
        List<Device> deviceList = deviceDao.getMyDevices(userId);
        return deviceDtoToModelConverter.getDeviceListResponseFromDeviceList(deviceList);
    }

    private String getSearch(final String search) {
        if (StringUtils.isEmpty(search)) {
            return null;
        } else {
            return  "%" + search + "%";
        }
    }
}
