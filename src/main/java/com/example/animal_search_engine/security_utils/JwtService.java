package com.example.animal_search_engine.security_utils;

import org.springframework.security.core.userdetails.UserDetails;

    public interface JwtService {
        String extractUserName(String token);

        String generateToken(UserDetails userDetails);

        boolean isTokenValid(String token, UserDetails userDetails);
    }
