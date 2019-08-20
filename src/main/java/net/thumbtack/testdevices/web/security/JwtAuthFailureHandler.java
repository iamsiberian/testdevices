package net.thumbtack.testdevices.web.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This is handler of failure authentication
 */
public class JwtAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse,
            final AuthenticationException e
    )
            throws IOException, ServletException {
        saveException(httpServletRequest, e);
    }

    private void saveException(
            final HttpServletRequest request,
            final AuthenticationException exception
    ) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        }
    }
}
