package com.example.animal_search_engine.dto.responce;

import com.example.animal_search_engine.model.Comment;
import lombok.Data;

@Data
public class CommentResponce {
    int id;

    String text;
    int consumerId;
    int announcementId;
    public CommentResponce(Comment comment){
        this.id = comment.getId();
        this.text = comment.getText();
        this.consumerId = comment.getConsumer().getId();
        this.announcementId = comment.getAnnouncement().getId();
    }

}