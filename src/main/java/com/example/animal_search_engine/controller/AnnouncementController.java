package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.dto.AnnouncementDTO;
import com.example.animal_search_engine.enums.AnimalBreed;
import com.example.animal_search_engine.enums.AnimalGender;
import com.example.animal_search_engine.enums.AnimalType;
import com.example.animal_search_engine.model.Announcement;
import com.example.animal_search_engine.service.AnnouncementService;
import com.example.animal_search_engine.service.S3FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnnouncementController {

    AnnouncementService announcementService;


    @GetMapping("/{entityId}/photos")
    public ResponseEntity<List<String>> getPhotosForAnnouncement(@PathVariable int entityId) {
        List<String> photoUrls = announcementService.getPhotosById(entityId);
        return new ResponseEntity<>(photoUrls, HttpStatus.OK);
    }

    @PostMapping("/{entityId}/add-photo")
    public ResponseEntity<String> addPhotosToEntityById(
            @PathVariable int entityId,
            @RequestParam("files") List<MultipartFile> file
    ) {
        announcementService.addPhotoToEntityById(entityId, file);

        return ResponseEntity.ok("Photos added");
    }


    @GetMapping("/one/{id}")
    public ResponseEntity<Optional<AnnouncementDTO>> getAnnouncementById(@PathVariable int id) {
        return ResponseEntity.ok(announcementService.getAnnouncementById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<AnnouncementDTO>> filterAnnouncements(
            @RequestParam(required = false) AnimalType animalType,
            @RequestParam(required = false) AnimalBreed animalBreed,
            @RequestParam(required = false) AnimalGender animalGender,
            Pageable pageable
    ) {
        Page<AnnouncementDTO> announcements = announcementService.filterAnnouncements(
                animalType, animalBreed, animalGender, pageable
        );
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AnnouncementDTO>> getAllAnnouncement(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<AnnouncementDTO> announcements = announcementService.getAllAnnouncement(page, size);
        return ResponseEntity.ok(announcements);
    }

    @PostMapping("/add")
    public ResponseEntity<String> createAnnouncement(@RequestBody Announcement announcement) {
        announcementService.createAnnouncement(announcement);
        return ResponseEntity.ok("Create");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAnnouncement(@RequestBody Announcement announcement) {
        announcementService.updateAnnouncement(announcement);
        return ResponseEntity.ok("Update");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAnnouncementById(@PathVariable int id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.ok("Delete");
    }

}
