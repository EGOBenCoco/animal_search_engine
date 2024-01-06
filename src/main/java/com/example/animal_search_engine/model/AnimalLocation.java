package com.example.animal_search_engine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "animal_location")
public class AnimalLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotBlank(message = "Latitude is required")
    double latitude;

    @NotBlank(message = "longitude is required")
    double longitude;

    @NotBlank(message = "City is required")
    String city;
}