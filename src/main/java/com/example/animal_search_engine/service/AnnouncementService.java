package com.example.animal_search_engine.service;

import com.example.animal_search_engine.dto.responce.AnnouncementResponce;
import com.example.animal_search_engine.enums.Breed;
import com.example.animal_search_engine.enums.Gender;
import com.example.animal_search_engine.enums.Type;
import com.example.animal_search_engine.model.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AnnouncementService {
    List<String> getPhotosByAnnouncementId(int entityId);
    List<Announcement> getAnnouncementByConsumerId(int entityId);
    Optional<Announcement> getAnnouncementById(int announcementId);
    Page<AnnouncementResponce> filterAnnouncements(
            Type type, Breed breed, Gender gender,String city, Pageable pageable
    );
    Page<AnnouncementResponce> getAllAnnouncement(int page, int size);
    void createAnnouncement(Announcement announcement);

    void updateAnnouncement(Announcement announcement);
    void deleteAnnouncement(int announcementId);

    void addPhotoToEntityById(int announcementId, List<MultipartFile> files);


}
