package com.example.animal_search_engine.service;


import com.example.animal_search_engine.dao.request.SignUpRequest;
import com.example.animal_search_engine.dao.request.SigninRequest;
import com.example.animal_search_engine.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
