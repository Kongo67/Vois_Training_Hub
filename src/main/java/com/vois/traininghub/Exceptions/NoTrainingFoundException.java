package com.vois.traininghub.Exceptions;

public class NoTrainingFoundException extends RuntimeException {
    
    public NoTrainingFoundException(String message) {
        super(message);
    }

    public NoTrainingFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
