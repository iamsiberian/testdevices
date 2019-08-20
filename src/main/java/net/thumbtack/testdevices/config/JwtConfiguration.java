package net.thumbtack.testdevices.config;

import net.thumbtack.testdevices.web.security.JwtSettings;
import net.thumbtack.testdevices.web.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {
    @Bean
    public JwtSettings getJwtSettings(
            final @Value("${jwt.token.Issuer}") String tokenIssuer,
            final @Value("${jwt.token.SigningKey}") String tokenSigningKey
    ) {
        return new JwtSettings(tokenIssuer, tokenSigningKey);
    }

    @Bean
    public JwtTokenService getJwtTokenService(final JwtSettings jwtSettings) {
        return new JwtTokenService(jwtSettings);
    }
}
