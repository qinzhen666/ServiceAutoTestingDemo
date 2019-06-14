package com.testerhome.hogwarts.wework.brainPlatformApp;

import com.testerhome.hogwarts.wework.Api;
import com.testerhome.hogwarts.wework.Wework;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class brainPlatformApp extends Api {
    @Override
    public RequestSpecification getDefaultRequestSpecification(String tokenPattern) {
        RequestSpecification requestSpecification = super.getDefaultRequestSpecification(tokenPattern);
        requestSpecification.queryParam("Token", Wework.getToken(tokenPattern))
                .contentType(ContentType.JSON);

        requestSpecification.filter((req, res, ctx) -> {
            //todo:对请求，响应做封装
            return ctx.next(req, res);
        });
        return requestSpecification;
    }
}
