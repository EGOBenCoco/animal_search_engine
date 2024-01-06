package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.dto.responce.CommentResponce;
import com.example.animal_search_engine.model.Comment;
import com.example.animal_search_engine.service.CommentService;
import com.example.animal_search_engine.security_utils.JwtService;
import com.example.animal_search_engine.security_utils.SecuredConsumerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
//@ExtendWith(SpringExtension.class)
//@Import(SecurityConfiguration.class)
//@WebMvcTest(controllers = CommentController.class)

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;


    @MockBean
    private JwtService jwtService;

    @MockBean
    private SecuredConsumerServiceImpl securedConsumerService;

    private static String url;

    private Comment comment;

    @BeforeEach
    public void init(){
        comment = Comment.builder()
                .id(1)
                .text("Comment")
                .build();
    }


    @BeforeAll
    public static void setUp() {
        url = "http://localhost:8080/api/v1/comments";
    }

    @Test
    public void should_retrieve_comments_for_announcement() throws Exception {


        CommentResponce commentResponce = CommentResponce.builder()
                .id(1)
                .text("Test Comment")
                .build();
        when(commentService.getByAnnouncementId(1, 0, 10))
                .thenReturn(new PageImpl<>(List.of(commentResponce)));

        mockMvc.perform(get(url + "/{announcement-id}/announcement", 1)
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id",is (commentResponce.getId())))
                .andExpect(jsonPath("$.content[0].text",is (commentResponce.getText())));
    }


    @Test
    public void should_create_comment() throws Exception {

        doNothing().when(commentService).create(comment);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(comment))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())))
                .andExpect(jsonPath("$.message", is("Comment added")));
    }

    @Test
    public void should_update_comment() throws Exception {
        doNothing().when(commentService).update(comment);

        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(comment))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message", is("Comment updated")));
    }

    @Test
    public void should_delete_comment_by_id() throws Exception {
        int idComment = 1;

        doNothing().when(commentService).deleteById(idComment);


        mockMvc.perform(delete(url + "/{comment-id}", idComment)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message", is("Comment deleted")));
    }
}
