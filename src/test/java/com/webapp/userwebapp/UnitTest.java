package com.webapp.userwebapp;

import com.timgroup.statsd.StatsDClient;
import com.webapp.userwebapp.controller.UserOps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.mockito.Mockito.*;


public class UnitTest {



    @Test
    void test_healthz_url()
    {
        UserOps userOps = new UserOps();
        userOps.healthz();

        Assertions.assertEquals(HttpStatus.OK,userOps.healthz());

    }
}
