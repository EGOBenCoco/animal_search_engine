package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {


    Page<Comment> findByAnnouncementId(int id, Pageable pageable);

    @EntityGraph(attributePaths = "announcement")
    Page<Comment> findByConsumerId(int id,Pageable pageable);

    @EntityGraph(attributePaths = {"consumer","announcement"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Comment> findById(int id);
}
