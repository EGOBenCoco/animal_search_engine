package com.example.animal_search_engine.service;

import com.example.animal_search_engine.exception.CustomException;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.model.ContactInfo;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ContactInfosService {

    void create(ContactInfo contactInfo);

    void update(ContactInfo contactInfo);

    void delete(int id);

    List<ContactInfo> getByConsumerId(int id);

    ContactInfo getById(int id);
}
