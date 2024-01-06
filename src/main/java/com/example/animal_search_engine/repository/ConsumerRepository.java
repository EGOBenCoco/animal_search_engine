package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.model.Consumer;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer,Integer> {

    @EntityGraph(attributePaths = {"contactInfos"})
   Optional <Consumer> findById(@Param("id") int id);

    @EntityGraph(attributePaths = {"contactInfos","roles"})
    Optional<Consumer> findByEmail(String email);

    boolean existsByEmail(String email);
}
