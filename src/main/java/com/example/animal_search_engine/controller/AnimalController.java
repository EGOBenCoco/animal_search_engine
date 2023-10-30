package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.dto.AnimalDTO;
import com.example.animal_search_engine.service.AnimalService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/api/animal")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AnimalController {

    AnimalService animalService;

    @GetMapping("/one/{id}")
    public ResponseEntity<Optional<AnimalDTO>> getAnimalById(@PathVariable int id){
        return ResponseEntity.ok(animalService.getAnimalById(id));
    }

    @GetMapping("/byPost/{id}")
    public ResponseEntity<Optional<AnimalDTO>> getAnimalByAnnouncement(@PathVariable int id){
        return ResponseEntity.ok(animalService.getAnimalsByAnnouncementId(id));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAnimal(@RequestBody AnimalDTO animalDTO){
        animalService.updateAnimal(animalDTO);
        return ResponseEntity.ok("Update");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAnimalById(@PathVariable int id) {
        animalService.deleteAnimalById(id);
        return ResponseEntity.ok("Delete");
    }
}
