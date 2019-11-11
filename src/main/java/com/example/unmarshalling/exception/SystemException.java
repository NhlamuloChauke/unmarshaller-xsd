package com.example.unmarshalling.exception;

/**
 * Developer: Nathi Khanyile
 * Date: 12/5/17
 */
public class SystemException extends RuntimeException {

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}