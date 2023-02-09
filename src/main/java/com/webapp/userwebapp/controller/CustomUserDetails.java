package com.webapp.userwebapp.controller;

import com.webapp.userwebapp.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.Collections;


public class CustomUserDetails implements UserDetails {




   private User user1;
    private Integer id;


    public CustomUserDetails(User user) {
        this.user1 = user;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user1.getUsername()== null)
        {
            return null;
        }

        else
        {
            return Collections.singleton(new SimpleGrantedAuthority(user1.getUsername()));
        }

    }

    @Override
    public String getPassword() {
       return user1.getPassword();

    }

    @Override
    public String getUsername() {
        return user1.getUsername();
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
