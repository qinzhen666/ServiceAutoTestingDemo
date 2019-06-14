package com.testerhome.hogwarts.wework.devtools;

import com.testerhome.hogwarts.wework.Api;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    static String tokenPattern = "wechat";
    @Test
    void listApp() {
        App app = new App();
        app.listApp("/api/app.har.json",".*tid=67.*",null,tokenPattern).then().statusCode(200);
    }
}