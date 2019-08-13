package net.thumbtack.testdevices.core.repositories;

import net.thumbtack.testdevices.core.models.Device;

import java.util.List;

public interface DeviceDao {
    Device insert(Device device);

    List<Device> getAll();

    Device getById(long id);

    void deleteById(long id);

    void deleteAll();
}
