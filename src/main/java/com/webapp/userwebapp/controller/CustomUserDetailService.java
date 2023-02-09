package com.webapp.userwebapp.controller;

import com.webapp.userwebapp.model.User;
import com.webapp.userwebapp.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



        User user = userRepository.findByUsername(username);
    if(user==null)
    {
        throw new UsernameNotFoundException("User Not Found");
    }
        return new CustomUserDetails(user) {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }
        };

    }


}
