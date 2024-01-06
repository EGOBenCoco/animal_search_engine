package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.dto.request.ConsumerUpdateRequest;
import com.example.animal_search_engine.dto.request.UpdatePasswordRequest;
import com.example.animal_search_engine.exception.SuccessMessage;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.service.ConsumerService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/v1/consumers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConsumerController {

    ConsumerService consumerService;



    @GetMapping("/{consumer-id}")
    public ResponseEntity<Consumer> getById(@PathVariable("consumer-id") int id) {
        return ResponseEntity.ok(consumerService.getById(id));
    }


    @PutMapping("/{consumer-id}")
    public ResponseEntity<Object> update(@PathVariable("consumer-id") int id, @RequestBody ConsumerUpdateRequest consumer) {
        consumerService.update(id,consumer);
        return ResponseEntity.ok(SuccessMessage.builder()
                .status(HttpStatus.OK.value())
                .message("Consumer updated")
                .datetime(LocalDateTime.now())
                .build());
    }

 @PatchMapping("/updatePassword")
 public ResponseEntity<Object> updatePassword(
             @AuthenticationPrincipal UserDetails userDetails,
         @RequestBody UpdatePasswordRequest updatePasswordRequest) {
     String login = userDetails.getUsername();
     consumerService.updatePassword(login, updatePasswordRequest);
     return ResponseEntity.ok(SuccessMessage.builder()
             .status(HttpStatus.OK.value())
             .message("Password updated")
             .datetime(LocalDateTime.now())
             .build());
    }


    @DeleteMapping("/{consumer-id}")
    public ResponseEntity<Object> deleteById(@PathVariable("consumer-id") int id) {
        consumerService.deleteById(id);
        return ResponseEntity.ok(SuccessMessage.builder()
                .status(HttpStatus.OK.value())
                .message("Consumer deleted")
                .datetime(LocalDateTime.now())
                .build());
    }

}
