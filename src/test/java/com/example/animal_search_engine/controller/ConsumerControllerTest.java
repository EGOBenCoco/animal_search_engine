package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.AbstractContainerBaseTest;
import com.example.animal_search_engine.dto.request.ConsumerUpdateRequest;
import com.example.animal_search_engine.dto.request.UpdatePasswordRequest;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.service.ConsumerService;
import com.example.animal_search_engine.security_utils.JwtService;
import com.example.animal_search_engine.security_utils.SecuredConsumerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class ConsumerControllerTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsumerService consumerService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private SecuredConsumerServiceImpl securedConsumerService;
    private static String url;

    private Consumer consumer;

    @BeforeEach
    public void init() {
        consumer = Consumer.builder()
                .id(1)
                .firstName("Kirill")
                .lastName("Kasyanov")
                .email("john@example.com")
                .build();
    }

    @BeforeAll
    public static void setUp() {
        url = "http://localhost:8080/api/v1/consumers";

    }

    @Test
    @WithMockUser
    void should_retrieve_consumer_by_id() throws Exception {

        int userId = 1;

        when(consumerService.getById(userId)).thenReturn(consumer);

        mockMvc.perform(get(url + "/{consumer-id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(consumer.getId())))
                .andExpect(jsonPath("$.firstName", is(consumer.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(consumer.getLastName())));
    }

    @Test
    public void should_update_consumer() throws Exception {
        int consumerId =1;
        ConsumerUpdateRequest consumerUpdateRequest = new ConsumerUpdateRequest(1, "Sam", "Winchester", "sam@gmail.com");

        doNothing().when(consumerService).update(consumerId,consumerUpdateRequest);

        mockMvc.perform(put(url+ "/{consumer-id}",consumerId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(consumerUpdateRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message", is("Consumer updated")));
    }

    @Test
    @WithMockUser("testUser")
    public void should_update_password() throws Exception {

        String testLogin = "test@gmail.com";
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.setOldPassword("213");
        updatePasswordRequest.setNewPassword("123");
        updatePasswordRequest.setConfirmPassword("123");

        doNothing().when(consumerService).updatePassword(testLogin,updatePasswordRequest);

        mockMvc.perform(patch(url + "/updatePassword")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(updatePasswordRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message", is("Password updated")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void should_delete_consumer_by_id() throws Exception {
        int idConsumer = 1;

        doNothing().when(consumerService).deleteById(idConsumer);


        mockMvc.perform(delete(url + "/{consumer-id}", idConsumer)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message", is("Consumer deleted")));


    }


}