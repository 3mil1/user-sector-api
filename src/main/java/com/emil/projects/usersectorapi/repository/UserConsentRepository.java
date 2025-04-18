package com.emil.projects.usersectorapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emil.projects.usersectorapi.entity.UserConsentEntity;
import com.emil.projects.usersectorapi.entity.UserEntity;

public interface UserConsentRepository extends JpaRepository<UserConsentEntity, UUID> {

    boolean existsByUserAndConsentTypeAndTermsVersion(UserEntity user,
            String consentType,
            String termsVersion);
}