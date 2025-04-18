package com.emil.projects.usersectorapi.service;

import com.emil.projects.usersectorapi.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    @Transactional
    UUID saveUser(UserDto userDto);

    @Transactional(readOnly = true)
    Optional<UserDto> findById(UUID id);

    @Transactional
    UserDto updateUser(UUID userId, UserDto userDto);

}