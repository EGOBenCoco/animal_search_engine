package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.dto.responce.CommentResponce;
import com.example.animal_search_engine.model.Comment;
import com.example.animal_search_engine.service.impl.CommentServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CommentController {

    CommentServiceImp commentServiceImp;

    @GetMapping("/post/{id}")
    public ResponseEntity<Page<CommentResponce>> getCommentsByAnnouncementId(@PathVariable int id,
                                                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(commentServiceImp.getCommentsByAnnouncementId(id,page, size));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<CommentResponce>> getCommentsByConsumerId(@PathVariable int id,
                                                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                                                         @RequestParam(name = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(commentServiceImp.getCommentsByConsumerId(id,page, size));
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<Comment> getCommentsById(@PathVariable int id){
        return ResponseEntity.ok(commentServiceImp.getCommentById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createComment(@RequestBody Comment comment){
        commentServiceImp.createComment(comment);
        return ResponseEntity.ok("Create");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateComment(@RequestBody Comment comment){
        commentServiceImp.updateComment(comment);
        return ResponseEntity.ok("Update");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCommentById(int id){
        commentServiceImp.deleteCommentById(id);
        return ResponseEntity.ok("Delete");
    }
}
