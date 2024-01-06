package com.example.animal_search_engine.service.impl;

import com.example.animal_search_engine.dto.responce.AnnouncementResponce;
import com.example.animal_search_engine.dto.responce.CommentResponce;
import com.example.animal_search_engine.enums.Breed;
import com.example.animal_search_engine.enums.Gender;
import com.example.animal_search_engine.enums.Type;
import com.example.animal_search_engine.exception.CustomException;
import com.example.animal_search_engine.model.*;
import com.example.animal_search_engine.repository.AnnouncementRepository;
import com.example.animal_search_engine.service.S3FileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class AnnouncementServiceImpTest {

    private static final int EXISTING_ANNOUNCEMENT_ID = 1;
    private static final int EXISTING_CONSUMER_ID = 1;
    private static final int NON_EXISTING_ID = 2;

    @Mock
    private AnnouncementRepository announcementRepository;
    @Mock
    private S3FileService s3FileService;

    @InjectMocks
    private AnnouncementServiceImp announcementServiceImp;

    public Announcement announcement;

    @BeforeEach
    public void setup(){
       announcement = Announcement.builder()
               .id(1)
               .header("Post")
               .animal(mock(Animal.class))
               .animalLocation(mock(AnimalLocation.class))
               .consumer(mock(Consumer.class))
               .photoUrls(new ArrayList<>(List.of("urls")))
               .build();
    }

    @Test
    public void should_retrieve_photos_for_existing_announcement() {
        when(announcementRepository.findByIdWithPhotoUrls(EXISTING_ANNOUNCEMENT_ID)).thenReturn(Optional.of(announcement));

        List<String> result = announcementServiceImp.getPhotosByAnnouncementId(EXISTING_ANNOUNCEMENT_ID);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(announcement.getPhotoUrls(), result);
    }

    @Test
    public void should_throw_exception_when_announcement_not_found() {

        when(announcementRepository.findByIdWithPhotoUrls(NON_EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> announcementServiceImp.getPhotosByAnnouncementId(NON_EXISTING_ID));
    }

    @Test
    void should_throw_exception_when_announcement_has_no_photos() {
        announcement.setPhotoUrls(Collections.emptyList());

        when(announcementRepository.findByIdWithPhotoUrls(EXISTING_ANNOUNCEMENT_ID))
                .thenReturn(Optional.of(announcement));

        // Assert
        assertThrows(CustomException.class,
                () -> announcementServiceImp.getPhotosByAnnouncementId(EXISTING_ANNOUNCEMENT_ID),
                "Expected CustomException when no photos are found");

        // Verify
        verify(announcementRepository, times(1)).findByIdWithPhotoUrls(EXISTING_ANNOUNCEMENT_ID);
        verifyNoMoreInteractions(announcementRepository);
    }

    @Test
    void should_retrieve_announcements_for_consumer() {
        int page =0;
        int size = 10;
        List<Announcement> announcements = List.of(announcement);
        Page<Announcement> page1 = new PageImpl<>(announcements);

        when(announcementRepository.findAllByConsumerId(EXISTING_CONSUMER_ID,PageRequest.of(page,size))).thenReturn(page1);

        Page<AnnouncementResponce> resultPage = announcementServiceImp.getByConsumerId(EXISTING_ANNOUNCEMENT_ID,page,size);
        Assertions.assertEquals(page1.getTotalElements(), resultPage.getTotalElements());
        Assertions.assertEquals(page1.getContent().size(), resultPage.getContent().size());

    }

    @Test
    void should_return_empty_page_if_consumer_not_found() {
        int page = 0;
        int size = 10;

        Mockito.when(announcementRepository.findAllByConsumerId(NON_EXISTING_ID, PageRequest.of(page, size)))
                .thenReturn(Page.empty());

        CustomException exception = Assertions.assertThrows(
                CustomException.class,
                () -> announcementServiceImp.getByConsumerId(NON_EXISTING_ID, page, size)
        );

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void should_retrieve_announcement_by_id() {
        when(announcementRepository.findById(EXISTING_ANNOUNCEMENT_ID)).thenReturn(Optional.of(announcement));

        Announcement result = announcementServiceImp.getById(EXISTING_ANNOUNCEMENT_ID);

        assertThat(result).isNotNull();

    }

    @Test
    public void should_throw_exception_when_announcement_not_found_by_id() {

        when(announcementRepository.findById(NON_EXISTING_ID))
                .thenReturn(Optional.empty());
        assertThrows(CustomException.class, () -> announcementServiceImp.getById(NON_EXISTING_ID));
        verify(announcementRepository, times(1)).findById(NON_EXISTING_ID);

    }

    @Test
    public void should_filter_announcements_by_criteria() {
        Type type = Type.CAT;
        Breed breed = Breed.LABRADOR;
        Gender gender = Gender.MALE;
        String city = "New York";
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(0, 10);

        Page<Announcement> announcementPage = new PageImpl<>(List.of(announcement));

        when(announcementRepository.findByFilters(type, breed, gender, city,pageable )).thenReturn(announcementPage);

        Page<AnnouncementResponce> result = announcementServiceImp.filter(type, breed, gender, city, page,size);

        assertEquals(1, result.getTotalElements());
        assertThat(result).isNotNull();
    }

    @Test
    public void should_throw_exception_when_no_announcements_match_filters() {
        Type type = Type.CAT;
        Breed breed = Breed.LABRADOR;
        Gender gender = Gender.MALE;
        String city = "New York";
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(0, 10);

        when(announcementRepository.findByFilters(type, breed, gender, city, pageable))
                .thenThrow(new CustomException("Announcement not found", HttpStatus.NOT_FOUND));

        assertThrows(CustomException.class, () -> announcementServiceImp.filter(type, breed, gender, city, page,size));
    }


    @Test
    public void should_retrieve_all_announcements() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Announcement> announcementPage = new PageImpl<>(List.of(announcement));

        when(announcementRepository.findAllAnnouncements(pageable)).thenReturn(announcementPage);

        Page<AnnouncementResponce> result = announcementServiceImp.getAll(0, 10);

        assertEquals(announcementPage.getTotalElements(), result.getTotalElements());
    }

    @Test
    public void should_throw_exception_when_no_announcements_exist() {
        Pageable pageable = PageRequest.of(0, 10);

        when(announcementRepository.findAllAnnouncements(pageable)).thenReturn(new PageImpl<>(Collections.emptyList()));

        CustomException exception = assertThrows(CustomException.class, () -> announcementServiceImp.getAll(0, 10));

        verify(announcementRepository).findAllAnnouncements(pageable);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No announcements found", exception.getMessage());
    }



    @Test
    public void should_create_announcement() {
        when(announcementRepository.save(announcement)).thenReturn(announcement);

        announcementServiceImp.create(announcement);

        assertThat(announcement).isNotNull();
        verify(announcementRepository,times(1)).save(announcement);
    }

    @Test
    public void should_update_existing_announcement() {

        when(announcementRepository.existsById(EXISTING_ANNOUNCEMENT_ID)).thenReturn(true);
        when(announcementRepository.save(announcement)).thenReturn(announcement);

        announcement.setHeader("New title");
        announcementServiceImp.update(announcement);


        verify(announcementRepository, times(1)).save(announcement);
        assertThat(announcement.getHeader()).isEqualTo("New title");
        assertThat(announcement).isNotNull();
    }

    @Test
    void should_throw_exception_when_updating_non_existing_announcement() {

        when(announcementRepository.existsById(announcement.getId())).thenReturn(false);

        assertThrows(CustomException.class, () -> announcementServiceImp.update(announcement));

        verify(announcementRepository, never()).save(any(Announcement.class));
    }

    @Test
    public void should_delete_announcement() {
        when(announcementRepository.existsById(EXISTING_ANNOUNCEMENT_ID)).thenReturn(true);
        willDoNothing().given(announcementRepository).deleteById(EXISTING_ANNOUNCEMENT_ID);

        announcementServiceImp.delete(EXISTING_ANNOUNCEMENT_ID);

        verify(announcementRepository, times(1)).deleteById(EXISTING_ANNOUNCEMENT_ID);
    }

    @Test
    public void should_throw_exception_when_deleting_non_existing_announcement() {
        when(announcementRepository.existsById(NON_EXISTING_ID)).thenReturn(false);

        assertThrows(CustomException.class, () -> announcementServiceImp.delete(NON_EXISTING_ID));

        verify(announcementRepository,never()).deleteById(NON_EXISTING_ID);
    }



        @Test
        void should_add_photos_to_announcement() {

            List<MultipartFile> files = Arrays.asList(mock(MultipartFile.class), mock(MultipartFile.class));

            when(announcementRepository.findById(EXISTING_ANNOUNCEMENT_ID))
                    .thenReturn(Optional.of(announcement));

            when(s3FileService.uploadFile(any(), any())).thenReturn("mockedS3Url");

            announcementServiceImp.addPhotoById(EXISTING_ANNOUNCEMENT_ID, files);

            verify(announcementRepository, times(1)).findById(EXISTING_ANNOUNCEMENT_ID);
            verify(s3FileService, times(files.size())).uploadFile(any(), any());
            verify(announcementRepository, times(1)).save(announcement);
            assertEquals(3, announcement.getPhotoUrls().size()); // Assuming one file was mocked
        }

    @Test
    void should_throw_exception_when_adding_photos_to_non_existing_announcement() {
        List<MultipartFile> files = Arrays.asList(mock(MultipartFile.class), mock(MultipartFile.class));

        when(announcementRepository.findById(NON_EXISTING_ID))
                .thenReturn(Optional.empty());

        assertThrows(CustomException.class,
                () -> announcementServiceImp.addPhotoById(NON_EXISTING_ID, files),
                "Expected CustomException when announcement is not found");

        verify(announcementRepository, times(1)).findById(NON_EXISTING_ID);
        verifyNoMoreInteractions(s3FileService, announcementRepository);
    }


    @Test
    void should_delete_photo_from_announcement() {
        String urlPhoto = "urls";

        when(announcementRepository.findById(EXISTING_ANNOUNCEMENT_ID))
                .thenReturn(Optional.of(announcement));

        announcementServiceImp.deletePhotoById(EXISTING_ANNOUNCEMENT_ID,urlPhoto );

        verify(announcementRepository, times(1)).findById(EXISTING_ANNOUNCEMENT_ID);
        verify(s3FileService, times(1)).deleteFile(urlPhoto);
        verify(announcementRepository, times(1)).save(announcement);

        assertEquals(0, announcement.getPhotoUrls().size());
    }

    @Test
    void should_throw_exception_when_deleting_photo_from_non_existing_announcement() {
        String urlPhoto = "urls";
        when(announcementRepository.findById(NON_EXISTING_ID))
                .thenReturn(Optional.empty());

        assertThrows(CustomException.class,
                () -> announcementServiceImp.deletePhotoById(NON_EXISTING_ID,urlPhoto ),
                "Expected CustomException when announcement is not found");

        verify(announcementRepository, times(1)).findById(NON_EXISTING_ID);
        verifyNoMoreInteractions(s3FileService, announcementRepository);
    }

    @Test
    void should_throw_exception_when_deleting_non_existing_photo() {
        String urlPhoto = "urls";
        Announcement existingAnnouncement = new Announcement();
        existingAnnouncement.setId(EXISTING_ANNOUNCEMENT_ID);

        when(announcementRepository.findById(EXISTING_ANNOUNCEMENT_ID))
                .thenReturn(Optional.of(existingAnnouncement));

        assertThrows(CustomException.class,
                () -> announcementServiceImp.deletePhotoById(EXISTING_ANNOUNCEMENT_ID, urlPhoto),
                "Expected CustomException when photo is not found");

        verify(announcementRepository, times(1)).findById(EXISTING_ANNOUNCEMENT_ID);
        verifyNoMoreInteractions(s3FileService, announcementRepository);
    }

}