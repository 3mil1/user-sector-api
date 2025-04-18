package com.emil.projects.usersectorapi.security.service;

import java.util.Collections;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emil.projects.usersectorapi.entity.UserEntity;
import com.emil.projects.usersectorapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpSessionSecurityContextRepository securityContextRepository;
    private final SessionAuthenticationStrategy sessionStrategy;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "userDetails", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UUID userId;
        try {
            userId = UUID.fromString(username);
        } catch (IllegalArgumentException ex) {
            log.debug("Supplied identifier is not a valid UUID: {}", username);
            throw new UsernameNotFoundException("User not found");
        }

        UserEntity user = userRepository.findByIdWithAll(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getId().toString())
                .password("{noop}")
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public void authenticateUser(UUID userId,
            HttpServletRequest request,
            HttpServletResponse response) {

        UserDetails userDetails = loadUserByUsername(userId.toString());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(context, request, response);

        sessionStrategy.onAuthentication(authentication, request, response);

        log.debug("Programmatic authentication completed for user {}", userId);
    }
}