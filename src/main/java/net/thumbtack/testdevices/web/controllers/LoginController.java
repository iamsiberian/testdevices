package net.thumbtack.testdevices.web.controllers;

import net.thumbtack.testdevices.core.models.User;
import net.thumbtack.testdevices.core.services.loginservice.LoginService;
import net.thumbtack.testdevices.dto.request.LoginRequest;
import net.thumbtack.testdevices.web.security.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/api"})
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    private final LoginService loginService;
    private final JwtTokenService tokenService;

    /**
     * The basic constructor
     *
     * @param loginService login service
     * @param tokenService json web token service
     */
    public LoginController(
            final LoginService loginService,
            final JwtTokenService tokenService
    ) {
        this.loginService = loginService;
        this.tokenService = tokenService;
    }

    /**
     * This method returns a json web token
     *
     * @param loginRequest user's loginRequest
     * @return token
     */
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(final @Valid @RequestBody LoginRequest loginRequest) {
        LOGGER.debug("LoginController login {}", loginRequest);
        User user = loginService.login(loginRequest);
        return ResponseEntity
                .ok(tokenService.createToken(user));
    }

    /**
     * This method returns a authentication
     *
     * @param token token
     * @return authenticated data
     */
    @GetMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Authentication getName(@RequestParam("token") final String token) {
        LOGGER.debug("LoginController getName {}", token);
        return tokenService.parseToken(token);
    }
}
