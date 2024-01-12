package com.example.animal_search_engine.model;

import com.example.animal_search_engine.enums.ContactType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Enumerated(EnumType.STRING)
    ContactType type;

    String value;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "consumer_id")
    Consumer consumer;
    public ContactInfo(int id,ContactType type, String value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }
}
