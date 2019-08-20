package net.thumbtack.testdevices.web.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * This class is for customization json web token
 */
@Component
public class JwtSettings {
    private String tokenIssuer;
    private String tokenSigningKey;
    private static final int TOKEN_DURATION = 240; //TODO: replace to 5 minutes, 240 minutes are for testing purposes

    /**
     * The basic constructor
     *
     * @param tokenIssuer token issuer from config .yml
     * @param tokenSigningKey token signing key from config .yml
     */
    public JwtSettings(
            final @Value("${jwt.token.Issuer}") String tokenIssuer,
            final @Value("${jwt.token.SigningKey}") String tokenSigningKey
    ) {
        this.tokenIssuer = tokenIssuer;
        this.tokenSigningKey = tokenSigningKey;
    }

    /**
     * This method returns a token issuer
     *
     * @return token issuer
     */
    public String getTokenIssuer() {
        return tokenIssuer;
    }

    /**
     * This method returns a token signing key
     *
     * @return token signing key from config .yml
     */
    public byte[] getTokenSigningKey() {
        return tokenSigningKey.getBytes(StandardCharsets.US_ASCII);
    }

    /**
     * This method returns a token duration
     *
     * @return token duration
     */
    public Duration getTokenExpiredIn() {
        return Duration.ofMinutes(TOKEN_DURATION);
    }
}
