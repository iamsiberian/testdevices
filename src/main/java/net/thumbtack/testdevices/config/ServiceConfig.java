package net.thumbtack.testdevices.config;

import net.thumbtack.testdevices.core.repositories.AuthoritiesDao;
import net.thumbtack.testdevices.core.repositories.DeviceDao;
import net.thumbtack.testdevices.core.repositories.UsersDao;
import net.thumbtack.testdevices.web.converters.DeviceDtoToModelConverter;
import net.thumbtack.testdevices.web.converters.UserDtoToModelConverter;
import net.thumbtack.testdevices.web.services.DevicesService;
import net.thumbtack.testdevices.web.services.DevicesServiceImpl;
import net.thumbtack.testdevices.web.services.UsersService;
import net.thumbtack.testdevices.web.services.UsersServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    public DevicesService devicesService(
            final DeviceDao deviceDao,
            final DeviceDtoToModelConverter deviceDtoToModelConverter
    ) {
        return new DevicesServiceImpl(deviceDao, deviceDtoToModelConverter);
    }

    @Bean
    public UsersService getUserService(
            final AuthoritiesDao authoritiesDao,
            final UsersDao usersDao,
            final UserDtoToModelConverter userDtoToModelConverter
    ) {
        return new UsersServiceImpl(authoritiesDao, usersDao, userDtoToModelConverter);
    }
}
