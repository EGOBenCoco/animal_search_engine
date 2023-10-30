package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.enums.AnimalBreed;
import com.example.animal_search_engine.enums.AnimalGender;
import com.example.animal_search_engine.enums.AnimalType;
import com.example.animal_search_engine.model.Announcement;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Integer> {

    @Query("SELECT a FROM Announcement a JOIN FETCH a.animal JOIN FETCH a.consumer where a.id =:id")
    Optional<Announcement> findById(@Param("id") int id);

    @Query("SELECT a FROM Announcement a LEFT JOIN FETCH a.consumer LEFT JOIN  FETCH a.photoUrls WHERE a.id = :id")
    Announcement findAnnouncementWithConsumer(@Param("id") int id);

    @Query("SELECT a FROM Announcement a JOIN FETCH a.animal JOIN FETCH a.consumer  JOIN FETCH a.animalLocation ")
    List<Announcement> findAll();

    @Query("SELECT a FROM Announcement a WHERE " +
            "(:animalType IS NULL OR a.animal.animalType = :animalType) AND " +
            "(:animalBreed IS NULL OR a.animal.animalBreed = :animalBreed) AND " +
            "(:animalGender IS NULL OR a.animal.animalGender = :animalGender)")
    Page<Announcement> findByFilters(
            AnimalType animalType, AnimalBreed animalBreed, AnimalGender animalGender, Pageable pageable
    );
}
