package com.greenvalley.brainPlatform;

import io.restassured.RestAssured;

public class Wework {

    private static String token;
    private static String BrainPlatformTestUrl = "http://192.168.1.103/brain/rest/user/login";
    private static String BrainPlatformDevUrl = "http://ijixin.com/brain/rest/user/login";
    public static String getWeworkToken(String secret){
        return RestAssured.given().log().all()
                .queryParam("corpid", WeworkConfig.getInstance().corid)
                .queryParam("corpsecret", secret)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all().statusCode(200)
                .extract().path("access_token");
    }

    public static String getBrainPlatformAppToken(){
        String url = setLoginEnv();
        String body = "{\"userName\":\"18616210504\",\"password\":\"suiren123\",\"userType\":1}";
        return RestAssured.given().log().all()
                .body(body)
                .when().post(url)
                .then().log().all().statusCode(200)
                .extract().response().getHeader("Token");
    }

    public static String setLoginEnv() {
        String url = "";
        String currentEnv = WeworkConfig.getInstance().currentEnv;
        if (currentEnv.equals("brainPlatformTest")){
            url = BrainPlatformTestUrl;
            return url;
        }
        if (currentEnv.equals("brainPlatformDev")){
            url = BrainPlatformDevUrl;
            return url;
        }else {
            System.out.println("[Error,未找到匹配的currentEnv]");
        }
        return url;
    }

    public static String getToken(String tokenPattern)  {
        //todo:支持两种类型的token
        if (token == null){
            if (tokenPattern.equals("wechat")){
                token = getWeworkToken(WeworkConfig.getInstance().contactSecret);
                return token;
            }
            if(tokenPattern.equals("brainPlatform")){
                token = getBrainPlatformAppToken();
                return token;
            }else {
                System.out.println("[Error]未找到匹配的tokenPattern");
                return null;
            }
        }
        return token;
    }


}

