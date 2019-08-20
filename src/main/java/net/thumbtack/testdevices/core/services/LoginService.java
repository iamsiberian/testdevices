package net.thumbtack.testdevices.core.services;

import net.thumbtack.testdevices.core.models.User;
import net.thumbtack.testdevices.core.repositories.UsersDao;
import net.thumbtack.testdevices.dto.request.LoginRequest;
import net.thumbtack.testdevices.exceptions.ErrorCode;
import net.thumbtack.testdevices.exceptions.TestDevicesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * This is service for login
 */
public class LoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    private final UsersDao usersDao;

    /**
     * The basic constructor
     *
     * @param usersDao users repository
     */
    public LoginService(final UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    /**
     * Method receives a user with authorities from loginRequest
     *
     * @param loginRequest loginRequest
     * @return user with authorities
     */
    public User login(final LoginRequest loginRequest) {
        LOGGER.debug("LoginService login: {}", loginRequest);
        User user = usersDao.findByLogin(loginRequest.getLogin());
        if (user == null) {
            throw new TestDevicesException(ErrorCode.USER_NOT_FOUND);
        }
        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            throw new TestDevicesException(ErrorCode.WRONG_PASSWORD);
        }
        return user;
    }
}
