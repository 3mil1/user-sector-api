package com.emil.projects.usersectorapi.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import com.emil.projects.usersectorapi.dto.UserDto;
import com.emil.projects.usersectorapi.entity.SectorEntity;
import com.emil.projects.usersectorapi.entity.UserEntity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    @DisplayName("toDto() converts entity to DTO without leaking internal state")
    void toDto() {

        UserEntity entity = new UserEntity("Bob");
        entity.setId(UUID.randomUUID());
        entity.addSector(new SectorEntity("Wood"));

        UserDto dto = mapper.toDto(entity);

        assertThat(dto.name()).isEqualTo("Bob");
        assertThat(dto.sectors()).containsExactly(entity.getSectors().iterator().next().getId());
        assertThat(dto.termsAccepted()).isFalse();
    }

    @Test
    @DisplayName("toEntity() ignores ID and collections as specified by mapping rules")
    void toEntity() {

        UserDto dto = new UserDto("Carol", List.of(3L, 4L), true);

        UserEntity entity = mapper.toEntity(dto);

        assertThat(entity.getId()).isNull();
        assertThat(entity.getName()).isEqualTo("Carol");
        assertThat(entity.getSectors()).isEmpty();
    }
}