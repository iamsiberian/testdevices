package net.thumbtack.testdevices.web.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Model for a authenticated json web token
 */
public class AuthenticatedJwtToken extends AbstractAuthenticationToken {
    private final String subject;

    /**
     * The basic constructor
     *
     * @param subject subject
     * @param authorities authorities collection
     */
    public AuthenticatedJwtToken(final String subject, final Collection<GrantedAuthority> authorities) {
        super(authorities);
        this.subject = subject;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return subject;
    }
}
