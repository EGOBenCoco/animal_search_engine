package com.example.animal_search_engine.service;

import com.example.animal_search_engine.dto.request.ConsumerUpdateRequest;
import com.example.animal_search_engine.dto.request.UpdatePasswordRequest;
import com.example.animal_search_engine.model.Consumer;

public interface ConsumerService{
    Consumer getById(int consumerId);
    void update(int consumerId,ConsumerUpdateRequest consumerUpdateRequest);
    void deleteById(int consumerId);
    void updatePassword(String login, UpdatePasswordRequest updatePasswordRequest);
}
