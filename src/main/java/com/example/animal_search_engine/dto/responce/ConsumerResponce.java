package com.example.animal_search_engine.dto.responce;

import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.model.ContactInfo;
import lombok.Data;

import java.util.List;

@Data
public class ConsumerResponce {

    int id;
    String firstName;
    String lastName;
    List<ContactInfo> contactInfos;

    public ConsumerResponce(Consumer consumer){
        this.id= consumer.getId();
        this.firstName = consumer.getFirstName();
        this.lastName = consumer.getLastName();
        this.contactInfos = consumer.getContactInfos();
    }

}
