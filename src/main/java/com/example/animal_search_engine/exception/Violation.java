package com.example.animal_search_engine.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


public record Violation(String fieldName, String message) {

}