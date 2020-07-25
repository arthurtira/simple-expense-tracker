package com.arthurtira.tracker.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomGlobalExceptionHander extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(CustomExpenseErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .timestamp(new Date())
                .message("Invalid request")
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ExpenseNotFoundException.class, UserAlreadyExistsException.class})
    public final ResponseEntity<Object> handleExpenseNotFoundException(ExpenseNotFoundException ex , WebRequest req) {
        return new ResponseEntity<>(CustomExpenseErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .timestamp(new Date())
                .message("Bad request exception")
                .build(), HttpStatus.BAD_REQUEST);
    }

}
