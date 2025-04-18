package com.emil.projects.usersectorapi.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID id,

        @NotBlank(message = "Name is required") @Size(max = 100, message = "Name must be at most 100 characters") String name,

        @NotEmpty(message = "At least one sector must be selected") List<Long> sectors,

        @NotNull(message = "Confirmation of agreement to terms is required.") @AssertTrue(message = "You must agree to the Terms and Conditions to proceed.") Boolean termsAccepted) {

    public UserDto {
        if (sectors == null) {
            sectors = new ArrayList<>();
        }
    }

    public UserDto() {
        this(null, null, new ArrayList<>(), null);
    }

    public UserDto(
            String name,
            List<Long> sectors,
            Boolean termsAccepted) {
        this(null, name, sectors, termsAccepted);
    }
}