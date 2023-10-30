package com.example.animal_search_engine.dto;

import com.example.animal_search_engine.model.Announcement;
import com.example.animal_search_engine.model.Consumer;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AnnouncementDTO {
    int id;


    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    String title;

    ConsumerDTO consumerDTO;
    AnimalDTO animalDTO;
    List<AnimalLocationDTO> animalLocationDTOS;
    public AnnouncementDTO(Announcement announcement)
    {
        this.id = announcement.getId();
        this.createdAt = announcement.getCreatedAt();
        this.updatedAt = announcement.getUpdatedAt();
        this.consumerDTO = new ConsumerDTO(announcement.getConsumer());
        this.animalDTO = new AnimalDTO(announcement.getAnimal());
        this.animalLocationDTOS = announcement.getAnimalLocation().stream().map(AnimalLocationDTO::new).toList();
    }
}
