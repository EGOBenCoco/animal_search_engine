package com.example.animal_search_engine.service.impl;

import com.example.animal_search_engine.dto.request.ConsumerUpdateRequest;
import com.example.animal_search_engine.dto.request.UpdatePasswordRequest;
import com.example.animal_search_engine.exception.CustomException;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.repository.ConsumerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsumerServiceImpTest {
    private static final int EXISTING_CONSUMER_ID = 1;
    private static final int NON_EXISTING_ID = 2;
    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    ConsumerRepository consumerRepository;

    @InjectMocks
    @Spy
    ConsumerServiceImp consumerServiceImp;

    private Consumer expectedConsumer;

    @BeforeEach
    public void setUp() {
        expectedConsumer = Consumer.builder()
                .id(1)
                .firstName("Bob")
                .lastName("Singer")
                .email("bob@gmail.com")
                .password(passwordEncoder.encode("12345"))
                .build();
    }

    @Test
    public void should_Update_Password_When_OldPassword_IsCorrect() {
        String login = "bob@gmail.com";
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.setOldPassword("12345");
        updatePasswordRequest.setNewPassword("newPassword");
        updatePasswordRequest.setConfirmPassword("newPassword");

        when(consumerRepository.findByEmail(login)).thenReturn(Optional.of(expectedConsumer));
        when(passwordEncoder.matches(updatePasswordRequest.getOldPassword(), expectedConsumer.getPassword())).thenReturn(true);

        consumerServiceImp.updatePassword(login, updatePasswordRequest);

        verify(consumerRepository, times(1)).findByEmail(login);
        verify(passwordEncoder, times(1)).matches(updatePasswordRequest.getOldPassword(), expectedConsumer.getPassword());
        verify(consumerRepository, times(1)).save(expectedConsumer);
    }

    @Test
    public void should_Throw_Bad_Request_Exception_When_OldPassword_Is_Incorrect() {
        String login = "bob@gmail.com";
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.setOldPassword("wrongPassword");
        updatePasswordRequest.setNewPassword("newPassword");
        updatePasswordRequest.setConfirmPassword("newPassword");


        when(consumerRepository.findByEmail(login)).thenReturn(Optional.of(expectedConsumer));
        when(passwordEncoder.matches(updatePasswordRequest.getOldPassword(), expectedConsumer.getPassword())).thenReturn(false);

        assertThrows(CustomException.class, () -> consumerServiceImp.updatePassword(login, updatePasswordRequest));
    }

    @Test
    public void should_Throw_Not_Found_Exception_When_User_NotFound() {
        // Arrange
        String login = "test@example.com";
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.setOldPassword("12345");
        updatePasswordRequest.setNewPassword("newPassword");
        updatePasswordRequest.setConfirmPassword("newPassword");

        when(consumerRepository.findByEmail(login)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> consumerServiceImp.updatePassword(login, updatePasswordRequest));
    }


    @Test
    void should_Get_Consumer_ById_When_ExistingId() {

        when(consumerRepository.findById(EXISTING_CONSUMER_ID)).thenReturn(Optional.of(expectedConsumer));

        Consumer actualConsumer = consumerServiceImp.getById(EXISTING_CONSUMER_ID);

        assertThat(actualConsumer).isNotNull();
    }


    @Test
    void should_Throw_Exception_For_Non_Existing_ConsumerId() {
        when(consumerRepository.findById(EXISTING_CONSUMER_ID)).thenReturn(Optional.empty());

        assertThrows(
                CustomException.class,
                () -> consumerServiceImp.getById(EXISTING_CONSUMER_ID));

        verify(consumerRepository, times(1)).findById(EXISTING_CONSUMER_ID);
    }


    @Test
    void should_Update_Existing_Consumer() {
        ConsumerUpdateRequest consumerUpdateRequest = new ConsumerUpdateRequest(1, "Sam", "Winchester", "sam@gmail.com");

        when(consumerRepository.findById(expectedConsumer.getId())).thenReturn(Optional.of(expectedConsumer));
        when(consumerRepository.save(expectedConsumer)).thenReturn(expectedConsumer);

        consumerServiceImp.update(EXISTING_CONSUMER_ID, consumerUpdateRequest);

        verify(consumerRepository, times(1)).findById(expectedConsumer.getId());
        assertThat(consumerUpdateRequest.getFirstName()).isEqualTo(expectedConsumer.getFirstName());
        assertThat(consumerUpdateRequest).isNotNull();
    }


    @Test
    void should_Update_Non_Existing_Consumer() {
        ConsumerUpdateRequest consumerUpdateRequest = new ConsumerUpdateRequest(1, "Sam", "Winchester", "sam@gmail.com");

        when(consumerRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(CustomException.class,
                () -> consumerServiceImp.update(NON_EXISTING_ID, consumerUpdateRequest));

        verify(consumerRepository, never()).save(any(Consumer.class));

    }


    @Test
    void should_Delete_Consumer_ById_When_ExistingId() {

        when(consumerRepository.existsById(EXISTING_CONSUMER_ID)).thenReturn(true);
        willDoNothing().given(consumerRepository).deleteById(EXISTING_CONSUMER_ID);


        consumerServiceImp.deleteById(EXISTING_CONSUMER_ID);

        verify(consumerRepository, times(1)).deleteById(EXISTING_CONSUMER_ID);

    }

    @Test
    void should_Throw_Exception_For_Non_Existing_Consumer_Id_OnDelete() {
        when(consumerRepository.existsById(NON_EXISTING_ID)).thenReturn(false);

        assertThrows(CustomException.class, () -> consumerServiceImp.deleteById(NON_EXISTING_ID));

        verify(consumerRepository, Mockito.never()).delete(expectedConsumer);
    }

}