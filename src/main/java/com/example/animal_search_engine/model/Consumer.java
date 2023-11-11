package com.example.animal_search_engine.model;

import com.amazonaws.services.identitymanagement.model.UserDetail;
import com.example.animal_search_engine.enums.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Consumer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    int id;

    String firstName;

    String lastName;

    String email;

    @JsonIgnore
    String password;

    @JsonManagedReference
    @OneToMany(mappedBy = "consumer", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    List<ContactInfo> contactInfos;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    Role role;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//remove list
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        // email in our case
        return email;
    }

    @JsonIgnore

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore

    @Override
    public boolean isEnabled() {
        return true;
    }

}
