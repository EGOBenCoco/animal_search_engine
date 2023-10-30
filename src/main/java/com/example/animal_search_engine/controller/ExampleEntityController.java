package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.model.ExampleEntity;
import com.example.animal_search_engine.service.ExampleEntityService;
import com.example.animal_search_engine.service.S3FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/entity")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ExampleEntityController {

    ExampleEntityService exampleEntityService;
    S3FileService s3FileService;

    @PostMapping("/add")
    public ResponseEntity<String> createExampleEntity(@RequestBody ExampleEntity entity) {
        exampleEntityService.createEntityWithoutPhoto(entity);
        return  ResponseEntity.ok("Create");
    }
    @PostMapping("/{entityId}/add-photo")
    public ResponseEntity<String> addPhotoToEntityById(
            @PathVariable int entityId,
            @RequestParam("file") MultipartFile file
    ) {
        exampleEntityService.addPhotoToEntityById(entityId, file);

        return  ResponseEntity.ok("Add");
    }
}
