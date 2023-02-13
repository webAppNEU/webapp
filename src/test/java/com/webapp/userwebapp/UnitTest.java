package com.webapp.userwebapp;

import com.webapp.userwebapp.controller.UserOps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class UnitTest {

    @Test

    void test_healthz_url()
    {
        UserOps userOps = new UserOps();
        userOps.healthz();

        Assertions.assertEquals(HttpStatus.FORBIDDEN,userOps.healthz());

    }
}
