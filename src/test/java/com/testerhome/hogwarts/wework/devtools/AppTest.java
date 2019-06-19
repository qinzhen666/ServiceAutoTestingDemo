package com.testerhome.hogwarts.wework.devtools;

import org.junit.jupiter.api.Test;

class AppTest {

    static String tokenPattern = "wechat";
    @Test
    void listApp() {
        App app = new App();
        app.listApp("/api/weChat/app.har.json",".*tid=67.*",null,tokenPattern).then().statusCode(200);
    }
}