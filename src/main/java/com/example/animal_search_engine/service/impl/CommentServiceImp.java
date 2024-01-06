package com.example.animal_search_engine.service.impl;

import com.example.animal_search_engine.dto.responce.CommentResponce;
import com.example.animal_search_engine.exception.CustomException;
import com.example.animal_search_engine.model.Announcement;
import com.example.animal_search_engine.model.Comment;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.repository.CommentRepository;
import com.example.animal_search_engine.service.AnnouncementService;
import com.example.animal_search_engine.service.CommentService;
import com.example.animal_search_engine.service.ConsumerService;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CommentServiceImp implements CommentService {

    CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public Page<CommentResponce> getByAnnouncementId(int announcementId, int page, int size) {
        Page<Comment> commentPage = commentRepository.findAllByAnnouncementId(announcementId,PageRequest.of(page, size));
        if (commentPage.isEmpty()) {
            throw new CustomException("No comments found", HttpStatus.NOT_FOUND);
        }
        return commentPage.map(CommentResponce::new);
    }


    @Transactional(readOnly = true)
    public CommentResponce getById(int commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if (comment.isEmpty()) {
            throw new CustomException(String.format("Comment not found by id %d", commentId), HttpStatus.NOT_FOUND);
        }
        return comment.map(CommentResponce::new).get();

    }

    @Override
    public void create(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void update(Comment comment) {
        if(comment.getId()!=0 && !commentRepository.existsById(comment.getId())){
            throw new CustomException("There is no entity with such ID in the database.",HttpStatus.NOT_FOUND);
        }
        commentRepository.save(comment);
    }

    public void deleteById(int commentId){
      if(!commentRepository.existsById(commentId)){
          throw new CustomException("Comment not found by id", HttpStatus.NOT_FOUND);
      }
      commentRepository.deleteById(commentId);
    }
}
