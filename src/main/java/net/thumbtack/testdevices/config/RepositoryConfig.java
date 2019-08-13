package net.thumbtack.testdevices.config;

import net.thumbtack.testdevices.core.mappers.DeviceMapper;
import net.thumbtack.testdevices.core.repositories.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    public DeviceDao getDeviceDao(
            final DeviceMapper deviceMapper
    ) {
        return new DeviceDaoImpl(deviceMapper);
    }
}
