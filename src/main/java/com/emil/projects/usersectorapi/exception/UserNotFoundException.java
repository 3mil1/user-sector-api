package com.emil.projects.usersectorapi.exception;

import org.springframework.http.HttpStatus;
import java.util.UUID;

public class UserNotFoundException extends BaseApiException {

    public UserNotFoundException(String message) {
        super(message, ErrorCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public UserNotFoundException(UUID userId) {
        super("User with ID " + userId + " not found", ErrorCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}