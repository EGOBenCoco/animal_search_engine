package com.example.animal_search_engine.service;

import com.example.animal_search_engine.dto.ContactInfoDTO;
import com.example.animal_search_engine.exception.CustomMessage;
import com.example.animal_search_engine.model.ContactInfo;
import com.example.animal_search_engine.repository.ContactInfoRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ContactInfoService {

    ContactInfoRepository contactInfoRepository;

    @Transactional
    public Optional<ContactInfo> getContactById(int id) {
        if(!contactInfoRepository.existsById(id)){
            throw new CustomMessage("ContactInfo not found", HttpStatus.NOT_FOUND);
        }
      return contactInfoRepository.findById(id);
    }

    @Transactional
    public List<ContactInfoDTO> getContactByConsumerId(int id){
        List<ContactInfo> contactInfoList = contactInfoRepository.findAllByConsumerId(id);
        if (contactInfoList.isEmpty()) {
            throw new CustomMessage("No contact information found for the specified consumer id", HttpStatus.NOT_FOUND);
        }
        return contactInfoList.stream().map(ContactInfoDTO::new).toList();
    }
    @Transactional
    public List<ContactInfo> getAllContact(){
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();

        if (contactInfoList.isEmpty()) {
            throw new CustomMessage("No contact information found in the database", HttpStatus.NOT_FOUND);
        }

        return contactInfoList;    }

    public void createContact(ContactInfo contactInfo) {
        contactInfoRepository.save(contactInfo);
    }

    public void deleteContactById(int id) {
        if(!contactInfoRepository.existsById(id)){
            throw new CustomMessage("ContactInfo not found", HttpStatus.NOT_FOUND);

        }
        contactInfoRepository.deleteById(id);
    }
}
