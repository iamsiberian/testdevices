package net.thumbtack.testdevices.core.mappers;

import net.thumbtack.testdevices.core.models.Device;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeviceMapper {
    @Insert(
            "INSERT INTO devices (type, owner, model, os_type, description) \n" +
            "VALUES (#{device.type}, #{device.owner}, #{device.model}, #{device.osType}, #{device.description})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(@Param("device") Device device);

    @Select(
            "SELECT d.id, d.type, d.owner, d.model, d.os_type AS osType, d.description\n" +
            "FROM devices d"
    )
    List<Device> getAll();

    @Select(
            "SELECT d.id, d.type, d.owner, d.model, d.os_type AS osType, d.description\n" +
            "FROM devices d\n" +
            "WHERE d.id = #{id}"
    )
    Device getById(long id);

    @Delete(
            "DELETE FROM devices WHERE id = #{id}"
    )
    Integer deleteById(long id);

    @Delete(
            "DELETE FROM devices"
    )
    Integer deleteAll();
}
