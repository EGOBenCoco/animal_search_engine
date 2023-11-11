package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.dto.request.ConsumerRequest;
import com.example.animal_search_engine.dto.responce.ConsumerResponce;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.service.impl.ConsumerServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/consumer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConsumerController {

    ConsumerServiceImp consumerServiceImp;


    @GetMapping("/one/{id}")
    public ResponseEntity<Optional<Consumer>> getConsumerById(@PathVariable int id) {
        return ResponseEntity.ok(consumerServiceImp.getConsumerById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNewConsumer(@RequestBody Consumer consumer) {
        consumerServiceImp.createNewConsumer(consumer);
        return ResponseEntity.ok("Create");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateConsumer(@RequestBody ConsumerRequest consumer) {

        consumerServiceImp.updateConsumer(consumer);
        return ResponseEntity.ok("Update");
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteConsumerById(@PathVariable int id) {
        consumerServiceImp.deleteConsumerById(id);
        return ResponseEntity.ok("Delete");
    }
}
