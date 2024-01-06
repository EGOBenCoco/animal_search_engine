package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.dto.responce.CommentResponce;
import com.example.animal_search_engine.exception.SuccessMessage;
import com.example.animal_search_engine.model.Comment;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.service.CommentService;
import com.example.animal_search_engine.service.ConsumerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {

    CommentService commentService;
    @GetMapping("/{announcement-id}/announcement")
    public ResponseEntity<Page<CommentResponce>> getByAnnouncementId(@PathVariable("announcement-id") int id,
                                                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                                                     @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(commentService.getByAnnouncementId(id, page, size));
    }


    @GetMapping("/{comment-id}")
    public ResponseEntity<CommentResponce> getById(@PathVariable("comment-id") int id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Comment comment) {
        commentService.create(comment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessMessage.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Comment added")
                        .datetime(LocalDateTime.now())
                        .build());
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Comment comment) {
        commentService.update(comment);
        return ResponseEntity.ok(SuccessMessage.builder()
                .status(HttpStatus.OK.value())
                .message("Comment updated")
                .datetime(LocalDateTime.now())
                .build());
    }


    @DeleteMapping("/{comment-id}")
    public ResponseEntity<Object> deleteById(@PathVariable("comment-id") int id) {
        commentService.deleteById(id);
        return ResponseEntity.ok(SuccessMessage.builder()
                .status(HttpStatus.OK.value())
                .message("Comment deleted")
                .datetime(LocalDateTime.now())
                .build());
    }
}
