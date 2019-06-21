package com.greenvalley.brainPlatform.brainPlatformApp;

import io.restassured.response.Response;

import java.util.HashMap;

public class ForgetManager extends brainPlatformApp {

    String tokenPattern = "brainPlatform";
// "password": "suirendanao@",
//    "tel": "17988888888",
//    "verifyCode": 0
    public Response forgetPassword(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("_file", "/data/brainPlatformApp/forgetPassword.json");
        return getResponseFromSwagger("/api/brainPlatformApp/SwaggerDemo.yaml", "/data/brainPlatformApp/AppSwagger.json",map,tokenPattern);
    }
}
