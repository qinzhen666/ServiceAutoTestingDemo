package com.greenvalley.brainPlatform.brainPlatformApp;

import com.greenvalley.brainPlatform.Api;
import com.greenvalley.brainPlatform.Wework;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class brainPlatformApp extends Api {
    @Override
    public RequestSpecification getDefaultRequestSpecification(String tokenPattern) {
        RequestSpecification requestSpecification = super.getDefaultRequestSpecification(tokenPattern);
        requestSpecification.header("Token", Wework.getToken(tokenPattern))
                .contentType(ContentType.JSON);

        requestSpecification.filter((req, res, ctx) -> {
            //todo:对请求，响应做封装
            return ctx.next(req, res);
        });
        return requestSpecification;
    }
}
