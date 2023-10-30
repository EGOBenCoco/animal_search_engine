package com.example.animal_search_engine.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnimalLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

        double latitude; // Широта
    double longitude; // Долгота

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id")
    Announcement announcement;
}