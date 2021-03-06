package com.greenvalley.brainPlatform.contact;

import com.greenvalley.brainPlatform.Api;
import com.greenvalley.brainPlatform.Wework;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public class Contact extends Api {

    String random = String.valueOf(System.currentTimeMillis());
    @Override
    public RequestSpecification getDefaultRequestSpecification(String tokenPattern){
        RequestSpecification requestSpecification = super.getDefaultRequestSpecification(tokenPattern);
        requestSpecification.queryParam("access_token", Wework.getToken(tokenPattern))
                            .contentType(ContentType.JSON);

        requestSpecification.filter((req,res,ctx)->{
           return ctx.next(req,res);
        });
        return requestSpecification;
    }


}
