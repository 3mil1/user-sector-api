package com.emil.projects.usersectorapi.service;

import java.util.List;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;

import com.emil.projects.usersectorapi.dto.SectorDto;
import com.emil.projects.usersectorapi.entity.SectorEntity;

public interface SectorService {

    @Transactional(readOnly = true)
    List<SectorDto> getAllSectors();

    @Transactional(readOnly = true)
    Set<SectorEntity> findSectorsByIds(List<Long> sectorIds);

}