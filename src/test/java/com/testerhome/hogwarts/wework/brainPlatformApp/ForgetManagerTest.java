package com.testerhome.hogwarts.wework.brainPlatformApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        forgetManager.forgetPassword();
    }
}