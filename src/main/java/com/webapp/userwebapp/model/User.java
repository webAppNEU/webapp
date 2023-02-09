package com.webapp.userwebapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.DataAmount;
import org.hibernate.validator.constraints.Email;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Table(name="users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="userId")
    private Integer userId;
    @Column
    @NotNull
    @Size(min=2, message = "First name should have at least 2 characters")
    private String firstname;
    @Column
    @NotNull
    @Size(min=2, message = "Last name should have at least 2 characters")
    private String lastname;

    @Column
    @NotNull
    //@Size(min=5, message = "Password should have at least 5 characters")
    private String password;

    @Email
    @NotNull
    @Column(name="username")
    private String username;
    @Column
    private LocalDateTime account_created;
    @Column
    private LocalDateTime account_updated;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getAccount_created() {
        return account_created;
    }

    public void setAccount_created(LocalDateTime account_created) {
        this.account_created = account_created;
    }

    public LocalDateTime getAccount_updated() {
        return account_updated;
    }

    public void setAccount_updated(LocalDateTime account_updated) {
        this.account_updated = account_updated;
    }


}
