package com.example.animal_search_engine.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    String lastName;

    String email;

    @OneToMany(mappedBy = "consumer",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<Announcement> announcements;

    @OneToMany(mappedBy = "consumer",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<ContactInfo> contactInfos;


}
