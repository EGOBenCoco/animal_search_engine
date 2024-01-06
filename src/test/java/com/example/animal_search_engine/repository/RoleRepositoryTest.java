package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.enums.EnumRole;
import com.example.animal_search_engine.model.Role;
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
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Sql({"/data.sql"})
class RoleRepositoryTest {

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
    private RoleRepository roleRepository;

    @Test
    void should_retrieve_role_by_name(){
        Optional<Role> roleOptional = roleRepository.findByName(EnumRole.valueOf(EnumRole.ROLE_USER.toString()));

        assertThat(roleOptional).isPresent();
        assertThat(roleOptional.get().getName()).isEqualTo(EnumRole.ROLE_USER);

    }
}