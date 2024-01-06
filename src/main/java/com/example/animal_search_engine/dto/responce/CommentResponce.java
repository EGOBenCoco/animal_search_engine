package com.example.animal_search_engine.dto.responce;

import com.example.animal_search_engine.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.NIP;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponce {
    int id;
    String text;
    int consumerId;
    int announcementId;
    LocalDateTime createdAt;

    public CommentResponce(Comment comment){
        this.id = comment.getId();
        this.text = comment.getText();
        this.consumerId = comment.getConsumer().getId();
        this.announcementId = comment.getAnnouncement().getId();
        this.createdAt = comment.getCreatedAt();
    }

}