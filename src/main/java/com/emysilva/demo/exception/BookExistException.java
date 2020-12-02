package com.emysilva.demo.exception;

public class BookExistException extends RuntimeException {
    public BookExistException(String message) {
        super(message);
    }
}
