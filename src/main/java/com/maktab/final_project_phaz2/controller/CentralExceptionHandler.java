package com.maktab.final_project_phaz2.controller;


import com.maktab.final_project_phaz2.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CentralExceptionHandler {
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<?> handleException(NoResultException ex) {
        CustomException customException = new CustomException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<?> handleException(DuplicateEntryException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(InputInvalidException.class)
    public ResponseEntity<?> handleException(InputInvalidException in) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, in.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(RequestIsNotValidException.class)
    public ResponseEntity<?> handleException(RequestIsNotValidException re) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, re.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(SourceUsageRestrictionsException.class)
    public ResponseEntity<?> handleException(SourceUsageRestrictionsException sr) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, sr.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }
}
