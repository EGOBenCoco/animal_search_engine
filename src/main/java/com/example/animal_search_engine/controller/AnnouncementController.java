package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.dto.responce.AnnouncementResponce;
import com.example.animal_search_engine.enums.Breed;
import com.example.animal_search_engine.enums.Gender;
import com.example.animal_search_engine.enums.Type;
import com.example.animal_search_engine.exception.SuccessMessage;
import com.example.animal_search_engine.model.Announcement;
import com.example.animal_search_engine.service.AnnouncementService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/v1/announcements")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnnouncementController {

    AnnouncementService announcementService;
    @GetMapping
    public ResponseEntity<Page<AnnouncementResponce>> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "10") int size){

        return ResponseEntity.ok(announcementService.getAll(page,size));
    }


    @GetMapping("/{announcement-id}/photos")
    public ResponseEntity<List<String>> getPhotosById(@PathVariable("announcement-id") int entityId) {
        List<String> photoUrls = announcementService.getPhotosByAnnouncementId(entityId);
        return new ResponseEntity<>(photoUrls, HttpStatus.OK);
    }

    @GetMapping("/{consumer-id}/consumer")
    public ResponseEntity<Page<AnnouncementResponce>> getByConsumerId(@PathVariable("consumer-id") int entityId,
                                                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                                                      @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<AnnouncementResponce> announcements = announcementService.getByConsumerId(entityId,page,size);
        return ResponseEntity.ok(announcements);
    }
    @GetMapping("/filter")
    public ResponseEntity<Page<AnnouncementResponce>> filter(
            @RequestParam(required = false) Type type,
            @RequestParam(required = false) Breed breed,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) String city,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<AnnouncementResponce> announcements = announcementService.filter(
                type, breed, gender, city, page,size
        );
        return ResponseEntity.ok(announcements);
    }

    @PostMapping("/add-photo/{announcement-id}")
    public ResponseEntity<Object> addPhotosToEntityById(
            @PathVariable("announcement-id") int entityId,
            @RequestParam("files") List<MultipartFile> file
    ) {
        announcementService.addPhotoById(entityId, file);

        return ResponseEntity.ok(SuccessMessage.builder()
                .status(HttpStatus.CREATED.value())
                .message("Photo added")
                .datetime(LocalDateTime.now())
                .build());
    }



    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Announcement announcement) {
        announcementService.create(announcement);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessMessage.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Announcement added")
                        .datetime(LocalDateTime.now())
                        .build());
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Announcement announcement) {
        announcementService.update(announcement);

        return ResponseEntity.ok(SuccessMessage.builder()
                .status(HttpStatus.OK.value())
                .message("Announcement updated")
                .datetime(LocalDateTime.now())
                .build());
    }


    @DeleteMapping("/{announcement-id}")
    public ResponseEntity<Object> deleteById(@PathVariable("announcement-id") int id) {
        announcementService.delete(id);
        return ResponseEntity.ok(SuccessMessage.builder()
                .status(HttpStatus.OK.value())
                .message("Announcement deleted")
                .datetime(LocalDateTime.now())
                .build());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Announcement> findAllWithCommentsAndTags(
            @PathVariable int id) {

      return ResponseEntity.ok(announcementService.getById(id));
    }


    @DeleteMapping("/{announcement-id}/delete-photo")
    public ResponseEntity<Object> deletePhotoFromEntityById(
            @PathVariable("announcement-id") int announcementId,
            @RequestParam("photo-url") String photoUrl
    ) {
        announcementService.deletePhotoById(announcementId, photoUrl);

        return ResponseEntity.ok(SuccessMessage.builder()
                .status(HttpStatus.OK.value())
                .message("Photo deleted")
                .datetime(LocalDateTime.now())
                .build());
    }

}
