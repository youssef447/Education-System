package com.example.education_system.config.security;

import com.example.education_system.auth.service.OAuth.CustomOAuth2UserService;
import com.example.education_system.auth.service.OAuth.handler.OAuth2LoginFailureHandler;
import com.example.education_system.auth.service.OAuth.handler.OAuth2LoginSuccessHandler;
import com.example.education_system.config.security.filter.JwtAuthFilter;
import com.example.education_system.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {

        return http
                .headers(httpSecurityHeadersConfigurer ->
                        httpSecurityHeadersConfigurer
                                .contentSecurityPolicy(csp ->
                                        csp.policyDirectives(
                                                "default-src 'self'; " +
                                                        "script-src 'self'; " +
                                                        "object-src 'none'; " +
                                                        "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com/; " +
                                                        "font-src https://fonts.gstatic.com;"
                                        )
                                )
                                .xssProtection(HeadersConfigurer.XXssConfig::disable)
                )

                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**", "/oauth2/**", "/course/payment/success",
                                "/course/payment/fail", "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()).
                oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService))
                        .successHandler(oAuth2LoginSuccessHandler).failureHandler(oAuth2LoginFailureHandler))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // This bean will be used by Spring Security to handle authentication requests
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder)
            throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return builder.build();
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}