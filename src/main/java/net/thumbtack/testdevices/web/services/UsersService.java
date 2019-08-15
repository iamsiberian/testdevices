package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.dto.request.UserRequest;
import net.thumbtack.testdevices.dto.response.UserResponse;

public interface UsersService {
    UserResponse userRegistration(UserRequest request);

    UserResponse administratorRegistration(UserRequest request);
}
