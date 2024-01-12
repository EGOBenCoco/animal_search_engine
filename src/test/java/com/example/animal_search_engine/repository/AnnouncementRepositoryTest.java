package com.example.animal_search_engine.repository;

import com.example.animal_search_engine.AbstractContainerBaseTest;
import com.example.animal_search_engine.enums.Breed;
import com.example.animal_search_engine.enums.Gender;
import com.example.animal_search_engine.enums.Type;
import com.example.animal_search_engine.model.Announcement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class AnnouncementRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Test
    void should_retrieve_announcements_for_consumer() {
        int consumerId = 1;
        int page = 0;
        int size = 10;

        Page<Announcement> announcements = announcementRepository.findAllByConsumerId(consumerId, PageRequest.of(page, size));

        assertEquals(size, announcements.getSize());
    }


    @Test
    void should_retrieve_announcement_by_id(){
        int announcementId =1;
        Optional<Announcement> foundAnnouncement = announcementRepository.findById(announcementId);
        assertThat(foundAnnouncement).isPresent();
    }

    @Test
    void should_retrieve_all_announcements() {
        int page = 0;
        int size = 10;

        Page<Announcement> announcements = announcementRepository.findAllAnnouncements(PageRequest.of(page, size));

        assertEquals(size, announcements.getSize());
    }

    @Test
    void should_filter_announcements_by_criteria() {
        Type type = Type.DOG;
        Breed breed = Breed.LABRADOR;
        Gender gender = Gender.MALE;
        String city = "NY";
        int page = 0;
        int size = 10;

        Page<Announcement> announcements = announcementRepository.findByFilters(type, breed, gender, city, PageRequest.of(page, size));

        assertEquals(size, announcements.getSize());
    }

    @Test
    void should_retrieve_announcement_with_photo_urls() {
        int announcementId = 1;

        Optional<Announcement> foundAnnouncement = announcementRepository.findByIdWithPhotoUrls(announcementId);

        assertTrue(foundAnnouncement.isPresent());
        Assertions.assertThat(foundAnnouncement.get().getId()).isEqualTo(announcementId);
    }


}