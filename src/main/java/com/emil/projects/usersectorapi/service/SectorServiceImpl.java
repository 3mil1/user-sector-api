package com.emil.projects.usersectorapi.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emil.projects.usersectorapi.dto.SectorDto;
import com.emil.projects.usersectorapi.entity.SectorEntity;
import com.emil.projects.usersectorapi.exception.SectorNotFoundException;
import com.emil.projects.usersectorapi.repository.SectorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorsRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable("sectors")
    public List<SectorDto> getAllSectors() {
        log.debug("Fetching all root sectors with their children");
        List<SectorEntity> rootSectors = sectorsRepository.findRootsWithChildrenFetched();

        return rootSectors.stream()
                .map(this::mapEntityToDtoWithChildren)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<SectorEntity> findSectorsByIds(List<Long> sectorIds) {
        if (sectorIds == null || sectorIds.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Long> uniqueIds = new HashSet<>(sectorIds);
        log.debug("Finding sectors by IDs: {}", uniqueIds);

        Set<SectorEntity> foundSectors = new HashSet<>(sectorsRepository.findAllById(uniqueIds));

        if (foundSectors.size() != uniqueIds.size()) {
            List<Long> missingIds = uniqueIds.stream()
                    .filter(id -> foundSectors.stream().noneMatch(s -> s.getId().equals(id)))
                    .collect(Collectors.toList());
            log.warn("Some sector IDs were not found: {}", missingIds);
            throw new SectorNotFoundException(
                    "One or more sectors not found for provided IDs during mapping. Missing IDs: " + missingIds);
        }
        return foundSectors;
    }

    private SectorDto mapEntityToDtoWithChildren(SectorEntity entity) {
        if (entity == null) {
            return null;
        }

        SectorDto dto = new SectorDto(entity.getId(), entity.getName());

        if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
            List<SectorDto> childrenDtos = entity.getChildren().stream()
                    .map(this::mapEntityToDtoWithChildren)
                    .collect(Collectors.toList());

            dto = dto.withAddedChildren(childrenDtos);
        }

        return dto;
    }
}