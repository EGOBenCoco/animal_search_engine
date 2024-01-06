package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.model.Consumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Sql({"/data.sql"})
class ConsumerRepositoryTest {

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
    private ConsumerRepository consumerRepository;


    @DisplayName("Injected component repository is not null")
    @Test
    public void componentRepositoryNotNull() {

        assertThat(consumerRepository).isNotNull();
    }


    @Test
    void should_retrieve_consumer_by_email() {
        Optional<Consumer> foundConsumer = consumerRepository.findByEmail("kirillkasanov291@gmail.com");

        assertThat(foundConsumer).isPresent();
        assertThat(foundConsumer.get().getEmail()).isEqualTo("kirillkasanov291@gmail.com");
    }

    @Test
    void should_correctly_indicate_existing_consumer_by_email() {
        boolean exists = consumerRepository.existsByEmail("kirillkasanov291@gmail.com");
        assertThat(exists).isTrue();
    }
    @Test
    void should_retrieve_consumer_by_id() {
        int validId = 1;
        Optional<Consumer> foundConsumer = consumerRepository.findById(validId);

        assertThat(foundConsumer).isPresent();
        assertThat(foundConsumer.get().getFirstName()).isEqualTo("Kirill");
        assertThat(foundConsumer.get().getLastName()).isEqualTo("Kasyanov");
        assertThat(foundConsumer.get().getId()).isEqualTo(validId);
    }

}