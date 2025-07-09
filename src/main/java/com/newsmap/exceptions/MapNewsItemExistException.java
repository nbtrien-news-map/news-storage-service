package com.newsmap.exceptions;

public class MapNewsItemExistException extends RuntimeException {
    public MapNewsItemExistException() {
        this("Map News Item does not exist.");
    }

    public MapNewsItemExistException(String message) {
        super(message);
    }
}
