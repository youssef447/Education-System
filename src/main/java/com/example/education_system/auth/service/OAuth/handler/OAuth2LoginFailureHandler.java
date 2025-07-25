package com.example.education_system.auth.service.OAuth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    public OAuth2LoginFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Authentication Failed");
        response.setContentType("application/json");

        if (exception instanceof OAuth2AuthenticationException) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            errorResponse.put("message", ((OAuth2AuthenticationException) exception).getError().getErrorCode());
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            errorResponse.put("message", exception.getMessage());

        }
        objectMapper.writeValue(response.getOutputStream(), errorResponse);


    }
}
