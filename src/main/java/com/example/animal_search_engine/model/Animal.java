package com.example.animal_search_engine.model;

import com.example.animal_search_engine.enums.Breed;
import com.example.animal_search_engine.enums.Gender;
import com.example.animal_search_engine.enums.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotBlank(message = "Breed is required")
    @Enumerated(EnumType.STRING)
    Breed breed;

    @NotBlank(message = "Type is required")
    @Enumerated(EnumType.STRING)
    Type type;

    @NotBlank(message = "Gender is required")
    @Enumerated(EnumType.STRING)
    Gender gender;

    @NotBlank(message = "Age is required")
    @Size(min = 1,max = 20,message = "The size should vary from 1 to 20")
    int age;

    @NotBlank(message = "Description is required")
    @Size(min = 1,max = 150,message = "The size should vary from 1 to 150")
    String description;

}

