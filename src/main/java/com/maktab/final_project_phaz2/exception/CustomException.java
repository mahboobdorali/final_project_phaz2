package com.maktab.final_project_phaz2.exception;

import org.springframework.http.HttpStatus;

public record CustomException(HttpStatus httpStatus,String message) {
}
