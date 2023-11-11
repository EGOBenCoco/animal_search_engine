package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.enums.Breed;
import com.example.animal_search_engine.enums.Gender;
import com.example.animal_search_engine.enums.Type;
import com.example.animal_search_engine.model.Announcement;
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

    @EntityGraph(attributePaths = {"consumer.contactInfos", "animal", "animalLocation"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT a FROM Announcement a  WHERE a.id = :id")
    Optional<Announcement> findById(@Param("id") int id);


    @EntityGraph(attributePaths = { "animal", "animalLocation"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT a FROM Announcement a JOIN FETCH a.consumer c JOIN FETCH c.contactInfos  WHERE c.id = :id")
    List<Announcement> findByConsumerId(@Param("id") int id);


    @Query("SELECT a FROM Announcement  a LEFT JOIN FETCH a.photoUrls ")
    Page<Announcement> findAllAnnouncements(Pageable pageable);


    @EntityGraph(attributePaths = "photoUrls")
 @Query("SELECT a FROM Announcement a WHERE " +
         "(:type IS NULL OR a.animal.type = :type) AND " +
         "(:breed IS NULL OR a.animal.breed = :breed) AND " +
         "(:gender IS NULL OR a.animal.gender = :gender) AND " +
         "(:city IS NULL OR a.animalLocation.city = :city)")
    Page<Announcement> findByFilters(
            @Param("type") Type type,
            @Param("breed") Breed breed,
            @Param("gender") Gender gender,
            @Param("city") String city,
            Pageable pageable
    );    @Query("select a from Announcement a join fetch a.photoUrls where a.id =:id")
    Optional<Announcement> findByIdWithPhotoUrls(@Param("id") int id);


}
