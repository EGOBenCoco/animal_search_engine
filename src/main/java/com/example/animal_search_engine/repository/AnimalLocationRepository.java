package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.model.AnimalLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalLocationRepository extends JpaRepository<AnimalLocation,Integer> {
}
