package com.example.animal_search_engine.service;


import com.example.animal_search_engine.dto.request.SignUpRequest;
import com.example.animal_search_engine.dto.request.SigninRequest;
import com.example.animal_search_engine.dto.responce.JwtAuthenticationResponse;

public interface AuthenticationService {
    void signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);

    void blockUser(String email);

    void unblockUser(String email);

}
