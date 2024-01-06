package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.model.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Sql({"/data.sql"})
class CommentRepositoryTest {
    @Container
    static MySQLContainer postgresqlContainer = new MySQLContainer("mysql")
            .withDatabaseName("poc")
            .withUsername("sa")
            .withPassword("sa");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @Autowired
    private CommentRepository commentRepository;


    @DisplayName("Injected component repository is not null")
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