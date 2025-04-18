package com.emil.projects.usersectorapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emil.projects.usersectorapi.entity.UserConsentEntity;
import com.emil.projects.usersectorapi.entity.UserEntity;
import com.emil.projects.usersectorapi.repository.UserConsentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsentServiceImpl implements ConsentService {

    private final UserConsentRepository consentRepository;

    @Override
    @Transactional
    public void addConsent(UserEntity user, String consentType, String version) {

        if (consentRepository.existsByUserAndConsentTypeAndTermsVersion(user, consentType, version)) {
            log.debug("Consent '{}' version '{}' already exists for user with id '{}'",
                    consentType, version, user.getId());
            return;
        }

        UserConsentEntity consent = new UserConsentEntity(user, consentType, version);
        consentRepository.save(consent);
        user.addConsent(consent);

        log.info("Recorded consent '{}' version '{}' for user with id '{}'",
                consentType, version, user.getId());
    }
}