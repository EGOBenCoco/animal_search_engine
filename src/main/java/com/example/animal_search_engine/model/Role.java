package com.example.animal_search_engine.model;

import com.example.animal_search_engine.enums.EnumRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;


    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_name")
    EnumRole name;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Role otherRole = (Role) obj;
        return Objects.equals(id, otherRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
