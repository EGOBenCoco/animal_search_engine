package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.model.ExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleEntityRepository extends JpaRepository<ExampleEntity,Integer> {
}
