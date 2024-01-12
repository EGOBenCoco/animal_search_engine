package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.AbstractContainerBaseTest;
import com.example.animal_search_engine.model.Consumer;
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


@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ConsumerRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private ConsumerRepository consumerRepository;
    @Test
    public void componentRepositoryNotNull() {

        assertThat(consumerRepository).isNotNull();
    }


    @Test
    void should_retrieve_consumer_by_email() {
        Optional<Consumer> foundConsumer = consumerRepository.findByEmail("user@gmail.com");

        assertThat(foundConsumer).isPresent();
        assertThat(foundConsumer.get().getEmail()).isEqualTo("user@gmail.com");
    }

    @Test
    void should_correctly_indicate_existing_consumer_by_email() {
        boolean exists = consumerRepository.existsByEmail("user@gmail.com");
        assertThat(exists).isTrue();
    }
    @Test
    void should_retrieve_consumer_by_id() {
        int validId = 1;
        Optional<Consumer> foundConsumer = consumerRepository.findById(validId);

        assertThat(foundConsumer).isPresent();
        assertThat(foundConsumer.get().getFirstName()).isEqualTo("user");
        assertThat(foundConsumer.get().getId()).isEqualTo(validId);
    }

}