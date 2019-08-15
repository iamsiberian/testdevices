package net.thumbtack.testdevices.core.repositories;

import net.thumbtack.testdevices.core.models.Authority;

import java.util.List;

public interface AuthoritiesDao {
    Authority insert(Authority authority);

    void insert(List<Authority> authoritiesList);

    Authority getByName(String authority);

    List<Authority> getAll();

    void deleteAll();
}
