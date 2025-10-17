package com.tramed.backend.presentation.webapi.security.user;

import com.tramed.backend.core.base.model.common.UserId;
import com.tramed.backend.core.base.model.user.User;
import com.tramed.backend.core.base.model.user.UserRole;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record ApplicationUserDetails(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.role().name()));
    }

    @Override
    public String getPassword() {
        return user.passwordHash();
    }

    @Override
    public String getUsername() {
        return user.username();
    }

    @Override
    public boolean isEnabled() {
        return user.active();
    }

    public UserId getUserId() {
        return user.userId();
    }

    public String getFullName() {
        return user.fullName();
    }

    public UserRole getRole() {
        return user.role();
    }
}
