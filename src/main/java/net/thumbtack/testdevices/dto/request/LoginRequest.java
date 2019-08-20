package net.thumbtack.testdevices.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Model to receive login and password
 */
public class LoginRequest {
    @Email
    private final String login;
    @NotBlank(message = "password can not be blank or null")
    @Pattern(
            regexp = "[a-zA-Zа-яА-Я0-9\\W_]+",
            message = "password may contain English and Russian letters, numbers and all symbols"
    )
    private final String password;

    /**
     * The basic constructor
     *
     * @param username login
     * @param password password
     */
    @JsonCreator
    public LoginRequest(
            final @JsonProperty("login") String username,
            final @JsonProperty("password") String password
    ) {
        this.login = username;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
