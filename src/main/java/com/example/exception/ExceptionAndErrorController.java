package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;

@RestControllerAdvice
public class ExceptionAndErrorController {

    /**
     * If a required parameter is missing, returns response status 400 BAD_REQUEST.
     *
     * @param ex a MissingServletRequestParameterException object
     * @return a String containing the missing parameter name and a message
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        return ex.getParameterName() + " is missing in the query parameters and is required.";
    }

    /**
     * If an Account with the given username already exists, returns response status 409 CONFLICT.
     *
     * @param ex a DuplicateUsernameException object
     * @return the detail message String of this exception
     */
    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateUsername(DuplicateUsernameException ex) {
        return ex.getMessage();
    }

    /**
     * If the username or password associated with an Account does not meet the necessary conditions, returns response
     * status 400 BAD_REQUEST.
     *
     * @param ex an InvalidUsernameOrPasswordException object
     * @return the detail message String of this exception
     */
    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidUsernameOrPassword(InvalidUsernameOrPasswordException ex) {
        return ex.getMessage();
    }

    /**
     * If a referenced resource is not found in the database, returns response status 400 BAD_REQUEST.
     *
     * @param ex a ResourceNotFoundException object
     * @return the detail message String of this exception
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNotFound(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

    /**
     * If there is not an existing Account with the given username and password, returns response status 401
     * UNAUTHORIZED.
     *
     * @param ex an AuthenticationException object
     * @return the detail message String of this exception
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(AuthenticationException ex) {
        return ex.getMessage();
    }

    /**
     * If the messageText associated with a Message does not meet the necessary conditions, returns response status 400
     * BAD_REQUEST.
     *
     * @param ex an InvalidMessageTextException object
     * @return the detail message String of this exception
     */
    @ExceptionHandler(InvalidMessageTextException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidMessageText(InvalidMessageTextException ex) {
        return ex.getMessage();
    }

}
