package net.thumbtack.testdevices.core.mappers;

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
import java.util.Set;

@Mapper
public interface UserMapper {
    @Insert(
            "INSERT INTO users (first_name, last_name, phone, email, password) \n" +
            "VALUES (#{user.firstName}, #{user.lastName}, #{user.phone}, #{user.email}, #{user.password})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insert(@Param("user") User user);

    @Select(
            "SELECT u.id, u.first_name AS firstName, u.last_name AS lastName, u.phone, u.email, u.password \n" +
            "FROM users u \n" +
            "WHERE u.email = #{email}"
    )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(
                    property = "authorities", column = "id", javaType = Set.class,
                    one = @One(select = "net.thumbtack.testdevices.core.mappers.AuthoritiesMapper.getByUserId", fetchType = FetchType.LAZY)
            )
    })
    User getByEMail(@Param("email") String email);

    @Select(
            "SELECT u.id, u.first_name AS firstName, u.last_name AS lastName, u.phone, u.email, u.password\n" +
            "FROM users u\n" +
            "WHERE (LOWER(CONCAT_WS(' ', last_name, first_name)) LIKE lower(?))"
    )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(
                    property = "authorities", column = "id", javaType = Set.class,
                    one = @One(select = "net.thumbtack.testdevices.core.mappers.AuthoritiesMapper.getByUserId", fetchType = FetchType.LAZY)
            )
    })
    List<User> getByName(@Param("userName") String userName);

    @Select(
            "SELECT u.id, u.first_name AS firstName, u.last_name AS lastName, u.phone, u.email, u.password\n" +
            "FROM users u\n" +
            "WHERE u.id = #{id}"
    )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(
                    property = "authorities", column = "id", javaType = Set.class,
                    one = @One(select = "net.thumbtack.testdevices.core.mappers.AuthoritiesMapper.getByUserId", fetchType = FetchType.LAZY)
            )
    })
    User getById(@Param("id") long id);

    @Select(
            "SELECT u.id, u.first_name AS firstName, u.last_name AS lastName, u.phone, u.email, u.password \n" +
            "FROM users u\n" +
            "    JOIN (\n" +
            "       SELECT e.id AS id, e.user_id AS userId, e.device_id AS deviceId, e.action_type AS actionType, e.date AS date\n" +
            "       FROM events e\n" +
            "       WHERE e.device_id = #{deviceId}\n" +
            "       ORDER BY e.date DESC\n" +
            "       LIMIT 1\n" +
            "    ) AS last_event ON u.id = last_event.userId\n" +
            "WHERE last_event.actionType = 'TAKE'"
    )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(
                    property = "authorities", column = "id", javaType = Set.class,
                    one = @One(select = "net.thumbtack.testdevices.core.mappers.AuthoritiesMapper.getByUserId", fetchType = FetchType.LAZY)
            )
    })
    User getLastUserWhoTakenDeviceByDeviceId(@Param("deviceId") long deviceId);

    @Select(
            "SELECT u.id, u.first_name AS firstName, u.last_name AS lastName, u.phone, u.email, u.password \n" +
            "FROM users u"
    )
    List<User> getAll();

    @Delete(
            "DELETE FROM users"
    )
    void deleteAll();
}
