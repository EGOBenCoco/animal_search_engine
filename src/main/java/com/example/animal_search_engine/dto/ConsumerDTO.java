package com.example.animal_search_engine.dto;

import com.example.animal_search_engine.model.Consumer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsumerDTO {
    int id;
    String name;
    String lastname;
    String email;
    List<ContactInfoDTO> contactInfoDTOList;

    public ConsumerDTO(Consumer consumer)
    {
        this.id= consumer.getId();
        this.name = consumer.getName();
        this.lastname = consumer.getLastName();
        this.email = consumer.getEmail();
        this.contactInfoDTOList = consumer.getContactInfos().stream().map(ContactInfoDTO::new).toList();
    }


}
