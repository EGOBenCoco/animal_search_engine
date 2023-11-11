package com.example.animal_search_engine.service;

import com.example.animal_search_engine.dto.request.ConsumerRequest;
import com.example.animal_search_engine.dto.responce.ConsumerResponce;
import com.example.animal_search_engine.model.Consumer;

import java.util.Optional;

public interface ConsumerService {
    Optional<Consumer> getConsumerById(int id);
    void createNewConsumer(Consumer consumer);

    void updateConsumer(ConsumerRequest consumer);
    void deleteConsumerById(int id);
}
