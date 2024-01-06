package com.example.animal_search_engine.service.impl;

import com.example.animal_search_engine.dto.responce.CommentResponce;
import com.example.animal_search_engine.exception.CustomException;
import com.example.animal_search_engine.model.Announcement;
import com.example.animal_search_engine.model.Comment;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.model.ContactInfo;
import com.example.animal_search_engine.repository.CommentRepository;
import com.example.animal_search_engine.service.AnnouncementService;
import com.example.animal_search_engine.service.ConsumerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImpTest {


    private static final int EXISTING_ANNOUNCEMENT_ID = 1;
    private static final int EXISTING_COMMENT_ID = 1;
    private static final int NON_EXISTING_ID = 2;
    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentServiceImp commentServiceImp;

    private Comment comment;

    @BeforeEach
    public void setup(){
        comment = Comment.builder()
                .id(1)
                .text("Comment")
                .consumer(mock(Consumer.class))
                .announcement(mock(Announcement.class))
                .build();
    }


    @Test
    void should_Return_Comment_Response_When_Existing_CommentId() {

        when(commentRepository.findById(EXISTING_COMMENT_ID)).thenReturn(Optional.of(comment));

        CommentResponce actualComment = commentServiceImp.getById(EXISTING_COMMENT_ID);

        assertThat(actualComment).isNotNull();
        assertEquals(comment.getId(), actualComment.getId());
        assertEquals(comment.getText(), actualComment.getText());
    }

        @Test
    public void should_Throw_Exception_For_Non_Existing_CommentId() {
        when(commentRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(CustomException.class,
                () -> commentServiceImp.getById(NON_EXISTING_ID),
                String.format("Comment not found by id %d  ", NON_EXISTING_ID));
    }

    @Test
    public void should_Return_Comments_Page_For_Valid_AnnouncementId() {
        int page = 0;
        int size = 10;
        List<Comment> commentList = List.of(comment);
        Page<Comment> commentPage = new PageImpl<>(commentList);

        when(commentRepository.findAllByAnnouncementId(EXISTING_ANNOUNCEMENT_ID, PageRequest.of(page, size)))
                .thenReturn(commentPage);


        Page<CommentResponce> resultPage = commentServiceImp.getByAnnouncementId(EXISTING_ANNOUNCEMENT_ID, page, size);

        Assertions.assertEquals(commentPage.getTotalElements(), resultPage.getTotalElements());
        Assertions.assertEquals(commentPage.getContent().size(), resultPage.getContent().size());

    }


    @Test
    public void should_Throw_Exception_For_Invalid_AnnouncementId() {
        int page = 0;
        int size = 10;

        Mockito.when(commentRepository.findAllByAnnouncementId(NON_EXISTING_ID, PageRequest.of(page, size)))
                .thenReturn(Page.empty());

        CustomException exception = Assertions.assertThrows(
                CustomException.class,
                () -> commentServiceImp.getByAnnouncementId(NON_EXISTING_ID, page, size)
        );

        Assertions.assertEquals("No comments found", exception.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());


    }



    @Test
    public void should_Save_Comment_OnCreate() {
        when(commentRepository.save(comment)).thenReturn(comment);

        commentServiceImp.create(comment);

        assertThat(comment).isNotNull();
        verify(commentRepository,times(1)).save(comment);
    }

    @Test
    public void should_Update_Existing_Comment() {

        when(commentRepository.existsById(EXISTING_COMMENT_ID)).thenReturn(true);
        when(commentRepository.save(comment)).thenReturn(comment);

        comment.setText("New comment");

        commentServiceImp.update(comment);

        verify(commentRepository, times(1)).save(comment);
        assertThat(comment.getText()).isEqualTo("New comment");
        assertThat(comment).isNotNull();

    }



    @Test
    public void should_Throw_Exception_For_Non_Existing_Comment_OnUpdate() {

        when(commentRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(CustomException.class, () -> commentServiceImp.update(comment));

        verify(commentRepository, never()).save(any(Comment.class));

    }

    @Test
    public void should_Delete_Comment_ById_For_Existing_Comment() {

        when(commentRepository.existsById(EXISTING_COMMENT_ID)).thenReturn(true);
        willDoNothing().given(commentRepository).deleteById(EXISTING_COMMENT_ID);

        commentServiceImp.deleteById(EXISTING_COMMENT_ID);

        verify(commentRepository,times(1)).deleteById(EXISTING_COMMENT_ID);

    }

    @Test
    public void should_ThrowException_For_NonExisting_Comment_Id_On_Delete() {

        when(commentRepository.existsById(NON_EXISTING_ID)).thenReturn(false);

        assertThrows(CustomException.class, () -> commentServiceImp.deleteById(NON_EXISTING_ID));

        verify(commentRepository,never()).deleteById(NON_EXISTING_ID);
    }

}