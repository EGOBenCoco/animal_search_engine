package com.example.animal_search_engine.service;

import com.example.animal_search_engine.dto.AnimalDTO;
import com.example.animal_search_engine.enums.AnimalType;
import com.example.animal_search_engine.exception.CustomMessage;
import com.example.animal_search_engine.model.Animal;
import com.example.animal_search_engine.repository.AnimalRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnimalService {

    AnimalRepository animalRepository;

    public Optional<AnimalDTO> getAnimalById(int id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new CustomMessage("Animal not found", HttpStatus.NOT_FOUND));;
        return Optional.of(new AnimalDTO(animal));
    }

    public Optional<AnimalDTO> getAnimalsByAnnouncementId(int id) {

        Animal animal = animalRepository.findByAnnouncementId(id)
                .orElseThrow(() -> new CustomMessage("Animal not found for this ad", HttpStatus.NOT_FOUND));;
        return Optional.of(new AnimalDTO(animal));
    }

    public void updateAnimal(AnimalDTO animalDTO) {
        Animal animal = animalRepository.findById(animalDTO.getId())
                .orElseThrow(() -> new CustomMessage("Animal not found", HttpStatus.NOT_FOUND));;
        animal.setAnimalType(animalDTO.getAnimalType());
        animal.setAnimalGender(animalDTO.getAnimalGender());
        animal.setAnimalBreed(animalDTO.getAnimalBreed());
        animal.setAge(animalDTO.getAge());
        animal.setDescription(animalDTO.getDescription());
        animalRepository.save(animal);
    }

    public void deleteAnimalById(int id) {
        if(!animalRepository.existsById(id)) {
            throw new CustomMessage("Animal not found", HttpStatus.NOT_FOUND);

        }
        animalRepository.deleteById(id);
    }
}
