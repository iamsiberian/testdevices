package net.thumbtack.testdevices.config;

import net.thumbtack.testdevices.core.mappers.AuthoritiesMapper;
import net.thumbtack.testdevices.core.mappers.UserMapper;
import net.thumbtack.testdevices.core.mappers.UsersAuthoritiesRelationshipsMapper;
import net.thumbtack.testdevices.core.repositories.AuthoritiesDao;
import net.thumbtack.testdevices.core.repositories.AuthoritiesDaoImpl;
import net.thumbtack.testdevices.core.repositories.UsersDao;
import net.thumbtack.testdevices.core.repositories.UsersDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
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
}
