package com.student.management.studentdemo.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Mono<String> handleRuntimeException(RuntimeException ex) {
        return Mono.just("Error: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Mono<String> handleGenericException(Exception ex) {
        return Mono.just("Internal server error");
    }
}
