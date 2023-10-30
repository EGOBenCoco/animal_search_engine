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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    Consumer consumer;

    @OneToOne(mappedBy ="announcement",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Animal animal;

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<AnimalLocation> animalLocation;

    //String photoUrl;

    @ElementCollection
    @Column(name = "photo_url")
    List<String> photoUrls = new ArrayList<>();
}
