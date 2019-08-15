package net.thumbtack.testdevices.config;

import net.thumbtack.testdevices.core.repositories.DeviceDao;
import net.thumbtack.testdevices.web.converters.DeviceDtoToModelConverter;
import net.thumbtack.testdevices.web.services.DevicesService;
import net.thumbtack.testdevices.web.services.DevicesServiceImpl;
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
}
