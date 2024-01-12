package com.example.animal_search_engine.service.impl;

import com.example.animal_search_engine.dto.responce.AnnouncementResponce;
import com.example.animal_search_engine.enums.Breed;
import com.example.animal_search_engine.enums.Gender;
import com.example.animal_search_engine.enums.Type;
import com.example.animal_search_engine.exception.CustomException;
import com.example.animal_search_engine.model.Announcement;
import com.example.animal_search_engine.repository.AnnouncementRepository;
import com.example.animal_search_engine.service.AnnouncementService;
import com.example.animal_search_engine.service.S3FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnnouncementServiceImp implements AnnouncementService {

    AnnouncementRepository announcementRepository;

    S3FileService s3FileService;

    @Transactional(readOnly = true)
    public List<String> getPhotosByAnnouncementId(int announcementId) {
        Optional<Announcement> optionalAnnouncement = announcementRepository.findByIdWithPhotoUrls(announcementId);
        if (optionalAnnouncement.isEmpty()) {
            throw new CustomException("Announcement not found", HttpStatus.NOT_FOUND);
        }
        List<String> photoUrls = optionalAnnouncement.get().getPhotoUrls();
        if (photoUrls.isEmpty()) {
            throw new CustomException("No photos found for the given announcement", HttpStatus.NOT_FOUND);
        }
        return optionalAnnouncement.get().getPhotoUrls();

    }

    @Transactional(readOnly = true)
    public Page<AnnouncementResponce> getByConsumerId(int consumerId, int page,int size) {
        Page<Announcement> announcementPage = announcementRepository.findAllByConsumerId(consumerId, PageRequest.of(page,size));
        if (announcementPage.isEmpty()) {
            throw new CustomException("Announcement not found", HttpStatus.NOT_FOUND);
        }
        return announcementPage.map(AnnouncementResponce::new);
    }

    @Transactional(readOnly = true)
    public Announcement getById(int announcementId) {

        return announcementRepository.findById(announcementId).orElseThrow(()->new CustomException("Announcement not found",HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnouncementResponce> filter(Type type,
                                             Breed breed,
                                             Gender gender,
                                             String city,
                                             int page,
                                             int size) {
        Page<Announcement> announcements = announcementRepository.findByFilters(type, breed, gender, city, PageRequest.of(page,size));
        if (announcements.isEmpty()) {
            throw new CustomException("No announcements found with the specified criteria", HttpStatus.NOT_FOUND);
        }

        return announcements.map(AnnouncementResponce::new);
    }

    @Transactional(readOnly = true)
    public Page<AnnouncementResponce> getAll(int page, int size) {
        Page<Announcement> announcementPage = announcementRepository.findAllAnnouncements(PageRequest.of(page, size));
        if (announcementPage.isEmpty()) {
            throw new CustomException("No announcements found", HttpStatus.NOT_FOUND);
        }
        return announcementPage.map(AnnouncementResponce::new);
    }

    public void create(Announcement announcement) {
        announcementRepository.save(announcement);
    }

    public void update(Announcement announcement) {
        if(announcement.getId()!=0 && !announcementRepository.existsById(announcement.getId())){
            throw new CustomException("There is no entity with such ID.",HttpStatus.NOT_FOUND);
        }
        announcementRepository.save(announcement);
    }

    public void delete(int announcementId) {
        if(!announcementRepository.existsById(announcementId)){
            throw new CustomException("No announcements found", HttpStatus.NOT_FOUND);
        }
        announcementRepository.deleteById(announcementId);
    }

    @Override
    public void addPhotoById(int announcementId, List<MultipartFile> files) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new CustomException("Announcement not found", HttpStatus.NOT_FOUND));

        List<String> newPhotoUrls = files.stream()
                .filter(file -> file != null && !file.isEmpty())
                .map(file -> {
                    String s3Key = "announcement/" + UUID.randomUUID() + "/" + file.getOriginalFilename();
                    return s3FileService.uploadFile(file, s3Key);
                })
                .toList();

        announcement.getPhotoUrls().addAll(newPhotoUrls);
        announcementRepository.save(announcement);
    }


    @Override
    public void deletePhotoById(int announcementId, String photoUrl) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new CustomException("Announcement not found", HttpStatus.NOT_FOUND));

        List<String> photoUrls = announcement.getPhotoUrls();

        if (photoUrls.contains(photoUrl)) {
            s3FileService.deleteFile(photoUrl);
            photoUrls.remove(photoUrl);
            announcementRepository.save(announcement);
        } else {
            throw new CustomException("Photo not found for the given announcement", HttpStatus.NOT_FOUND);
        }
    }

}
