package com.example.animal_search_engine.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(CustomMessage.class)
    public ResponseEntity<String> handleCustomException(CustomMessage customMessage) {
        return ResponseEntity.status(customMessage.getStatus()).body(
                "Поризошла ошибка: " + customMessage.getMessage() + "\n Статус ошибки: " + customMessage.getStatus()
        );
    }

/*
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e
    ) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(

                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }
*/


}
