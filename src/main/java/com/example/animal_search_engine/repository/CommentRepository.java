package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {



    @Query("SELECT c FROM Comment c ORDER BY c.createdAt DESC ")
    Page<Comment> findAllByAnnouncementId(int id, Pageable pageable);


}
