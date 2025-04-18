package com.emil.projects.usersectorapi.exception;

public enum ErrorCode {

    VALIDATION_ERROR("VALIDATION_ERROR"),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND"),
    UNAUTHORIZED("UNAUTHORIZED"),
    FORBIDDEN("FORBIDDEN"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),

    USER_NOT_FOUND("USER_NOT_FOUND"),
    SECTOR_NOT_FOUND("SECTOR_NOT_FOUND"),
    TERMS_NOT_ACCEPTED("TERMS_NOT_ACCEPTED"),
    DUPLICATE_USER("DUPLICATE_USER");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
