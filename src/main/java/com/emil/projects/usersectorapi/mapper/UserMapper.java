package com.emil.projects.usersectorapi.mapper;

import com.emil.projects.usersectorapi.constant.ApiConstants;
import com.emil.projects.usersectorapi.dto.UserDto;
import com.emil.projects.usersectorapi.entity.SectorEntity;
import com.emil.projects.usersectorapi.entity.UserConsentEntity;
import com.emil.projects.usersectorapi.entity.UserEntity;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "sectors", target = "sectors", qualifiedByName = "sectorsToIds"),
            @Mapping(source = "consents", target = "termsAccepted", qualifiedByName = "consentsToTermsAccepted")
    })
    UserDto toDto(UserEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "consents", ignore = true),
            @Mapping(target = "sectors", ignore = true)
    })
    UserEntity toEntity(UserDto dto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "consents", ignore = true),
            @Mapping(target = "sectors", ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserDto dto, @MappingTarget UserEntity entity);

    @Named("sectorsToIds")
    default List<Long> sectorsToIds(Set<SectorEntity> sectors) {
        if (sectors == null) {
            return Collections.emptyList();
        }
        return sectors.stream()
                .map(SectorEntity::getId)
                .collect(Collectors.toList());
    }

    @Named("consentsToTermsAccepted")
    default Boolean consentsToTermsAccepted(Set<UserConsentEntity> consents) {
        if (consents == null || consents.isEmpty()) {
            return false;
        }
        return consents.stream()
                .anyMatch(consent -> ApiConstants.TERMS_CONSENT_TYPE.equals(consent.getConsentType()));
    }
}