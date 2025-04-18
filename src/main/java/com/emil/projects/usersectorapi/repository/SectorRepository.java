package com.emil.projects.usersectorapi.repository;

import com.emil.projects.usersectorapi.entity.SectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<SectorEntity, Long> {

    @Query("SELECT DISTINCT s FROM SectorEntity s LEFT JOIN FETCH s.children")
    List<SectorEntity> findAllWithChildrenFetched();

    @Query("SELECT DISTINCT s FROM SectorEntity s LEFT JOIN FETCH s.children WHERE s.parent IS NULL")
    List<SectorEntity> findRootsWithChildrenFetched();

    @Query("SELECT s FROM SectorEntity s LEFT JOIN FETCH s.children WHERE s.id = :id")
    Optional<SectorEntity> findByIdWithChildrenFetched(@Param("id") Long id);

}