package com.emil.projects.usersectorapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.emil.projects.usersectorapi.config.SecurityConfig;
import com.emil.projects.usersectorapi.dto.UserDto;
import com.emil.projects.usersectorapi.exceptionhandler.GlobalExceptionHandler;
import com.emil.projects.usersectorapi.security.service.AuthenticationService;
import com.emil.projects.usersectorapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserController.class)
@Import({ SecurityConfig.class, GlobalExceptionHandler.class })
@ActiveProfiles("test")
class UserControllerValidationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        doNothing().when(authenticationService).authenticateUser(any(), any(), any());
    }

    @Nested
    @DisplayName("POST /api/users")
    class CreateUser {

        @Test
        @DisplayName("should return 400 BAD_REQUEST when 'name' is blank")
        void whenNameMissing_then400() throws Exception {

            UserDto invalid = new UserDto(null, List.of(1L), true);

            ResultActions result = mvc.perform(
                    post("/api/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(invalid)))
                    .andDo(MockMvcResultHandlers.print());

            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                    .andExpect(jsonPath("$.fieldErrors.name").exists());

            Mockito.verifyNoInteractions(userService);
        }

        @Test
        @DisplayName("should return 201 CREATED when payload is valid")
        void whenValidPayload_then201() throws Exception {

            UUID newId = UUID.randomUUID();
            when(userService.saveUser(any())).thenReturn(newId);

            UserDto valid = new UserDto("Alice", List.of(1L), true);

            mvc.perform(post("/api/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(valid)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location"));
        }
    }
}