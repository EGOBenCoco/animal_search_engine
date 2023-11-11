package com.example.animal_search_engine.controller;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.example.animal_search_engine.dto.responce.AnnouncementResponce;
import com.example.animal_search_engine.enums.Breed;
import com.example.animal_search_engine.enums.Gender;
import com.example.animal_search_engine.enums.Type;
import com.example.animal_search_engine.model.Announcement;
import com.example.animal_search_engine.service.impl.AnnouncementServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

    AnnouncementServiceImp announcementServiceImp;
    @GetMapping("/{entityId}/photos")
    public ResponseEntity<List<String>> getPhotosForAnnouncement(@PathVariable int entityId) {
        List<String> photoUrls = announcementServiceImp.getPhotosByAnnouncementId(entityId);
        return new ResponseEntity<>(photoUrls, HttpStatus.OK);
    }
    @GetMapping("/{entityId}/post")
    public ResponseEntity<List<Announcement>> getAnnouncementByConsumerId(@PathVariable int entityId) {
       return ResponseEntity.ok(announcementServiceImp.getAnnouncementByConsumerId(entityId));
    }


    @PostMapping("/{entityId}/add-photo")
    public ResponseEntity<String> addPhotosToEntityById(
            @PathVariable int entityId,
            @RequestParam("files") List<MultipartFile> file
    ) {
        announcementServiceImp.addPhotoToEntityById(entityId, file);

        return ResponseEntity.ok("Photos added");
    }




    @GetMapping("/one/{id}")
    public ResponseEntity<Optional<Announcement>> getAnnouncementById(@PathVariable int id) {
        return ResponseEntity.ok(announcementServiceImp.getAnnouncementById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<AnnouncementResponce>> filterAnnouncements(
            @RequestParam(required = false) Type type,
            @RequestParam(required = false) Breed breed,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) String city,
            Pageable pageable
    ) {
        Page<AnnouncementResponce> announcements = announcementServiceImp.filterAnnouncements(
                type, breed, gender,city, pageable
        );
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AnnouncementResponce>> getAllAnnouncement(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<AnnouncementResponce> announcements = announcementServiceImp.getAllAnnouncement(page, size);
        return ResponseEntity.ok(announcements);
    }

    @PostMapping("/add")
    public ResponseEntity<String> createAnnouncement(@RequestBody Announcement announcement) {
        announcementServiceImp.createAnnouncement(announcement);
        return ResponseEntity.ok("Create");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAnnouncement(@RequestBody Announcement announcement) {
        announcementServiceImp.updateAnnouncement(announcement);
        return ResponseEntity.ok("Update");
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAnnouncementById(@PathVariable int id) {
        announcementServiceImp.deleteAnnouncement(id);
        return ResponseEntity.ok("Delete");
    }


/*    @DeleteMapping("/{announcementId}/photos")
    public ResponseEntity<String> deleteAnnouncementPhoto(
            @PathVariable int announcementId,
            @RequestPart(value = "url") String photoUrl) {

        try {
            announcementServiceImp.deleteAnnouncementPhoto(announcementId, photoUrl);
            return ResponseEntity.ok("Photo deleted successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete photo");
        }
    }*/
}
