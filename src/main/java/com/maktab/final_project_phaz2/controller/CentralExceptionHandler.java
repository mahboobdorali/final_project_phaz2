package com.maktab.final_project_phaz2.controller;


import com.maktab.final_project_phaz2.exception.NoResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CentralExceptionHandler {
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<String> handleException(NoResultException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
