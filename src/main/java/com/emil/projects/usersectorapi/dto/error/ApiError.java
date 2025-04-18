package com.emil.projects.usersectorapi.dto.error;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ApiError(
        String code,
        String message,
        Map<String, String> fieldErrors,
        LocalDateTime timestamp,
        String path) {

    public ApiError(String code, String messsage) {
        this(code, messsage, Collections.emptyMap(), LocalDateTime.now(), null);
    }

    public ApiError(String code, String messsage, String path) {
        this(code, messsage, Collections.emptyMap(), LocalDateTime.now(), path);
    }

    public ApiError(String code, String message, Map<String, String> fieldErrors) {
        this(code, message, fieldErrors, LocalDateTime.now(), null);
    }

}
