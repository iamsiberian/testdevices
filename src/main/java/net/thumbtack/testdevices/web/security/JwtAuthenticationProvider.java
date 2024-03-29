package net.thumbtack.testdevices.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * This is authentication provider from json web token
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final JwtTokenService tokenService;

    /**
     * The basic constructor
     *
     * @param tokenService token service
     */
    public JwtAuthenticationProvider(final JwtTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        String token = String.valueOf(authentication.getCredentials());
        logger.debug("Authenticating {}", token);

        try {
            return tokenService.parseToken(token);
        } catch (Exception e) {
            throw new JwtAuthenticationException("Invalid token received", e);
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return (JwtToken.class.isAssignableFrom(authentication));
    }
}
