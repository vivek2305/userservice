package com.harikart.userservice.security.model;

import com.harikart.userservice.model.Role;
import com.harikart.userservice.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(Optional<User> user){
        this.user=user.orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<CustomGrantedAuthority> customGrantedAuthorities = new ArrayList<>();
        for(Role role: user.getRoles()){
            customGrantedAuthorities.add(new CustomGrantedAuthority(role));
        }
        return customGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getHashedPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
