package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.exception.SuccessMessage;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.model.ContactInfo;
import com.example.animal_search_engine.service.ConsumerService;
import com.example.animal_search_engine.service.ContactInfosService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ContactInfoController {

    ContactInfosService contactInfosService;

    @GetMapping("/{consumer-id}/consumer")
    public ResponseEntity<List<ContactInfo>> byUser(@PathVariable("consumer-id") int id){
        return ResponseEntity.ok(contactInfosService.getByConsumerId(id));
    }


    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ContactInfo contactInfo){
        contactInfosService.create(contactInfo);
        return ResponseEntity.ok(SuccessMessage.builder()
                .status(HttpStatus.OK.value())
                .message("Contact created")
                .datetime(LocalDateTime.now())
                .build());
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody ContactInfo contactInfo){
        contactInfosService.update(contactInfo);
        return ResponseEntity.ok(SuccessMessage.builder()
                .status(HttpStatus.OK.value())
                .message("Contact updated")
                .datetime(LocalDateTime.now())
                .build());    }

    @DeleteMapping("/{contact-id}")
    public ResponseEntity<Object> deleteById(@PathVariable("contact-id") int id) {
            contactInfosService.delete(id);
        return ResponseEntity.ok(SuccessMessage.builder()
                .status(HttpStatus.OK.value())
                .message("Contact deleted")
                .datetime(LocalDateTime.now())
                .build());    }

}
