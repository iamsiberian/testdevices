package net.thumbtack.testdevices.core.mappers;

import net.thumbtack.testdevices.core.models.Event;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EventMapper {
    @Insert(
           "INSERT INTO events (user_id, device_id, action_type, date)\n" +
           "VALUES (#{event.userId}, #{event.deviceId}, #{event.actionType}, #{event.date})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insert(@Param("event") Event event);

    @Delete(
            "DELETE FROM events"
    )
    Integer deleteAll();

    @Select({
            "<script>",
            "SELECT e.id AS id, e.user_id AS userId, e.device_id AS deviceId, e.action_type AS actionType, e.date::timestamp AS date \n" +
            "FROM events e \n" +
            "WHERE e.device_id = #{deviceId} " +
                "<if test='userId != null'> " +
                    "AND e.user_id = #{userId} " +
                "</if>",
            "ORDER BY e.date DESC " +
            "LIMIT 1 " +
            "</script>"
    })
    Event getLastEventByDeviceIdAndUserId(@Param("deviceId") long deviceId, @Param("userId") Long userId);
}
