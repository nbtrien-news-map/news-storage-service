package com.newsmap.exceptions;

public class ResourceNotFoundException
        extends RuntimeException {

    public ResourceNotFoundException() {
        this("api resource not found.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
