package com.emil.projects.usersectorapi.service;

import java.util.Optional;
import java.util.UUID;

import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.emil.projects.usersectorapi.constant.ApiConstants;
import com.emil.projects.usersectorapi.dto.UserDto;
import com.emil.projects.usersectorapi.entity.SectorEntity;
import com.emil.projects.usersectorapi.entity.UserEntity;
import com.emil.projects.usersectorapi.exception.TermsNotAcceptedException;
import com.emil.projects.usersectorapi.exception.UserNotFoundException;
import com.emil.projects.usersectorapi.mapper.UserMapper;
import com.emil.projects.usersectorapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SectorService sectorService;
    private final ConsentService consentService;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public UUID saveUser(UserDto dto) {
        log.info("Attempting to save new user");

        if (Boolean.FALSE.equals(dto.termsAccepted())) {
            throw new TermsNotAcceptedException();
        }

        UserEntity userEntity = userMapper.toEntity(dto);

        Set<SectorEntity> sectors = sectorService.findSectorsByIds(dto.sectors());
        userEntity.setSectors(sectors);

        UserEntity savedEntity = userRepository.save(userEntity);

        consentService.addConsent(savedEntity,
                ApiConstants.TERMS_CONSENT_TYPE,
                ApiConstants.CURRENT_TERMS_VERSION);

        return savedEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findById(UUID id) {
        log.debug("Finding user by ID: {}", id);
        return userRepository.findByIdWithAll(id)
                .map(userMapper::toDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public UserDto updateUser(UUID userId, UserDto userDto) {
        UserEntity existingUser = userRepository.findByIdWithAll(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        userMapper.updateUserFromDto(userDto, existingUser);

        if (userDto.sectors() != null && !userDto.sectors().isEmpty()) {
            Set<SectorEntity> sectors = sectorService.findSectorsByIds(userDto.sectors());
            existingUser.setSectors(sectors);
        }

        UserEntity updatedEntity = userRepository.save(existingUser);
        return userMapper.toDto(updatedEntity);
    }
}