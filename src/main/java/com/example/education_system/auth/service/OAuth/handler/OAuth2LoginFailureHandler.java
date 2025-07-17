package com.example.education_system.auth.service.OAuth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String errorMsg = "Login Failed: ";
        if (exception instanceof OAuth2AuthenticationException oAuth2Ex) {
            errorMsg += oAuth2Ex.getError().getErrorCode();
            response.getWriter().write("{\"error\": \"" + errorMsg + "\"}");
        } else {
            response.getWriter().write("{\"error\": \"" + exception.getMessage() + "\"}");
        }

    }
}
