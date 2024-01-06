package com.example.animal_search_engine.service.impl;

import com.example.animal_search_engine.enums.EnumRole;
import com.example.animal_search_engine.exception.CustomException;
import com.example.animal_search_engine.model.Role;
import com.example.animal_search_engine.repository.RoleRepository;
import com.example.animal_search_engine.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getUserRole() {
        return roleRepository.findByName(EnumRole.ROLE_USER).orElseThrow(()->new CustomException("Role not found", HttpStatus.NOT_FOUND));

    }
}