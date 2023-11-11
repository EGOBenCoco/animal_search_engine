package com.example.animal_search_engine.dto.request;

import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.model.ContactInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumerRequest {
    int id;
    String firstName;
    String lastName;
    String email;
    String password;
    List<ContactInfo> contactInfos;

    public ConsumerRequest(Consumer consumer){
        this.id= consumer.getId();
        this.firstName = consumer.getFirstName();
        this.lastName = consumer.getLastName();
        this.email = consumer.getEmail();
        this.password = consumer.getPassword();
        this.contactInfos = consumer.getContactInfos();
    }
}
