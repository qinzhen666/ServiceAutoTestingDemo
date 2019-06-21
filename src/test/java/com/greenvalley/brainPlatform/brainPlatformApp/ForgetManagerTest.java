package com.greenvalley.brainPlatform.brainPlatformApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

class ForgetManagerTest {

    ForgetManager forgetManager;
    @BeforeEach
    void setUp(){
        if (forgetManager == null){
            forgetManager = new ForgetManager();
        }
    }

    @Test
    void forgetPassword() {
        forgetManager.forgetPassword().then().statusCode(200).body("status",equalTo("0"));
    }
}