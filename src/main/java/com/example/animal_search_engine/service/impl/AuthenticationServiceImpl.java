package com.example.animal_search_engine.service.impl;

import com.example.animal_search_engine.dto.request.SignUpRequest;
import com.example.animal_search_engine.dto.request.SigninRequest;
import com.example.animal_search_engine.dto.responce.JwtAuthenticationResponse;
import com.example.animal_search_engine.exception.CustomException;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.repository.ConsumerRepository;
import com.example.animal_search_engine.security_utils.SecuredConsumerServiceImpl;
import com.example.animal_search_engine.service.AuthenticationService;
import com.example.animal_search_engine.security_utils.JwtService;
import com.example.animal_search_engine.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConsumerRepository consumerRepository;
    private final RoleService roleService;
    private final SecuredConsumerServiceImpl securedConsumerService;

    @Override
    public void signup(SignUpRequest request) {
        if (consumerRepository.existsByEmail(request.getEmail())) {
            throw new CustomException("User with the same email already exists", HttpStatus.BAD_REQUEST);
        }
        Consumer customer = new Consumer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setRoles(Set.of(roleService.getUserRole()));
        customer.setEnabled(true);
        consumerRepository.save(customer);
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest checkDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(checkDTO.getEmail(), checkDTO.getPassword()));
        } catch (BadCredentialsException b) {
            throw new CustomException("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = securedConsumerService.loadUserByUsername(checkDTO.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);
        return JwtAuthenticationResponse.builder().token(jwtToken).build();

    }

    @Override
    public void blockUser(String email) {
        Consumer consumer = consumerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found",HttpStatus.NOT_FOUND));
        consumer.setEnabled(false);
        consumerRepository.save(consumer);
    }

    @Override
    public void unblockUser(String email) {
        Consumer consumer = consumerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found",HttpStatus.NOT_FOUND));

        consumer.setEnabled(true);
        consumerRepository.save(consumer);
    }

}
