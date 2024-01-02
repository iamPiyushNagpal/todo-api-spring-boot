package com.practice.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandling(Exception exception, WebRequest webRequest) {
        return new ResponseEntity<>(new ErrorDetails(HttpStatus.NOT_FOUND.value(), new Date(), exception.getMessage(), webRequest.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedExceptionHandling(Exception exception, WebRequest webRequest) {
        return new ResponseEntity<>(new ErrorDetails(HttpStatus.FORBIDDEN.value(), new Date(), exception.getMessage(), webRequest.getDescription(false)), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<?> duplicateUserExceptionHandling(Exception exception, WebRequest webRequest) {
        return new ResponseEntity<>(new ErrorDetails(HttpStatus.CONFLICT.value(), new Date(), exception.getMessage(), webRequest.getDescription(false)), HttpStatus.CONFLICT);
    }

}
