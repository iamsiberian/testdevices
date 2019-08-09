package net.thumbtack.testdevices.web.controllers;

import net.thumbtack.testdevices.dto.request.UserRequest;
import net.thumbtack.testdevices.web.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/api/registration"})
public class RegistrationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private UsersService usersService;

    public RegistrationController(final UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerUser(final @Valid @RequestBody UserRequest request) {
        LOGGER.debug("RegistrationController registerUser {}", request);
        return
                ResponseEntity
                        .ok()
                        .body(usersService.userRegistration(request));
    }
}
