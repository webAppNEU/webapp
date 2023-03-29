package com.webapp.userwebapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
        //(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class UserWebAppApplication {

    private static final Logger logger =  LoggerFactory.getLogger(UserWebAppApplication.class);
    public static void main(String[] args) {

        logger.info("this is a info message");
        logger.warn("this is a warn message");
        logger.error("this is an error message");

        SpringApplication.run(UserWebAppApplication.class, args);
    }

}
