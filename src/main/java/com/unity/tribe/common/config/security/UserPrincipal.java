package com.unity.tribe.common.config.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.unity.tribe.domain.user.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    private final UserEntity user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getRole() {
        return user.getRole();
    }
}
