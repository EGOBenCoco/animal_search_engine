package com.example.animal_search_engine.dto.responce;

import com.example.animal_search_engine.model.Announcement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AnnouncementResponce {

    int id;
    String header;
    List<String> photoUrls;

    public AnnouncementResponce(Announcement announcement){
        this.id = announcement.getId();
        this.header = announcement.getHeader();
       this.photoUrls = announcement.getPhotoUrls();
    }
}
