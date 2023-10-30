package com.example.animal_search_engine.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomMessage extends RuntimeException {
    HttpStatus status;

    public CustomMessage(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
