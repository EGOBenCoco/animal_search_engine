package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.model.Consumer;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer,Integer> {
    @Query("SELECT c FROM Consumer c JOIN FETCH c.contactInfos where c.id =:id")
    Optional<Consumer> findById(@Param("id") int id);

}
