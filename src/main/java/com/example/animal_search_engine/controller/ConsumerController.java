package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.dto.ConsumerDTO;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.service.ConsumerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/consumer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ConsumerController {

    ConsumerService consumerService;

    @GetMapping("/one/{id}")
    public ResponseEntity<Optional<ConsumerDTO>> getConsumerById(@PathVariable int id)
    {
        return ResponseEntity.ok(consumerService.getConsumerById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNewConsumer(@RequestBody Consumer consumer)
    {
        consumerService.createNewConsumer(consumer);
        return ResponseEntity.ok("Create");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateConsumer(@RequestBody ConsumerDTO consumerDTO)
    {
        consumerService.updateConsumer(consumerDTO);
        return ResponseEntity.ok("Update");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteConsumerById(@PathVariable int id)
    {
        consumerService.deleteConsumerById(id);
        return ResponseEntity.ok("Delete");
    }
}
