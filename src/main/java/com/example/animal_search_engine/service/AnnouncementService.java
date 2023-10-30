package com.example.animal_search_engine.service;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.example.animal_search_engine.dto.AnnouncementDTO;
import com.example.animal_search_engine.enums.AnimalBreed;
import com.example.animal_search_engine.enums.AnimalGender;
import com.example.animal_search_engine.enums.AnimalType;
import com.example.animal_search_engine.exception.CustomMessage;
import com.example.animal_search_engine.model.Announcement;
import com.example.animal_search_engine.repository.AnnouncementRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnnouncementService {

    AnnouncementRepository announcementRepository;

    S3FileService s3FileService;

    @Transactional
    public List<String> getPhotosById(int entityId) {
        Announcement announcement = announcementRepository.findAnnouncementWithConsumer(entityId);
        if (announcement == null) {
            throw new CustomMessage("Announcement not found", HttpStatus.NOT_FOUND);
        }
        return announcement.getPhotoUrls();
    }

    @Transactional
    public Optional<AnnouncementDTO> getAnnouncementById(int announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new CustomMessage("Announcement not found", HttpStatus.NOT_FOUND));
        return Optional.of(new AnnouncementDTO(announcement));
    }

    @Transactional
    public Page<AnnouncementDTO> filterAnnouncements(
            AnimalType animalType, AnimalBreed animalBreed, AnimalGender animalGender, Pageable pageable
    ) {
        Page<Announcement> announcementPage = announcementRepository.findByFilters(animalType, animalBreed, animalGender, pageable);
        if (announcementPage.isEmpty()) {
            throw new CustomMessage("No announcements found", HttpStatus.NOT_FOUND);
        }
        return announcementPage.map(AnnouncementDTO::new);


    }

    @Transactional
    public Page<AnnouncementDTO> getAllAnnouncement(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Announcement> announcementPage = announcementRepository.findAll(pageable);
        if (announcementPage.isEmpty()) {
            throw new CustomMessage("No announcements found", HttpStatus.NOT_FOUND);
        }
        return announcementPage.map(AnnouncementDTO::new);
    }

    public void createAnnouncement(Announcement announcement) {
        announcementRepository.save(announcement);
    }

    public void updateAnnouncement(Announcement announcement) {
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

        Announcement announcement = announcementRepository.findAnnouncementWithConsumer(announcementId);

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
}