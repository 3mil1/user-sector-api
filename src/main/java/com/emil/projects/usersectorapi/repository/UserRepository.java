package com.emil.projects.usersectorapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emil.projects.usersectorapi.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.sectors WHERE u.id = :id")
    Optional<UserEntity> findByIdWithSectors(@Param("id") UUID id);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.sectors LEFT JOIN FETCH u.consents WHERE u.id = :id")
    Optional<UserEntity> findByIdWithAll(@Param("id") UUID id);
}