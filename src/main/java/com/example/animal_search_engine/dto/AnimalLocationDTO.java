package com.example.animal_search_engine.dto;

import com.example.animal_search_engine.model.AnimalLocation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimalLocationDTO {
    int id;

    double latitude; // Широта
    double longitude; // Долгота

    public AnimalLocationDTO(AnimalLocation animalLocation){
        this.id=animalLocation.getId();
        this.latitude = animalLocation.getLatitude();
        this.longitude = animalLocation.getLongitude();
    }
}
