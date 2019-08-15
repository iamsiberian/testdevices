package net.thumbtack.testdevices.web.converters;

import net.thumbtack.testdevices.core.models.User;
import net.thumbtack.testdevices.dto.request.UserRequest;
import net.thumbtack.testdevices.dto.response.UserResponse;

public class UserDtoToModelConverter {
    public User getUserFromUserRequest(final UserRequest request) {
        return new User(
                request.getFirstName(),
                request.getLastName(),
                request.getPhone(),
                request.getEmail(),
                request.getPassword()
        );
    }

    public UserResponse getUserResponseFromUser(final User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getEmail(),
                user.getAuthorities()
        );
    }
}
