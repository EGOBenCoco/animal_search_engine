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

import java.util.Optional;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    @EntityGraph(attributePaths = {"photoUrls"})
    @Query("SELECT a FROM Announcement a  WHERE a.consumer.id = :id ORDER BY a.createdAt DESC")
    Page<Announcement> findAllByConsumerId(@Param("id") int id,Pageable pageable);

    @EntityGraph(attributePaths = {"consumer.contactInfos","animal","animalLocation","photoUrls"})
    Optional<Announcement> findById(int id);
    @Query("SELECT a FROM Announcement a JOIN FETCH a.photoUrls ORDER BY a.createdAt DESC ")
    Page<Announcement> findAllAnnouncements(Pageable pageable);


    @EntityGraph(attributePaths = {"photoUrls"})
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
    );



    @Query("select a from Announcement a JOIN FETCH a.photoUrls WHERE a.id =:id")
    Optional<Announcement> findByIdWithPhotoUrls(@Param("id") int id);


}
