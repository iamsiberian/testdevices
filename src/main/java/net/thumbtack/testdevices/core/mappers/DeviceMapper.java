package net.thumbtack.testdevices.core.mappers;

import net.thumbtack.testdevices.core.models.Device;
import net.thumbtack.testdevices.core.models.DeviceWithLastUser;
import net.thumbtack.testdevices.core.models.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

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

    @Select({
            "<script>",
            "SELECT d.id, d.type, d.owner, d.model, d.os_type AS osType, d.description\n" +
            "FROM devices d \n" +
            "where d.is_deleted = FALSE " +
                "<if test='search != null'> " +
                    "AND (LOWER(CONCAT_WS(' ', d.owner, d.model, d.os_type)) LIKE LOWER(#{search})) \n" +
                "</if>" +
            "ORDER BY d.type, d.owner, d.model, d.os_type " +
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(
                    property = "user", column = "id", javaType = User.class,
                    one = @One(
                            select = "net.thumbtack.testdevices.core.mappers.UserMapper.getLastUserWhoTakenDeviceByDeviceId",
                            fetchType = FetchType.EAGER
                    )
            )
    })
    List<DeviceWithLastUser> getDevicesWithLastUserWhoTakenDevice(@Param("search") String search);

    @Select(
            "SELECT d.id, d.type, d.owner, d.model, d.os_type AS osType, d.description\n" +
            "FROM devices d\n" +
            "WHERE d.id = #{id} " +
            "AND d.is_deleted = FALSE"
    )
    Device getById(long id);

    @Delete(
            "UPDATE devices SET is_deleted = TRUE WHERE id = #{id}"
    )
    Integer deleteById(long id);

    @Delete(
            "DELETE FROM devices"
    )
    Integer deleteAll();
}
