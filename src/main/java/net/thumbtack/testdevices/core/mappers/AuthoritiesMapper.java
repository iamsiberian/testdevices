package net.thumbtack.testdevices.core.mappers;

import net.thumbtack.testdevices.core.models.Authority;
import net.thumbtack.testdevices.core.models.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface AuthoritiesMapper {
    @Insert(
            "INSERT INTO authorities (authority) VALUES (#{authority.authorityType})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(@Param("authority") Authority authority);

    @Insert({
            "<script>",
            "INSERT INTO authorities (authority) VALUES",
            "<foreach item='item' collection='list' separator=','>",
            "( #{item.authorityType} )",
            "</foreach>",
            "</script>"
    })
    Integer insertList(@Param("list") List<Authority> authoritiesList);

    @Select(
            "SELECT au.id, au.authority\n" +
            "FROM authorities au \n" +
            "WHERE au.authority = #{authority}"
    )
    Authority getByName(@Param("authority") String authority);

    @Select(
            "SELECT au.id, au.authority\n" +
            "FROM authorities au \n" +
            "    JOIN users_authorities_relationships uar on au.id = uar.authority_id\n" +
            "WHERE uar.user_id = #{user.id}"
    )
    Set<Authority> getByUserId(@Param("user") User user);

    @Select(
            "SELECT au.id, au.authority FROM authorities au"
    )
    List<Authority> getAll();

    @Delete(
            "DELETE FROM authorities"
    )
    Integer deleteAll();
}
