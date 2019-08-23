package net.thumbtack.testdevices.core.repositories;

import net.thumbtack.testdevices.core.mappers.AuthoritiesMapper;
import net.thumbtack.testdevices.core.mappers.UserMapper;
import net.thumbtack.testdevices.core.mappers.UsersAuthoritiesRelationshipsMapper;
import net.thumbtack.testdevices.core.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UsersDaoImpl implements UsersDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersDaoImpl.class);
    private UserMapper userMapper;
    private AuthoritiesMapper authoritiesMapper;
    private UsersAuthoritiesRelationshipsMapper usersAuthoritiesRelationshipsMapper;

    public UsersDaoImpl(
            final UserMapper userMapper,
            final AuthoritiesMapper authoritiesMapper,
            final UsersAuthoritiesRelationshipsMapper usersAuthoritiesRelationshipsMapper
    ) {
        this.userMapper = userMapper;
        this.authoritiesMapper = authoritiesMapper;
        this.usersAuthoritiesRelationshipsMapper = usersAuthoritiesRelationshipsMapper;
    }

    @Transactional(rollbackFor = DaoException.class)
    @Override
    public User insert(final long authorityId, final User user) {
        LOGGER.debug("UsersDaoImpl insert user: {}, with authority id: {}", user, authorityId);
        userMapper.insert(user);
        usersAuthoritiesRelationshipsMapper.addAuthorityByIdToUser(authorityId, user);
        user.setAuthorities(authoritiesMapper.getByUserId(user));
        return user;
    }

    @Transactional(rollbackFor = DaoException.class)
    @Override
    public User insert(final List<Long> authoritiesIdList, final User user) {
        LOGGER.debug("UsersDaoImpl insert user: {}, with authorities id: {}", user, authoritiesIdList);
        userMapper.insert(user);
        usersAuthoritiesRelationshipsMapper.addAuthoritiesByIdToUser(authoritiesIdList, user);
        user.setAuthorities(authoritiesMapper.getByUserId(user));
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public User getById(final long id) {
        LOGGER.debug("UsersDaoImpl getById : {}", id);
        return userMapper.getById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User getLastUserWhoTakenDeviceByDeviceId(final long deviceId) {
        LOGGER.debug("UsersDaoImpl getLastUserWhoTakenDeviceByDeviceId: {}", deviceId);
        return userMapper.getLastUserWhoTakenDeviceByDeviceId(deviceId);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByLogin(final String email) {
        LOGGER.debug("UsersDaoImpl findByLogin : {}", email);
        return userMapper.getByEMail(email);
    }

    @Transactional(rollbackFor = DaoException.class)
    @Override
    public void deleteAll() {
        LOGGER.debug("UsersDaoImpl deleteAll");
        usersAuthoritiesRelationshipsMapper.deleteAll();
        userMapper.deleteAll();
    }
}
