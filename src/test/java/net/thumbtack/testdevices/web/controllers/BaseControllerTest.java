package net.thumbtack.testdevices.web.controllers;

import net.thumbtack.testdevices.core.models.Authority;
import net.thumbtack.testdevices.core.models.AuthorityType;
import net.thumbtack.testdevices.core.models.User;
import net.thumbtack.testdevices.dto.response.TokenResponse;
import net.thumbtack.testdevices.web.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"net.thumbtack.testdevices.*"})
public class BaseControllerTest {
    private static final String PREFIX = "Bearer ";

    @Autowired
    private JwtTokenService jwtTokenService;

    private User authUser = new User(
            1L,
            "John",
            "Doe",
            "+79123456789",
            "john.doe@mail.com",
            "12345",
            new Authority(2L, AuthorityType.USER)
    );

    private User authAdmin = new User(
            2L,
            "admin",
            "admin",
            "+79999999999",
            "admin@mail.com",
            "12345678",
            new Authority(2L, AuthorityType.ADMINISTRATOR)
    );

    public String getUserAuthToken() {
        TokenResponse tokenResponse = jwtTokenService.createToken(authUser);
        return PREFIX + tokenResponse.getToken();
    }

    public String getAdminAuthToken() {
        TokenResponse tokenResponse = jwtTokenService.createToken(authAdmin);
        return PREFIX + tokenResponse.getToken();
    }
}
