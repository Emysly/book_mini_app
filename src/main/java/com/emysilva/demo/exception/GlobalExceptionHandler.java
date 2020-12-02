package com.emysilva.demo.exception;

import com.emysilva.demo.config.error.ApiErrors;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrors apiErrors = new ApiErrors(ex.getMessage(), "Method not supported", status, LocalDateTime.now() );
        return ResponseEntity.status(status).headers(headers).body(apiErrors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrors apiErrors = new ApiErrors(ex.getMessage(), "Media type not supported", status, LocalDateTime.now() );
        return ResponseEntity.status(status).headers(headers).body(apiErrors);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrors apiErrors = new ApiErrors(ex.getMessage(), "Path Variable is missing", status, LocalDateTime.now() );
        return ResponseEntity.status(status).headers(headers).body(apiErrors);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrors apiErrors = new ApiErrors(ex.getMessage(), "Mismatch of type", status, LocalDateTime.now() );
        return ResponseEntity.status(status).headers(headers).body(apiErrors);
    }

    @ExceptionHandler(BookNotFoundException.class)
    protected ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex) {
        ApiErrors apiErrors = new ApiErrors(ex.getMessage(), "Book not found", HttpStatus.BAD_REQUEST, LocalDateTime.now() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrors);
    }

    @ExceptionHandler(IdNotFoundException.class)
    protected ResponseEntity<Object> handleIdNotFoundException(IdNotFoundException ex) {
        ApiErrors apiErrors = new ApiErrors(ex.getMessage(), "Invalid Id", HttpStatus.NOT_FOUND, LocalDateTime.now() );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        ApiErrors apiErrors = new ApiErrors(ex.getMessage(), "User not found", HttpStatus.BAD_REQUEST, LocalDateTime.now() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrors);
    }

    @ExceptionHandler(UserExistException.class)
    protected ResponseEntity<Object> handleUserExistException(UserExistException ex) {
        ApiErrors apiErrors = new ApiErrors(ex.getMessage(), "User exist", HttpStatus.BAD_REQUEST, LocalDateTime.now() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrors);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleOtherException(Exception ex) {
        ApiErrors apiErrors = new ApiErrors(ex.getMessage(), "Other exception", HttpStatus.NOT_FOUND, LocalDateTime.now() );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrors);
    }
}
