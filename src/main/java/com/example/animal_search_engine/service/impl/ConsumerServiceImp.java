package com.example.animal_search_engine.service.impl;

import com.example.animal_search_engine.dto.request.ConsumerRequest;
import com.example.animal_search_engine.dto.responce.ConsumerResponce;
import com.example.animal_search_engine.enums.Role;
import com.example.animal_search_engine.exception.CustomMessage;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.repository.ConsumerRepository;
import com.example.animal_search_engine.service.ConsumerService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ConsumerServiceImp implements ConsumerService {
    ConsumerRepository consumerRepository;

    @Transactional
    public Optional<Consumer> getConsumerById(int id)
    {

       return Optional.of( consumerRepository.findById(id).orElseThrow(
                () -> new CustomMessage("User not found", HttpStatus.NOT_FOUND)
        ));
       // return Optional.of(new ConsumerResponce(consumer));
    }

    public void createNewConsumer(Consumer consumer)
    {

        // Проверка наличия пользователя с таким email
        if (consumerRepository.findByEmail(consumer.getEmail()).isPresent()) {
            throw new CustomMessage("User with the same email already exists", HttpStatus.BAD_REQUEST);
        }
        // Создание нового пользователя
        consumerRepository.save(consumer);
    }

    public void updateConsumer(ConsumerRequest consumerRequest) {
        if (consumerRepository.findByEmail(consumerRequest.getEmail()).isPresent()) {
            throw new CustomMessage("User with the same email already exists", HttpStatus.BAD_REQUEST);
        }
        Consumer user = Consumer.builder()
                .id(consumerRequest.getId())
                .firstName(consumerRequest.getFirstName())
                .lastName(consumerRequest.getLastName())
                .email(consumerRequest.getEmail())
                .password(consumerRequest.getPassword())
                .contactInfos(consumerRequest.getContactInfos())
                .role(Role.USER)
                .build();

        consumerRepository.save(user);
    }

    public void deleteConsumerById(int id)
    {
        if(!consumerRepository.existsById(id)) {
            throw new CustomMessage("User not found for deletion", HttpStatus.NOT_FOUND);

        }
        consumerRepository.deleteById(id);

    }
}
