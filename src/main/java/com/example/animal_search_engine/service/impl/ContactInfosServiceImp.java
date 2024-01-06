package com.example.animal_search_engine.service.impl;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.example.animal_search_engine.exception.CustomException;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.model.ContactInfo;
import com.example.animal_search_engine.repository.ContactInfosRepository;
import com.example.animal_search_engine.service.ContactInfosService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ContactInfosServiceImp implements ContactInfosService {

    ContactInfosRepository contactInfosRepository;

    @Override
    public void create(ContactInfo contactInfo) {
        contactInfosRepository.save(contactInfo);
    }

    @Override
    public void update(ContactInfo contactInfo) {

        if(contactInfo.getId()!=0 && !contactInfosRepository.existsById(contactInfo.getId())){
            throw new CustomException("There is no entity with such ID in the database.",HttpStatus.NOT_FOUND);
        }
        contactInfosRepository.save(contactInfo);
    }

    @Override
    public void delete(int contactId)  {
        if(!contactInfosRepository.existsById(contactId)){
            throw new CustomException("Contact not found",HttpStatus.NOT_FOUND);
        }
        contactInfosRepository.deleteById(contactId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactInfo> getByConsumerId(int  consumerId) {
        List<ContactInfo> contactInfoList = contactInfosRepository.findByConsumerId(consumerId);

        if(contactInfoList.isEmpty()){
            throw new CustomException("Contact not found",HttpStatus.NOT_FOUND);
        }
        return contactInfoList;
    }

    @Override
    @Transactional(readOnly = true)
    public ContactInfo getById(int id) {
        return contactInfosRepository.findById(id).orElseThrow(()-> new CustomException("Not found",HttpStatus.NOT_FOUND));
    }
}

