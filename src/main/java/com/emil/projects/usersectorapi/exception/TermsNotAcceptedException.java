package com.emil.projects.usersectorapi.exception;

import org.springframework.http.HttpStatus;

public class TermsNotAcceptedException extends BaseApiException {

    public TermsNotAcceptedException() {
        super("User must accept the Terms and Conditions",
                ErrorCode.TERMS_NOT_ACCEPTED,
                HttpStatus.BAD_REQUEST);
    }
}