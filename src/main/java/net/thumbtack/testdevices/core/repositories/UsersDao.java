package net.thumbtack.testdevices.core.repositories;

import net.thumbtack.testdevices.core.models.User;

import java.util.List;

public interface UsersDao {
    User insert(List<Long> authoritiesIdList, User user);

    User insert(long authorityId, User user);

    User getById(long id);

    User findByLogin(String email);

    void deleteAll();
}
