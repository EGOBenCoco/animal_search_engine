package com.example.animal_search_engine.service;

import com.example.animal_search_engine.dto.ConsumerDTO;
import com.example.animal_search_engine.exception.CustomMessage;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.repository.ConsumerRepository;
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
public class ConsumerService {
    ConsumerRepository consumerRepository;

    @Transactional
    public Optional<ConsumerDTO> getConsumerById(int id)
    {
        Consumer consumer = consumerRepository.findById(id).orElseThrow(
                () -> new CustomMessage("User not found", HttpStatus.NOT_FOUND)
        );
        return Optional.of(new ConsumerDTO(consumer));
    }

    public void createNewConsumer(Consumer consumer)
    {
        consumerRepository.save(consumer);
    }

    public void updateConsumer(ConsumerDTO consumerDTO)
    {
        Consumer consumer = consumerRepository.findById(consumerDTO.getId())
                .orElseThrow(()->  new CustomMessage("User not found", HttpStatus.NOT_FOUND));
        consumer.setName(consumerDTO.getName());
        consumer.setLastName(consumerDTO.getLastname());
        consumer.setEmail(consumerDTO.getEmail());
        consumerRepository.save(consumer);
    }

    public void deleteConsumerById(int id)
    {
        if(!consumerRepository.existsById(id)) {
            throw new CustomMessage("User not found for deletion", HttpStatus.NOT_FOUND);

        }
        consumerRepository.deleteById(id);

    }
}
