package net.thumbtack.testdevices.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filter to take JwtToken from the request header.
 */
public class JwtAuthFilter extends AbstractAuthenticationProcessingFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final Pattern BEARER_AUTH_PATTERN = Pattern.compile("^Bearer\\s+(.*)$");
    private static final int TOKEN_GROUP = 1;

    /**
     * The basic constructor
     *
     * @param matcher matcher
     */
    public JwtAuthFilter(final RequestMatcher matcher) {
        super(matcher);
    }

    @Override
    public Authentication attemptAuthentication(
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse
    ) throws AuthenticationException, IOException, ServletException {
        String token;
        try {
            String authHeader = httpServletRequest.getHeader("Authorization");
            Matcher matcher = BEARER_AUTH_PATTERN.matcher(authHeader);
            if (matcher.matches()) {
                token = matcher.group(TOKEN_GROUP);
            } else {
                throw new JwtAuthenticationException("Invalid authorization header: " + authHeader);
            }
        } catch (Exception e) {
            logger.warn("Failed to get authorization header: {}", e.getMessage());
            return anonymousToken();
        }
        return new JwtToken(token);
    }

    private Authentication anonymousToken() {
        return new AnonymousAuthenticationToken("ANONYMOUS", "ANONYMOUS",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult
    ) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
