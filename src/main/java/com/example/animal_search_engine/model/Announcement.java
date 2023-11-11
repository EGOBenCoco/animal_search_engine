package com.example.animal_search_engine.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String header;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")

    Consumer consumer;


    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    Animal animal;



    @OneToOne( cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    AnimalLocation animalLocation;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "announcement_photos", joinColumns = @JoinColumn(name = "announcement_id"))
    @Column(name = "photo_url")
    List<String> photoUrls = new ArrayList<>();

}
