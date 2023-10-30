package com.example.animal_search_engine.model;

import com.example.animal_search_engine.enums.AnimalBreed;
import com.example.animal_search_engine.enums.AnimalGender;
import com.example.animal_search_engine.enums.AnimalType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;


    @Enumerated(EnumType.STRING)
    AnimalBreed animalBreed;

    @Enumerated(EnumType.STRING)
    AnimalType animalType;

    @Enumerated(EnumType.STRING)
    AnimalGender animalGender;

    int age;

    String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "announcement_id")
    Announcement announcement;

/*
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;*/

    /*String location;*/
}

