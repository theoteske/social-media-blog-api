package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;

@RestControllerAdvice
public class ExceptionAndErrorController {

    // if a required parameter is missing, status 400
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        return ex.getParameterName() + " is missing in the query parameters and is required.";
    }

    // DuplicateUsernameException, status 409
    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateUsername(DuplicateUsernameException ex) {
        return ex.getMessage();
    }

    // InvalidUsernameOrPasswordException, status 400
    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidUsernameOrPassword(InvalidUsernameOrPasswordException ex) {
        return ex.getMessage();
    }

    // ResourceNotFoundException, NOT_FOUND (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

    // AuthenticationException, UNAUTHORIZED (401)
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(AuthenticationException ex) {
        return ex.getMessage();
    }
}
