package net.thumbtack.testdevices.core.repositories;

import net.thumbtack.testdevices.core.mappers.DeviceMapper;
import net.thumbtack.testdevices.core.models.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DeviceDaoImpl implements DeviceDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceDaoImpl.class);
    private DeviceMapper deviceMapper;

    public DeviceDaoImpl(final DeviceMapper deviceMapper) {
        this.deviceMapper = deviceMapper;
    }

    @Transactional(rollbackFor = DaoException.class)
    @Override
    public Device insert(final Device device) {
        LOGGER.debug("DeviceDaoImpl insert device: {}", device);
        deviceMapper.insert(device);
        return device;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Device> getAll() {
        LOGGER.debug("DeviceDaoImpl getAll");
        return deviceMapper.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Device getById(final long id) {
        LOGGER.debug("DeviceDaoImpl getById: {}", id);
        return deviceMapper.getById(id);
    }

    @Transactional(rollbackFor = DaoException.class)
    @Override
    public void deleteById(final long id) {
        LOGGER.debug("DeviceDaoImpl deleteById: {}", id);
        deviceMapper.deleteById(id);
    }

    @Transactional(rollbackFor = DaoException.class)
    @Override
    public void deleteAll() {
        LOGGER.debug("DeviceDaoImpl deleteAll devices");
        deviceMapper.deleteAll();
    }
}
