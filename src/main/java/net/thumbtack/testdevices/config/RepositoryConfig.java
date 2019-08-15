package net.thumbtack.testdevices.config;

import net.thumbtack.testdevices.core.mappers.DeviceMapper;
import net.thumbtack.testdevices.core.repositories.DeviceDao;
import net.thumbtack.testdevices.core.repositories.DeviceDaoImpl;
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
