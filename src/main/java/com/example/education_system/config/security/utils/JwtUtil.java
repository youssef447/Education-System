package com.example.education_system.config.security.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public abstract class JwtUtil {
    private static final String SECRET = "my-super-secret-key-that-is-long-enough-1234567890!@#";
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    //Generate Token with a subject: username
    public static String generateToken(String username) {
        //1hour
        long EXPIRATION_TIME = 1000 * 60 * 60;
        return Jwts.builder()
                .subject(username) // new fluent way
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public static String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private static Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}