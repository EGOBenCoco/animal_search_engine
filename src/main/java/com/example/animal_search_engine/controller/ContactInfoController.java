package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.dto.ContactInfoDTO;
import com.example.animal_search_engine.model.ContactInfo;
import com.example.animal_search_engine.service.ContactInfoService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ContactInfoController {

    ContactInfoService contactInfoService;

    @GetMapping("/one/{id}")
    public ResponseEntity<Optional<ContactInfo>> getContactById(@PathVariable int id){
        return ResponseEntity.ok(contactInfoService.getContactById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContactInfo>> getAllContact(){
        return ResponseEntity.ok(contactInfoService.getAllContact());
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<ContactInfoDTO>> getAllContactByConsumerId(@PathVariable int id){
        return ResponseEntity.ok(contactInfoService.getContactByConsumerId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createContact(@RequestBody ContactInfo contactInfo) {
        contactInfoService.createContact(contactInfo);
        return ResponseEntity.ok("Create");
    }

    @DeleteMapping("/delete/id")
    public ResponseEntity<String> deleteContactById(@PathVariable int id){
        contactInfoService.deleteContactById(id);
        return ResponseEntity.ok("Delete");
    }
}
