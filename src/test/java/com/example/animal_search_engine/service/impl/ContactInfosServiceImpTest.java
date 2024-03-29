package com.example.animal_search_engine.service.impl;

import com.example.animal_search_engine.enums.ContactType;
import com.example.animal_search_engine.exception.CustomException;
import com.example.animal_search_engine.model.ContactInfo;
import com.example.animal_search_engine.repository.ContactInfosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactInfosServiceImpTest {

    private static final int EXISTING_CONTACT_ID = 1;
    private static final int NON_EXISTING_ID = 2;
    private static final int EXISTING_CONSUMER_ID = 1;


    @Mock
    private ContactInfosRepository contactInfosRepository;

    @InjectMocks
    private ContactInfosServiceImp contactInfosServiceImp;

    private ContactInfo expectedContact;


    @BeforeEach
    public void setUp(){
       expectedContact = ContactInfo.builder()
                .id(1)
                .type(ContactType.EMAIL)
                .value("example@gmail.com")
                .build();
    }


    @Test
    void should_retrieve_contact_info_by_id(){

        when(contactInfosRepository.findById(EXISTING_CONTACT_ID)).thenReturn(Optional.of(expectedContact));

        ContactInfo actualContact = contactInfosServiceImp.getById(EXISTING_CONTACT_ID);

        assertThat(actualContact).isNotNull();
        assertEquals(expectedContact.getValue(),actualContact.getValue());
    }

    @Test
    void should_throw_exception_when_contact_info_not_found(){

        when(contactInfosRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, ()-> contactInfosServiceImp.getById(NON_EXISTING_ID));
    }

    @Test
    void should_retrieve_contact_infos_for_consumer(){
        List<ContactInfo> expiredContact = List.of(expectedContact);

        when(contactInfosRepository.findByConsumerId(EXISTING_CONSUMER_ID)).thenReturn(List.of(expectedContact));

        List<ContactInfo> actualContact = contactInfosServiceImp.getByConsumerId(EXISTING_CONSUMER_ID);

        assertThat(actualContact).isNotNull();
        assertEquals(expiredContact.size(),actualContact.size());
    }


    @Test
    void should_return_empty_list_if_consumer_has_no_contact_infos(){
        when(contactInfosRepository.findByConsumerId(EXISTING_CONSUMER_ID)).thenReturn(Collections.emptyList());

        assertThrows(CustomException.class, () -> contactInfosServiceImp.getByConsumerId(EXISTING_CONSUMER_ID));
    }


    @Test
    void should_update_existing_contact_info() {

        when(contactInfosRepository.existsById(EXISTING_CONTACT_ID)).thenReturn(true);
        when(contactInfosRepository.save(expectedContact)).thenReturn(expectedContact);

        expectedContact.setValue("newexample@gmail.com");

        contactInfosServiceImp.update(expectedContact);

        verify(contactInfosRepository, times(1)).save(expectedContact);
        assertThat(expectedContact.getValue()).isEqualTo("newexample@gmail.com");
        assertThat(expectedContact).isNotNull();

    }

    @Test
    void should_throw_exception_when_updating_non_existing_contact_info(){
        when(contactInfosRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(CustomException.class, () -> contactInfosServiceImp.update(expectedContact));

        verify(contactInfosRepository, never()).save(any(ContactInfo.class));
    }


    @Test
    void should_create_contact_info() {

        when(contactInfosRepository.save(expectedContact)).thenReturn(expectedContact);

       contactInfosServiceImp.create(expectedContact);

        assertThat(expectedContact).isNotNull();
        verify(contactInfosRepository,times(1)).save(expectedContact);
    }




    @Test
    void should_delete_contact_info(){

        when(contactInfosRepository.existsById(EXISTING_CONTACT_ID)).thenReturn(true);
        willDoNothing().given(contactInfosRepository).deleteById(EXISTING_CONTACT_ID);

        contactInfosServiceImp.delete(EXISTING_CONTACT_ID);

        verify(contactInfosRepository, times(1)).deleteById(EXISTING_CONTACT_ID);

    }

    @Test
    void should_throw_exception_when_deleting_non_existing_contact_info(){
        when(contactInfosRepository.existsById(NON_EXISTING_ID)).thenReturn(false);

        assertThrows(CustomException.class, () -> contactInfosServiceImp.delete(NON_EXISTING_ID));

        verify(contactInfosRepository, Mockito.never()).deleteById(NON_EXISTING_ID);
    }
}