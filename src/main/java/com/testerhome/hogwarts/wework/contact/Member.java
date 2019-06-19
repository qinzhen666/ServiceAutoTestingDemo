package com.testerhome.hogwarts.wework.contact;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;

public class Member extends Contact{

    public Response create(HashMap<String,Object> map,String tokenPattern){
        String body = template("/data/weChat/member.json",map);
        return getDefaultRequestSpecification(tokenPattern)
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().log().all().extract().response();
    }

    public Response read(String userid,String tokenPattern){
        return getDefaultRequestSpecification(tokenPattern)
                .param("userid",userid)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/user/get")
                .then().log().all().extract().response();
    }

    public Response update(HashMap<String,Object> map,String tokenPattern){
        String body = template("/data/weChat/member.json",map);
        return getDefaultRequestSpecification(tokenPattern)
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/user/update")
                .then().log().all().extract().response();
    }

    public Response delete (String userid,String tokenPattern){
        return getDefaultRequestSpecification(tokenPattern)
                .param("userid",userid)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/user/delete")
                .then().log().all().extract().response();
    }

    public Response getUserSimplelist(String department_id,String fetch_child,String tokenPattern){
        return getDefaultRequestSpecification(tokenPattern)
                .param("department_id",department_id)
                .param("fetch_child",fetch_child)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/user/simplelist")
                .then().log().all().extract().response();
    }

    public Response batchdelete(String tokenPattern){

        ArrayList<String> useridlist = getUserSimplelist("2", "1",tokenPattern)
                .then().log().all().extract().path("userlist.userid");
        useridlist.remove("QinZhen");
        String body = "{" + "useridlist" + ":" + useridlist + "}";
        body = JsonPath.parse(body).jsonString();
        return getDefaultRequestSpecification(tokenPattern)
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete")
                .then().log().all().extract().response();
    }
}
