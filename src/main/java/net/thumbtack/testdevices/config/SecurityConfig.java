package net.thumbtack.testdevices.config;

import net.thumbtack.testdevices.web.security.JwtAuthFailureHandler;
import net.thumbtack.testdevices.web.security.JwtAuthFilter;
import net.thumbtack.testdevices.web.security.JwtAuthSuccessHandler;
import net.thumbtack.testdevices.web.security.JwtAuthenticationProvider;
import net.thumbtack.testdevices.web.security.JwtTokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Security configuration class
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private JwtTokenService jwtTokenService;

    public SecurityConfig(final JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.formLogin().disable();
        http.logout().disable();
        http.sessionManagement().disable();
        http.requestCache().disable();
        http.anonymous();

        RequestMatcher authFilterRequests = request -> {
            String path = request.getServletPath() + request.getPathInfo();
            return !(
                            path.startsWith("/api/login") |
                            path.startsWith("/api/registration") |
                            path.startsWith("/swagger-") |
                            path.startsWith("/v2/api-docs") |
                            path.startsWith("/webjars") |
                            path.startsWith("/csrf") |
                            path.startsWith("/error") |
                            path.startsWith("/actuator")
            );
        };
        JwtAuthFilter authFilter = new JwtAuthFilter(authFilterRequests);
        authFilter.setAuthenticationSuccessHandler(new JwtAuthSuccessHandler());
        authFilter.setAuthenticationFailureHandler(new JwtAuthFailureHandler());
        http.addFilterBefore(authFilter, FilterSecurityInterceptor.class);

        http.authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/registration/user").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .and().authorizeRequests().anyRequest().authenticated();
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new JwtAuthenticationProvider(jwtTokenService));
    }
}
