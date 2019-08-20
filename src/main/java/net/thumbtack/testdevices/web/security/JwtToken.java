package net.thumbtack.testdevices.web.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Model for a Unauthenticated json web token
 */
public class JwtToken extends AbstractAuthenticationToken {
    private final String token;

    /**
     * The basic constructor
     *
     * @param token token
     */
    public JwtToken(final String token) {
        super(null);
        this.token = token;
        super.setAuthenticated(false);
    }

    @Override
    public void setAuthenticated(final boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
