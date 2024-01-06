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
    List<String> getPhotosByAnnouncementId(int announcementId);
    Page<AnnouncementResponce> getByConsumerId(int consumerId,int page,int size);
    Page<AnnouncementResponce> filter(
            Type type, Breed breed, Gender gender,String city, int size, int page);
    Page<AnnouncementResponce> getAll(int page, int size);
    void create(Announcement announcement);

    void update(Announcement announcement);
    void delete(int announcementId);

    void addPhotoById(int announcementId, List<MultipartFile> files);

    Announcement/*List<Announcement>*/ getById(int announcementId);
    void deletePhotoById(int announcementId, String photoUrl);
}
