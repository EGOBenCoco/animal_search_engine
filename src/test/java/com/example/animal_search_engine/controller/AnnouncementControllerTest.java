package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.AbstractContainerBaseTest;
import com.example.animal_search_engine.dto.responce.AnnouncementResponce;
import com.example.animal_search_engine.enums.Breed;
import com.example.animal_search_engine.enums.Gender;
import com.example.animal_search_engine.enums.Type;
import com.example.animal_search_engine.model.Animal;
import com.example.animal_search_engine.model.AnimalLocation;
import com.example.animal_search_engine.model.Announcement;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.service.AnnouncementService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class AnnouncementControllerTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnnouncementService announcementService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private SecuredConsumerServiceImpl securedConsumerService;
    private static String url;
    public Announcement announcement;

    @BeforeEach
    public void init(){
        announcement = Announcement.builder()
                .id(1)
                .header("Post")
                .animal(mock(Animal.class))
                .animalLocation(mock(AnimalLocation.class))
                .consumer(mock(Consumer.class))
                .photoUrls(new ArrayList<>(List.of("urls")))
                .build();
    }

    @BeforeAll
    public static void setUp() {
        url = "http://localhost:8080/api/v1/announcements";
    }

    @Test
    public void should_retrieve_photos_for_announcement() throws Exception {
        int announcementId = 1;
        List<String> photoUrls = List.of("https://example.com/photo.jpg");

        when(announcementService.getPhotosByAnnouncementId(announcementId)).thenReturn(photoUrls);

        mockMvc.perform(get(url + "/{announcement-id}/photos", announcementId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(photoUrls.size()))
                .andExpect(jsonPath("$[0]").value("https://example.com/photo.jpg"));
    }

    @Test
    public void should_retrieve_announcements_for_consumer() throws Exception {
        int consumerId = 1;
        AnnouncementResponce announcementResponce = AnnouncementResponce.builder()
                .id(1)
                .photoUrls(List.of("https://example.com/photo.jpg"))
                .header("Post")
                .build();

        when(announcementService.getByConsumerId(consumerId, 0, 10)).thenReturn(new PageImpl<>(List.of(announcementResponce)));

        mockMvc.perform(get(url + "/{consumer-id}/consumer", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(announcementResponce.getId()))
                .andExpect(jsonPath("$.content[0].header").value(announcementResponce.getHeader()));
    }

    @Test
    public void should_add_photos_to_announcement() throws Exception {
        int announcementId = 1;

        MockMultipartFile file = new MockMultipartFile(
                "files", "test-file.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());

        doNothing().when(announcementService).addPhotoById(announcementId,List.of(file));

        mockMvc.perform(
                        multipart(url + "/add-photo/{announcement-id}", 1)
                                .file(file)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())))
                .andExpect(jsonPath("$.message", is("Photo added")));
        verify(announcementService).addPhotoById(announcementId, Collections.singletonList(file));

    }

    @Test
    public void should_retrieve_announcement_by_id() throws Exception {
        int announcementId = 1;

        when(announcementService.getById(announcementId)).thenReturn( announcement);

        mockMvc.perform(get(url + "/{id}", announcementId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(announcement.getId()))
                .andExpect(jsonPath("$.header").value(announcement.getHeader()));
    }

    @Test
    public void should_filter_announcements() throws Exception {
        AnnouncementResponce announcementResponse = AnnouncementResponce.builder()
                .id(1)
                .header("Filtered Announcement")
                .build();
        Type type = Type.DOG;
        Breed breed = Breed.LABRADOR;
        Gender gender = Gender.MALE;
        String city = "New York";
        int page = 0;
        int size = 10;

        when(announcementService.filter(type,breed,gender,city,page,size)).thenReturn(new PageImpl<>(List.of(announcementResponse)));

        mockMvc.perform(get(url + "/filter")
                        .param("type", "DOG")
                        .param("breed", "LABRADOR")
                        .param("gender", "MALE")
                        .param("city", "New York")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.content[0].header",is(announcementResponse.getHeader())));
    }

    @Test
    public void should_retrieve_all_announcements() throws Exception {
        AnnouncementResponce announcementResponse = AnnouncementResponce.builder()
                .id(1)
                .header("Filtered Announcement")
                .build();


        when(announcementService.getAll(0,10)).thenReturn(new PageImpl<>(List.of(announcementResponse)));

        mockMvc.perform(get(url)
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void should_create_announcement() throws Exception {

        doNothing().when(announcementService).create(announcement);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(announcement))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())))
                .andExpect(jsonPath("$.message", is("Announcement added")));
    }
    @Test
    public void should_update_announcement() throws Exception {

        doNothing().when(announcementService).update(announcement);

        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(announcement))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message", is("Announcement updated")));
    }

    @Test
    public void should_delete_announcement_by_id() throws Exception {
        int idAnnouncement = 1;

        doNothing().when(announcementService).delete(idAnnouncement);

        mockMvc.perform(delete(url + "/{announcement-id}", idAnnouncement)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message", is("Announcement deleted")));
    }
}