package com.webapp.userwebapp.repository;

import com.webapp.userwebapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


@Repository
    public interface UserRepository extends JpaRepository<User, Integer>
    {

    boolean existsByUsername(String username);
    //@Query("SELECT User .username from User where username =?1")
    //User findByUsername(String username);
    public User findByUsername(String username);

    public Integer getByUserId (Integer userId);


    }
