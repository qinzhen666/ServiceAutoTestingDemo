package com.testerhome.hogwarts.wework.devtools;

import com.testerhome.hogwarts.wework.Api;
import com.testerhome.hogwarts.wework.Wework;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

public class App extends Api {
//    @Override
//    public RequestSpecification getDefaultRequestSpecification(String tokenPattern){
//        RequestSpecification requestSpecification = super.getDefaultRequestSpecification(tokenPattern);
//        requestSpecification.queryParam("access_token", Wework.getToken(tokenPattern))
//                .contentType(ContentType.JSON);
//
//        requestSpecification.filter((req,res,ctx)->{
//            //todo:对请求，响应做封装
//            return ctx.next(req,res);
//        });
//        return requestSpecification;
//    }

    public Response listApp(String path, String pattern, HashMap<String,Object> map,String tokenPattern){
        return getResponseFromHar(path,pattern,map,tokenPattern);
    }
}
