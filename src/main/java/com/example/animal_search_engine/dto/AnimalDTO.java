package com.example.animal_search_engine.dto;

import com.example.animal_search_engine.enums.AnimalBreed;
import com.example.animal_search_engine.enums.AnimalGender;
import com.example.animal_search_engine.enums.AnimalType;
import com.example.animal_search_engine.model.Animal;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimalDTO {
    int id;


    AnimalBreed animalBreed;

    AnimalType animalType;

    AnimalGender animalGender;

    int age;

    String description;



    public AnimalDTO(Animal animal) {
        this.id = animal.getId();
        this.animalBreed = animal.getAnimalBreed();
        this.animalType = animal.getAnimalType();
        this.animalGender = animal.getAnimalGender();
        this.age = animal.getAge();
        this.description = animal.getDescription();
    }
}
