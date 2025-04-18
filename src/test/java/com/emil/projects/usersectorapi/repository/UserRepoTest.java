package com.emil.projects.usersectorapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.emil.projects.usersectorapi.entity.UserEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:tc:postgresql:14:///test",
    "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver"
})
class UserRepoTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("findById returns the user when it exists")
    void findById_whenUserExists_returnsUser() {
        
        UserEntity user = new UserEntity("Test User");
        UserEntity savedUser = userRepository.save(user);

        Optional<UserEntity> result = userRepository.findById(savedUser.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test User");
    }

    @Test
    @DisplayName("findById returns empty when user doesn't exist")
    void findById_whenUserDoesNotExist_returnsEmpty() {

        UUID nonExistentId = UUID.randomUUID();

        Optional<UserEntity> result = userRepository.findById(nonExistentId);

        assertThat(result).isEmpty();
    }
}