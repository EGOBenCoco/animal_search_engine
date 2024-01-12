package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.AbstractContainerBaseTest;
import com.example.animal_search_engine.model.Comment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class CommentRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private CommentRepository commentRepository;
    @Test
    public void componentRepositoryNotNull() {

        assertThat(commentRepository).isNotNull();
    }
    @Test
    void should_retrieve_comment_by_id(){
        int validId = 1;
       Optional<Comment> foundComment = commentRepository.findById(validId);

        assertThat(foundComment).isPresent();
        assertThat(foundComment.get().getId()).isEqualTo(validId);
    }
}