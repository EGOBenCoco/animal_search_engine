package com.example.animal_search_engine.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class SuccessMessage {

    private int status;
    private String message;
    private LocalDateTime datetime;
}
