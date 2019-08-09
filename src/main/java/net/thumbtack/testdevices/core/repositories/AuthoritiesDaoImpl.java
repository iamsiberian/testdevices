package net.thumbtack.testdevices.core.repositories;

import net.thumbtack.testdevices.core.mappers.AuthoritiesMapper;
import net.thumbtack.testdevices.core.models.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class AuthoritiesDaoImpl implements AuthoritiesDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthoritiesDaoImpl.class);
    private AuthoritiesMapper authoritiesMapper;

    public AuthoritiesDaoImpl(final AuthoritiesMapper authoritiesMapper) {
        this.authoritiesMapper = authoritiesMapper;
    }

    @Transactional(rollbackFor = DaoException.class)
    @Override
    public Authority insert(final Authority authority) {
        LOGGER.debug("AuthoritiesDaoImpl insert authority: {}", authority);
        authoritiesMapper.insert(authority);
        return authority;
    }

    @Transactional(rollbackFor = DaoException.class)
    @Override
    public void insert(final List<Authority> authoritiesList) {
        LOGGER.debug("AuthoritiesDaoImpl insert authorities: {}", authoritiesList);
        authoritiesMapper.insertList(authoritiesList);
    }

    @Transactional(readOnly = true)
    @Override
    public Authority getByName(final String authority) {
        LOGGER.debug("AuthoritiesDaoImpl getByName: {}", authority);
        return authoritiesMapper.getByName(authority);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Authority> getAll() {
        LOGGER.debug("AuthoritiesDaoImpl getAll");
        return authoritiesMapper.getAll();
    }

    @Transactional(rollbackFor = DaoException.class)
    @Override
    public void deleteAll() {
        LOGGER.debug("AuthoritiesDaoImpl deleteAll");
        authoritiesMapper.deleteAll();
    }
}
