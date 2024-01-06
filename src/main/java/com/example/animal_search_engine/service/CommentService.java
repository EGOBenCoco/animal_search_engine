package com.example.animal_search_engine.service;

import com.example.animal_search_engine.dto.request.UpdatePasswordRequest;
import com.example.animal_search_engine.dto.responce.CommentResponce;
import com.example.animal_search_engine.model.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<CommentResponce> getByAnnouncementId(int announcementId, int page, int size);
    CommentResponce getById(int commentId);

    void create(Comment comment);
    void update(Comment comment);
    void deleteById(int commentId);
}
