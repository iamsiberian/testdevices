package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.core.models.Authority;
import net.thumbtack.testdevices.core.models.AuthorityType;
import net.thumbtack.testdevices.core.models.User;
import net.thumbtack.testdevices.core.repositories.AuthoritiesDao;
import net.thumbtack.testdevices.core.repositories.UsersDao;
import net.thumbtack.testdevices.dto.request.UserRequest;
import net.thumbtack.testdevices.dto.response.UserResponse;
import net.thumbtack.testdevices.exceptions.ErrorCode;
import net.thumbtack.testdevices.exceptions.TestDevicesException;
import net.thumbtack.testdevices.web.converters.UserDtoToModelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsersServiceImpl implements UsersService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersServiceImpl.class);
    private AuthoritiesDao authoritiesDao;
    private UsersDao usersDao;
    private UserDtoToModelConverter userDtoToModelConverter;

    public UsersServiceImpl(
            final AuthoritiesDao authoritiesDao,
            final UsersDao usersDao,
            final UserDtoToModelConverter userDtoToModelConverter
    ) {
        this.authoritiesDao = authoritiesDao;
        this.usersDao = usersDao;
        this.userDtoToModelConverter = userDtoToModelConverter;
    }

    @Override
    public UserResponse userRegistration(final UserRequest request) {
        LOGGER.debug("UsersServiceImpl userRegistration: {}", request);
        return registration(request, AuthorityType.USER);
    }

    @Override
    public UserResponse administratorRegistration(final UserRequest request) {
        LOGGER.debug("UsersServiceImpl administratorRegistration: {}", request);
        return registration(request, AuthorityType.ADMINISTRATOR);
    }

    private UserResponse registration(final UserRequest request, final AuthorityType authorityType) {
        LOGGER.debug("UsersServiceImpl registration: {}, with auth: {}", request, authorityType);
        User user = userDtoToModelConverter.getUserFromUserRequest(request);
        Authority authority = authoritiesDao.getByName(authorityType.getAuthorityType());
        if (authority == null) {
            throw new TestDevicesException(ErrorCode.INVALID_AUTHORITY);
        }
        return userDtoToModelConverter.getUserResponseFromUser(usersDao.insert(authority.getId(), user));
    }
}
