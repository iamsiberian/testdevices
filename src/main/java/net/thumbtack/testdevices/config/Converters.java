package net.thumbtack.testdevices.config;

import net.thumbtack.testdevices.web.converters.DeviceDtoToModelConverter;
import net.thumbtack.testdevices.web.converters.UserDtoToModelConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Converters {
    @Bean
    public DeviceDtoToModelConverter getDeviceDtoToModelConverter() {
        return new DeviceDtoToModelConverter();
      
    @Bean
    public UserDtoToModelConverter getUserDtoToModelConverter() {
        return new UserDtoToModelConverter();
    }
}
