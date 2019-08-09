package net.thumbtack.testdevices.config;

import net.thumbtack.testdevices.core.mappers.AuthoritiesMapper;
import net.thumbtack.testdevices.core.repositories.AuthoritiesDao;
import net.thumbtack.testdevices.core.repositories.AuthoritiesDaoImpl;
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
}
