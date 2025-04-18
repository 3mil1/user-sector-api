package com.emil.projects.usersectorapi.exceptionhandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.emil.projects.usersectorapi.dto.error.ApiError;
import com.emil.projects.usersectorapi.exception.BaseApiException;
import com.emil.projects.usersectorapi.exception.ErrorCode;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private String getRequestPath(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();
            return httpRequest.getRequestURI();
        }
        return null;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        log.warn("Validation error occurred: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            log.debug("Field '{}' validation error: {}", fieldName, errorMessage);
        });

        ApiError apiError = new ApiError(
                ErrorCode.VALIDATION_ERROR.getCode(),
                "Validation failed",
                errors,
                java.time.LocalDateTime.now(),
                getRequestPath(request));

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseApiException.class)
    public ResponseEntity<ApiError> handleBaseApiException(BaseApiException ex, WebRequest request) {
        log.warn("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());

        ApiError apiError = new ApiError(
                ex.getErrorCode().getCode(),
                ex.getMessage(),
                java.util.Collections.emptyMap(),
                java.time.LocalDateTime.now(),
                getRequestPath(request));

        return new ResponseEntity<>(apiError, ex.getHttpStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        log.warn("Entity not found: {}", ex.getMessage());

        ApiError apiError = new ApiError(
                ErrorCode.RESOURCE_NOT_FOUND.getCode(),
                ex.getMessage(),
                java.util.Collections.emptyMap(),
                java.time.LocalDateTime.now(),
                getRequestPath(request));

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        log.warn("Access denied: {}", ex.getMessage());

        ApiError apiError = new ApiError(
                ErrorCode.FORBIDDEN.getCode(),
                "Access denied: " + ex.getMessage(),
                java.util.Collections.emptyMap(),
                java.time.LocalDateTime.now(),
                getRequestPath(request));

        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, WebRequest request) {
        log.error("Unhandled exception occurred", ex);

        ApiError apiError = new ApiError(
                ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                "An unexpected error occurred: " + ex.getMessage(),
                java.util.Collections.emptyMap(),
                java.time.LocalDateTime.now(),
                getRequestPath(request));

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}