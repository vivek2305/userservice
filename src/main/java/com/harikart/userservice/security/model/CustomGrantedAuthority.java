package com.harikart.userservice.security.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.harikart.userservice.model.Role;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {


    private String authority;


    public CustomGrantedAuthority(Role role) {
        this.authority=role.getName();
    }

    public CustomGrantedAuthority() {
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
