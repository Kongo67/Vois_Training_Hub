package com.vois.traininghub.Exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class RestEceptionHandler {
    
    @ExceptionHandler(NoTrainingFoundException.class)
    public ResponseEntity<ApiError> NoTrainingFoundException(NoTrainingFoundException ex){
        
        String errorMessage = ex.getMessage();
        if (errorMessage == null || errorMessage.isEmpty()) {
            errorMessage = "No trainings were found with the given parameters.";
        }

        ApiError error = new ApiError(errorMessage, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, new Date());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        ApiError error = new ApiError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, new Date());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
