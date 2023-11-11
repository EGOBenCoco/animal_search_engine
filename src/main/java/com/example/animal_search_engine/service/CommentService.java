package com.example.animal_search_engine.service;

import com.example.animal_search_engine.dto.responce.CommentResponce;
import com.example.animal_search_engine.model.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<CommentResponce> getCommentsByAnnouncementId(int id, int page, int size);
    Page<CommentResponce> getCommentsByConsumerId(int id, int page, int size);
    Comment getCommentById(int id);

    void createComment(Comment comment);
    void updateComment(Comment comment);
    void deleteCommentById( int id);
}
