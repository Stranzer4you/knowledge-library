package com.knowledge.library.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        log.warn("{} {} | status=400 | message={}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        return new ErrorResponse(LocalDateTime.now(), ex.getClass().getSimpleName(), ex.getMessage(), null, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(s -> s.getField() + " : " + s.getDefaultMessage()).toList();
        log.warn("{} {} | status=400 | validationErrors={}", request.getMethod(), request.getRequestURI(), errors);
        return new ErrorResponse(LocalDateTime.now(), ex.getClass().getSimpleName(), "Validation failed", errors, request.getRequestURI());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.warn("{} {} | status=404 | message={}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        return new ErrorResponse(LocalDateTime.now(), ex.getClass().getSimpleName(), ex.getMessage(), null, request.getRequestURI());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(ConflictException ex, HttpServletRequest request) {
        log.warn("{} {} | status=409 | message={}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        return new ErrorResponse(LocalDateTime.now(), ex.getClass().getSimpleName(), ex.getMessage(), null, request.getRequestURI());
    }
}
