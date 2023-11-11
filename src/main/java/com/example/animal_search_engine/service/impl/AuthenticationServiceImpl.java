package com.example.animal_search_engine.service.impl;

import com.example.animal_search_engine.dao.request.SignUpRequest;
import com.example.animal_search_engine.dao.request.SigninRequest;
import com.example.animal_search_engine.dao.response.JwtAuthenticationResponse;
import com.example.animal_search_engine.enums.Role;
import com.example.animal_search_engine.exception.CustomMessage;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.repository.ConsumerRepository;
import com.example.animal_search_engine.service.AuthenticationService;
import com.example.animal_search_engine.service.ConsumerService;
import com.example.animal_search_engine.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
   // private final ConsumerRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConsumerRepository consumerRepository;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        if (consumerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomMessage("User with the same email already exists", HttpStatus.BAD_REQUEST);
        }
        // Создаем нового пользователя на основе данных из запроса
        var user = Consumer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))// Шифруем пароль
                .role(Role.USER).build();// Устанавливаем роль пользователя
        // Проверка наличия пользователя с таким email

        consumerRepository.save(user);// Сохраняем пользователя в базе данных

        var jwt = jwtService.generateToken(user);// Генерируем JWT-токен для нового пользователя

        return JwtAuthenticationResponse.builder().token(jwt).build();// Возвращаем JWT-токен в виде ответа

    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        // Аутентифицируем пользователя, проверяя соответствие email и пароля
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // Ищем пользователя в базе данных по email
        var user = consumerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        // Генерируем JWT-токен для аутентифицированного пользователя
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();// Возвращаем JWT-токен в виде ответа

    }
}
