package net.thumbtack.testdevices.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.thumbtack.testdevices.core.models.Authority;
import net.thumbtack.testdevices.core.models.User;
import net.thumbtack.testdevices.dto.response.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is service for a java web token
 */
public class JwtTokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenService.class);
    private final JwtSettings settings;

    /**
     * The basic constructor
     *
     * @param settings instance of JwtSettings class
     */
    public JwtTokenService(final JwtSettings settings) {
        this.settings = settings;
    }

    /**
     * Creates new TokenResponse for user.
     * @param user contains User to be represented as token
     * @return signed token
     */
    public TokenResponse createToken(final User user) {
        LOGGER.debug("Generating token for {}", user.getEmail());

        Instant now = Instant.now();

        Claims claims = Jwts.claims()
                .setIssuer(settings.getTokenIssuer())
                .setIssuedAt(java.util.Date.from(now))
                .setSubject(user.getEmail())
                .setExpiration(java.util.Date.from(now.plus(settings.getTokenExpiredIn())));

        Set<Authority> authoritySet = user.getAuthorities();
        List<String> authorityList = new ArrayList<>();
        for (Authority authority : authoritySet) {
            authorityList.add(authority.getAuthorityType().getAuthorityType());
        }
        claims.put("authorities", authorityList);
        claims.put("id", user.getId());
        String tokenString = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();
        return new TokenResponse(tokenString);
    }

    /**
     * Parses the token
     * @param token the token string to parse
     * @return authenticated data
     */
    public Authentication parseToken(final String token) {
        LOGGER.debug("Parse token: {}", token);
        Jws<Claims> claims = Jwts.parser().setSigningKey(settings.getTokenSigningKey()).parseClaimsJws(token);

        String subject = claims.getBody().getSubject();
        List<String> tokenAuthorities = claims.getBody().get("authorities", List.class);
        List<GrantedAuthority> authorities = tokenAuthorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new AuthenticatedJwtToken(subject, authorities);
    }
}
