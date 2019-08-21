package net.thumbtack.testdevices.config;

import net.thumbtack.testdevices.core.mappers.AuthoritiesMapper;
import net.thumbtack.testdevices.core.mappers.DeviceMapper;
import net.thumbtack.testdevices.core.mappers.EventMapper;
import net.thumbtack.testdevices.core.mappers.UserMapper;
import net.thumbtack.testdevices.core.mappers.UsersAuthoritiesRelationshipsMapper;
import net.thumbtack.testdevices.core.repositories.AuthoritiesDao;
import net.thumbtack.testdevices.core.repositories.AuthoritiesDaoImpl;
import net.thumbtack.testdevices.core.repositories.DeviceDao;
import net.thumbtack.testdevices.core.repositories.DeviceDaoImpl;
import net.thumbtack.testdevices.core.repositories.EventsDao;
import net.thumbtack.testdevices.core.repositories.EventsDaoImpl;
import net.thumbtack.testdevices.core.repositories.UsersDao;
import net.thumbtack.testdevices.core.repositories.UsersDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@MapperScan("net.thumbtack.testdevices.core.mappers")
public class RepositoryConfig {
    @Bean
    public DeviceDao getDeviceDao(
            final DeviceMapper deviceMapper
    ) {
        return new DeviceDaoImpl(deviceMapper);
    }

    @Bean
    public AuthoritiesDao getAuthoritiesDao(
            final AuthoritiesMapper authoritiesMapper
    ) {
        return new AuthoritiesDaoImpl(authoritiesMapper);
    }

    @Bean
    public UsersDao getUsersDao(
            final UserMapper userMapper,
            final AuthoritiesMapper authoritiesMapper,
            final UsersAuthoritiesRelationshipsMapper usersAuthoritiesRelationshipsMapper
    ) {
        return new UsersDaoImpl(userMapper, authoritiesMapper, usersAuthoritiesRelationshipsMapper);
    }

    @Bean
    public EventsDao getEventsDao(
            final EventMapper eventMapper
    ) {
        return new EventsDaoImpl(eventMapper);
    }
}
