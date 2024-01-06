package com.example.animal_search_engine.security_utils;

import com.example.animal_search_engine.model.Consumer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


public record ConsumerDetailsAdapter(Consumer consumer) implements ConsumerAdapter {



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return consumer.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .toList();
    }


    @Override
    public String getPassword() {
        return consumer.getPassword();
    }

    @Override
    public String getUsername() {
        return consumer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return consumer.isEnabled();

    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return consumer.isEnabled();
    }


}