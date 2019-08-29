package net.thumbtack.testdevices.core.repositories;

import net.thumbtack.testdevices.core.models.Device;
import net.thumbtack.testdevices.core.models.DeviceWithLastUser;

import java.util.List;

public interface DeviceDao {
    Device insert(Device device);

    List<Device> getAll();

    List<DeviceWithLastUser> getDevicesWithLastUserWhoTakenDevice(String search);

    Device getById(long id);

    void deleteById(long id);

    void deleteAll();

    List<Device> getMyDevices(long userId);
}
