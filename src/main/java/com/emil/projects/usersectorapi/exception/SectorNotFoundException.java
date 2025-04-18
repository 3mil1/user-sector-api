package com.emil.projects.usersectorapi.exception;

import org.springframework.http.HttpStatus;

public class SectorNotFoundException extends BaseApiException {

    public SectorNotFoundException(String message) {
        super(message, ErrorCode.SECTOR_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public SectorNotFoundException(Long sectorId) {
        super("Sector with ID " + sectorId + " not found", ErrorCode.SECTOR_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}