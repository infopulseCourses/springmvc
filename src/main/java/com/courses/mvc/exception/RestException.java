package com.courses.mvc.exception;

/**
 * @author Stepan
 */
public class RestException extends RuntimeException {

    public RestException(String message) {
        super(message);
    }
}
