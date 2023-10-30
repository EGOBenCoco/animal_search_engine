package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal,Integer> {

    Optional<Animal> findByAnnouncementId(int id);
}
