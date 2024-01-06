package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.enums.EnumRole;
import com.example.animal_search_engine.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(EnumRole name);
}
