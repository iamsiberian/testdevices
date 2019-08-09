package net.thumbtack.testdevices.core.mappers;

import net.thumbtack.testdevices.core.models.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UsersAuthoritiesRelationshipsMapper {
    @Insert({
            "<script>",
            "INSERT INTO users_authorities_relationships (authority_id, user_id) VALUES",
            "<foreach item='item' collection='list' separator=','>",
            "( #{item}, #{user.id} )",
            "</foreach>",
            "</script>"
    })
    Integer addAuthoritiesByIdToUser(@Param("list") List<Long> authoritiesIdList, @Param("user") User user);

    @Insert(
            "INSERT INTO users_authorities_relationships (authority_id, user_id) " +
            "VALUES (#{authorityId}, #{user.id})"
    )
    void addAuthorityByIdToUser(@Param("authorityId") long authorityId, @Param("user") User user);

    @Delete(
            "DELETE FROM users_authorities_relationships " +
            "WHERE user_id = #{user.id}"
    )
    Integer deleteByUserId(@Param("user") User user);

    @Delete(
            "DELETE FROM users_authorities_relationships"
    )
    Integer deleteAll();
}
