package com.webapp.userwebapp.controller;

import com.webapp.userwebapp.model.User;
import com.webapp.userwebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/")
public class UserOps {

    @Autowired
    private UserRepository userRepository;



    @GetMapping("/healthz")
    public HttpStatus healthz()
    {
        return HttpStatus.OK;
    }
    @PostMapping("/v1/user")
    public HttpStatus createUser(@RequestBody User user)
    {
        //user.setUserId(user.getUserId());
        if(userRepository.existsByUsername(user.getUsername())) {
            return HttpStatus.BAD_REQUEST;
        }
        else
        {
            //Base64.Encoder encoder = Base64.getEncoder();
            User usernew = new User();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            usernew.setFirstname(user.getFirstname());
            usernew.setLastname(user.getLastname());
            String pwd = passwordEncoder.encode(user.getPassword());
            //String password= encoder.encodeToString(user.getPassword().getBytes());

            usernew.setPassword(pwd);
            usernew.setUsername(user.getUsername());
            usernew.setAccount_created(LocalDateTime.now());
            usernew.setAccount_updated(LocalDateTime.now());
            //user.setAccount_created(LocalDateTime.now());
            //user.setAccount_updated(LocalDateTime.now());
            usernew.setRole("User");
            userRepository.save(usernew);
            return HttpStatus.CREATED;
        }

    }

    @GetMapping("/v1/user/{userId}")
    public Object getUserById(@PathVariable int userId)
    {
try {
    Map<String, String> userdetails = new HashMap<>();

    Optional<User> user = userRepository.findById(userId);
    if (user == null) {
        return HttpStatus.FORBIDDEN;
    } else {

        userdetails.put("firstname", user.get().getFirstname());
        userdetails.put("lastname", user.get().getLastname());
        userdetails.put("username", user.get().getUsername());
        userdetails.put("account_created", String.valueOf(user.get().getAccount_created()));
        userdetails.put("account_updated", String.valueOf(user.get().getAccount_updated()));
        return userdetails;
    }
}
catch (NoSuchElementException e)
        {
            return HttpStatus.UNAUTHORIZED;
        }
        //Optional<User> userret = userRepository.findById(userId);

        // return userret;
    }

    @PutMapping("/v1/user/{userId}")
    public HttpStatus updateUser(@PathVariable int userId, @RequestBody User user) {
        try {

            Optional<User> user1 = userRepository.findById(userId);

            if (user == null) {
                return HttpStatus.BAD_REQUEST;
            } else {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String pwd = passwordEncoder.encode(user.getPassword());
                user1.map(userupdate -> {
                    userupdate.setFirstname(user.getFirstname());
                    userupdate.setLastname(user.getLastname());
                    userupdate.setPassword(pwd);
                    userupdate.setAccount_updated(LocalDateTime.now());
                    userRepository.save(userupdate);
                    return null;
                });

                return HttpStatus.NO_CONTENT;

            }
        } catch (Exception e) {
            return HttpStatus.FORBIDDEN;
        }
    }
}
