package com.example.animal_search_engine.exception;

import java.util.List;


public record ValidationErrorResponse(List<Violation> violations) { }

