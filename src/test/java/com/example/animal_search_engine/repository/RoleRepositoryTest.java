package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.AbstractContainerBaseTest;
import com.example.animal_search_engine.enums.EnumRole;
import com.example.animal_search_engine.model.Role;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class RoleRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void should_retrieve_role_by_name(){
        Optional<Role> foundRole = roleRepository.findByName(EnumRole.valueOf(EnumRole.ROLE_USER.toString()));

        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getName()).isEqualTo(EnumRole.ROLE_USER);

    }
}