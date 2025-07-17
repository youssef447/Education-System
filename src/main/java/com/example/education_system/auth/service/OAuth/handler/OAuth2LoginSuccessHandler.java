package com.example.education_system.auth.service.OAuth.handler;

import com.example.education_system.config.security.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        String email = user.getAttribute("email");

        // Generate token from email
        String token = JwtUtil.generateToken(email);

        /// Return token in JSON response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = "{\"token\": \"" + token + "\"}";
        response.getWriter().write(json);

        // (frontend reads it)
        // getRedirectStrategy().sendRedirect(request, response,
              //  "http://localhost:3000/oauth2/success?token=" + token);
    }
}

