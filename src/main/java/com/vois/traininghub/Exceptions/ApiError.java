package com.vois.traininghub.Exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ApiError {

    private String message;
    private Integer errorCode;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    private HttpStatus status;
    private Date timestamp;

    public ApiError(String message, Integer errorCode, HttpStatus status, Date timestamp) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = timestamp;
    }

    
    
}
