package net.thumbtack.testdevices.core.mappers;

import net.thumbtack.testdevices.core.models.Event;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

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
}
