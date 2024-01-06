package com.example.animal_search_engine.service.impl;

import com.example.animal_search_engine.dto.request.ConsumerUpdateRequest;
import com.example.animal_search_engine.dto.request.UpdatePasswordRequest;
import com.example.animal_search_engine.exception.CustomException;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.model.ContactInfo;
import com.example.animal_search_engine.repository.ConsumerRepository;
import com.example.animal_search_engine.service.AnnouncementService;
import com.example.animal_search_engine.service.ConsumerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConsumerServiceImp implements ConsumerService {
    ConsumerRepository consumerRepository;
    PasswordEncoder passwordEncoder;
    @Override
    @Transactional(readOnly = true)
    public Consumer getById(int consumerId){
        return consumerRepository.findById(consumerId)
                .orElseThrow(() -> new CustomException("Consumer not found by id", HttpStatus.NOT_FOUND));
    }

    @Override
    public void update(int consumerId, ConsumerUpdateRequest updateConsumer) {
        consumerRepository.findById(consumerId).ifPresentOrElse(consumer ->
        {
            consumer.setFirstName(updateConsumer.getFirstName());
            consumer.setLastName(updateConsumer.getLastName());
            consumer.setEmail(updateConsumer.getEmail());
            consumerRepository.save(consumer);
        }, () -> {
            throw new CustomException("Not found",HttpStatus.NOT_FOUND);
        });

    }

    @Override
    public void updatePassword(String login, UpdatePasswordRequest updatePasswordRequest) {
        Consumer existingConsumer = consumerRepository.findByEmail(login).orElseThrow(() ->new CustomException( "Not found",HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), existingConsumer.getPassword())) {
            throw new CustomException("Old password is incorrect", HttpStatus.BAD_REQUEST);
        }

       else if (!updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getConfirmPassword())) {
            throw new CustomException("New password and confirm password do not match", HttpStatus.BAD_REQUEST);
        }

        existingConsumer.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        consumerRepository.save(existingConsumer);
    }


    public void deleteById(int consumerId) {

        if(!consumerRepository.existsById(consumerId)){
            throw new CustomException("Consumer not found",HttpStatus.NOT_FOUND);
        }
        consumerRepository.deleteById(consumerId);
    }

}
