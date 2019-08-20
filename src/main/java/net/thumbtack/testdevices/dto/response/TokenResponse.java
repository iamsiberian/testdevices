package net.thumbtack.testdevices.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model to receive token
 */
public class TokenResponse {
    private final String token;

    /**
     * The basic constructor
     *
     * @param token token
     */
    @JsonCreator
    public TokenResponse(
            final @JsonProperty("token") String token
    ) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}