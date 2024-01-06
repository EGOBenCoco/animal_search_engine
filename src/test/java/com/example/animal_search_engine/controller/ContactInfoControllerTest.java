package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.enums.ContactType;
import com.example.animal_search_engine.model.ContactInfo;
import com.example.animal_search_engine.service.ContactInfosService;
import com.example.animal_search_engine.security_utils.JwtService;
import com.example.animal_search_engine.security_utils.SecuredConsumerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc
@SpringBootTest
class ContactInfoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactInfosService contactInfosService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private SecuredConsumerServiceImpl securedConsumerService;
    private static String url;



    @BeforeAll
    public static void setUp() {
        url = "http://localhost:8080/api/v1/contacts";
    }

    @Test
    void should_retrieve_contact_infos_for_user() throws Exception {
        int consumerId = 1;
        ContactInfo contactInfo = ContactInfo.builder()
                .id(1)
                .type(ContactType.EMAIL)
                .value("example@gmail.com")
                .build();

        when(contactInfosService.getByConsumerId(consumerId)).thenReturn(List.of(contactInfo));

        mockMvc.perform(get(url + "/{consumer-id}/consumer", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].type",is("EMAIL")))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void should_create_contact_info() throws Exception {
        ContactInfo contactInfo = ContactInfo.builder()
                .id(1)
                .type(ContactType.EMAIL)
                .value("example@gmail.com")
                .build();

        doNothing().when(contactInfosService).create(contactInfo);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(contactInfo))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message", is("Contact created")));

    }

    @Test
    void should_update_contact_info() throws Exception {
        ContactInfo contactInfo = ContactInfo.builder()
                .id(1)
                .type(ContactType.EMAIL)
                .value("example@gmail.com")
                .build();

        doNothing().when(contactInfosService).update(contactInfo);

        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(contactInfo))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message", is("Contact updated")));
    }

    @Test
    void should_delete_contact_info_by_id() throws Exception {
        int contactId = 1;

        doNothing().when(contactInfosService).delete(contactId);


        mockMvc.perform(delete(url + "/{comment-id}", contactId)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message", is("Contact deleted")));

    }
}