package com.example.animal_search_engine.model;

import com.example.animal_search_engine.enums.ContactType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @Column(name = "contact_type")
    ContactType type;

    @Column(name = "contact_value")
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
