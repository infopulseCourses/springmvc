package com.courses.mvc.exception;

/**
 * @author Stepan
 */
public class UserServiceException extends RuntimeException {

    public UserServiceException() {
    }

    public UserServiceException(String message) {
        super(message);
    }
}
