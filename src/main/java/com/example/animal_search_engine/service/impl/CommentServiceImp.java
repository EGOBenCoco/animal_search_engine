package com.example.animal_search_engine.service.impl;

import com.example.animal_search_engine.dto.responce.CommentResponce;
import com.example.animal_search_engine.exception.CustomMessage;
import com.example.animal_search_engine.model.Comment;
import com.example.animal_search_engine.repository.CommentRepository;
import com.example.animal_search_engine.service.CommentService;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CommentServiceImp implements CommentService {

    CommentRepository commentRepository;


    @Transactional(readOnly = true)
    public Page<CommentResponce> getCommentsByAnnouncementId(int id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> announcementPage = commentRepository.findByAnnouncementId(id,pageable);
        if (announcementPage.isEmpty()) {
            throw new CustomMessage("No announcements found", HttpStatus.NOT_FOUND);
        }
        return announcementPage.map(CommentResponce::new);
    }


    @Transactional
    public Page<CommentResponce> getCommentsByConsumerId(int id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> announcementPage = commentRepository.findByConsumerId(id,pageable);
        if (announcementPage.isEmpty()) {
            throw new CustomMessage("No announcements found", HttpStatus.NOT_FOUND);
        }
        return announcementPage.map(CommentResponce::new);
    }



    @Transactional(readOnly = true)
    public Comment getCommentById(int id) {

       return commentRepository.findById(id)
                .orElseThrow(() -> new CustomMessage("Announcement not found", HttpStatus.NOT_FOUND));
        }

    @Override
    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteCommentById( int id){
        commentRepository.deleteById(id);
    }
}
