package com.example.animal_search_engine.service.impl;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.example.animal_search_engine.dto.responce.AnnouncementResponce;
import com.example.animal_search_engine.enums.Breed;
import com.example.animal_search_engine.enums.Gender;
import com.example.animal_search_engine.enums.Type;
import com.example.animal_search_engine.exception.CustomMessage;
import com.example.animal_search_engine.model.Announcement;
import com.example.animal_search_engine.repository.AnnouncementRepository;
import com.example.animal_search_engine.service.AnnouncementService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnnouncementServiceImp implements AnnouncementService {

    AnnouncementRepository announcementRepository;

    S3FileService s3FileService;


    @Transactional
    public List<String> getPhotosByAnnouncementId(int entityId) {
        Optional<Announcement> optionalAnnouncement = announcementRepository.findByIdWithPhotoUrls(entityId);
        if (optionalAnnouncement.isEmpty()) {
            throw new CustomMessage("Announcement not found", HttpStatus.NOT_FOUND);
        }
        return optionalAnnouncement.get().getPhotoUrls();
    }


    @Transactional(readOnly = true)
    public List<Announcement> getAnnouncementByConsumerId(int entityId) {
        List<Announcement> list = announcementRepository.findByConsumerId(entityId);
        if (list == null) {
            throw new CustomMessage("Announcement not found", HttpStatus.NOT_FOUND);
        }
        return list;
    }


    @Transactional
    public Optional<Announcement> getAnnouncementById(int announcementId) {
        return Optional.of(announcementRepository.findById(announcementId).orElseThrow(() -> new CustomMessage("Not found", HttpStatus.NOT_FOUND)));
    }

    @Transactional
    public Page<AnnouncementResponce> filterAnnouncements(Type type,
                                                          Breed breed,
                                                          Gender gender,
                                                          String city,
                                                          Pageable pageable) {
        return announcementRepository.findByFilters(type, breed, gender, city, pageable).map(AnnouncementResponce::new);
    }


    @Transactional
    public Page<AnnouncementResponce> getAllAnnouncement(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Announcement> announcementPage = announcementRepository.findAllAnnouncements(pageable);
        if (announcementPage.isEmpty()) {
            throw new CustomMessage("No announcements found", HttpStatus.NOT_FOUND);
        }
        return announcementPage.map(AnnouncementResponce::new);
    }

    public void createAnnouncement(Announcement announcement) {
        //announcement.getAnimalLocation().setAnnouncement(announcement);
        announcementRepository.save(announcement);
    }

    public void updateAnnouncement(Announcement announcement) {

        //   announcement.getAnimalLocation().setAnnouncement(announcement);
        announcementRepository.save(announcement);
    }

    public void deleteAnnouncement(int announcementId) {
        if (!announcementRepository.existsById(announcementId)) {
            throw new CustomMessage("No announcements found", HttpStatus.NOT_FOUND);
        }
        announcementRepository.deleteById(announcementId);
    }

    @Transactional
    public void addPhotoToEntityById(int announcementId, List<MultipartFile> files) {

        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(announcementId);
        Announcement announcement = optionalAnnouncement.get();

        List<String> newPhotoUrls = new ArrayList<>();
        if (announcement == null) {
            throw new CustomMessage("Announcement not found", HttpStatus.NOT_FOUND);
        }
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String s3Key = "announcement/" + UUID.randomUUID() + "/" + file.getOriginalFilename();
                String s3Url = s3FileService.uploadFile(file, s3Key);
                newPhotoUrls.add(s3Url);
            }
        }

        // Обновить список ссылок на фотографии в сущности
        announcement.getPhotoUrls().addAll(newPhotoUrls);

        // Сохранить обновленную сущность в базе данных
        announcementRepository.save(announcement);
    }

  /*  public void deleteAnnouncementPhoto(int announcementId, String photoUrl) {
        // Получите Announcement по announcementId из базы данных
        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(announcementId);

        if (optionalAnnouncement.isPresent()) {
            Announcement announcement = optionalAnnouncement.get();

            // Удалите фотографию из облака S3
            s3FileService.deleteFileFromS3Bucket(photoUrl);

            // Удалите URL фотографии из списка Announcement
            List<String> photoUrls = announcement.getPhotoUrls();
            photoUrls.remove(photoUrl);
            announcement.setPhotoUrls(photoUrls);

            // Сохраните обновленный Announcement в базе данных
             announcementRepository.save(announcement);
        } else {
            // Обработка случая, когда Announcement с заданным ID не найден
            throw new NotFoundException("Announcement with ID " + announcementId + " not found");
        }
    }*/

}