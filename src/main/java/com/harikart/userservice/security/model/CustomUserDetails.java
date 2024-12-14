package com.harikart.userservice.security.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.harikart.userservice.model.Role;
import com.harikart.userservice.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonDeserialize
public class CustomUserDetails implements UserDetails {

    List<CustomGrantedAuthority> authorities;
    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


    public CustomUserDetails(User user){
        this.accountNonExpired=true;
        this.accountNonLocked=true;
        this.credentialsNonExpired=true;
        this.enabled=true;
        this.password=user.getHashedPassword();
        this.username=user.getEmail();
        this.authorities = new ArrayList<>();

        List<CustomGrantedAuthority> customGrantedAuthorities = new ArrayList<>();
        for(Role role: user.getRoles()){
            customGrantedAuthorities.add(new CustomGrantedAuthority(role));
        }
        this.authorities = customGrantedAuthorities;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    public CustomUserDetails() {
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
