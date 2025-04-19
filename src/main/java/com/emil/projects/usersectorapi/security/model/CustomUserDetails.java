package com.emil.projects.usersectorapi.security.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

import static com.emil.projects.usersectorapi.constant.ApiConstants.PASSWORD_PLACEHOLDER;

@Getter
@ToString(callSuper = true)
public class CustomUserDetails extends User {

    private final UUID userId;
    private final String displayName;

    public CustomUserDetails(UUID userId, String displayName, Collection<? extends GrantedAuthority> authorities) {
        super(userId.toString(), PASSWORD_PLACEHOLDER, authorities);
        this.userId = userId;
        this.displayName = displayName;
    }
}
