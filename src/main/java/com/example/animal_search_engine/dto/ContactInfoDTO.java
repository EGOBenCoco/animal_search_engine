package com.example.animal_search_engine.dto;

import com.example.animal_search_engine.model.ContactInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactInfoDTO {
    int id;
    String type;
    String value;

    public ContactInfoDTO(ContactInfo contactInfo) {
        this.id = contactInfo.getId();
        this.type = contactInfo.getContactType().name();
        this.value = contactInfo.getValue();

    }
}
