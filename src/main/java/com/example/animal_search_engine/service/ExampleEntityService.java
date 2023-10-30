package com.example.animal_search_engine.service;

import com.amazonaws.services.glue.model.EntityNotFoundException;
import com.example.animal_search_engine.model.ExampleEntity;
import com.example.animal_search_engine.repository.ExampleEntityRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class ExampleEntityService {
    ExampleEntityRepository exampleEntityRepository;
    S3FileService s3FileService;

    @Transactional
    public void addPhotoToEntityById(int entityId, MultipartFile file) {
        ExampleEntity entity = exampleEntityRepository.findById(entityId).orElse(null);

        if (file != null && !file.isEmpty()) {
            String s3Key = "example_entity/" + UUID
                    .randomUUID() + "/" + file.getOriginalFilename();
            String s3Url = s3FileService.uploadFile(file, s3Key);
            entity.setPhotoUrl(s3Url);
            exampleEntityRepository.save(entity);
        }
    }
    public void createEntityWithoutPhoto(ExampleEntity entity) {
        exampleEntityRepository.save(entity);
    }
}
