package com.emil.projects.usersectorapi.service;

import org.springframework.transaction.annotation.Transactional;
import com.emil.projects.usersectorapi.entity.UserEntity;

public interface ConsentService {

    @Transactional
    void addConsent(UserEntity user, String consentType, String version);

}