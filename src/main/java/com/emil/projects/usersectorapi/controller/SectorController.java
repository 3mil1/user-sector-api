package com.emil.projects.usersectorapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emil.projects.usersectorapi.dto.SectorDto;
import com.emil.projects.usersectorapi.service.SectorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;

    @GetMapping
    public ResponseEntity<List<SectorDto>> getAllSectors() {
        return ResponseEntity.ok(sectorService.getAllSectors());
    }
}