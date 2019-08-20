package net.thumbtack.testdevices.web.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Class wrapper for Exception
 */
public class JwtAuthenticationException extends AuthenticationException {
    /**
     * Basic constructor
     *
     * @param message message exception text
     */
    public JwtAuthenticationException(final String message) {
        super(message);
    }

    /**
     * Basic constructor
     *
     * @param message message exception text
     * @param cause cause exception object
     */
    public JwtAuthenticationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
