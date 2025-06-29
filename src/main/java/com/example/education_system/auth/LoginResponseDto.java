package com.example.education_system.auth;

import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
public class LoginResponseDto {
    final String token;
    final Date date;

}
