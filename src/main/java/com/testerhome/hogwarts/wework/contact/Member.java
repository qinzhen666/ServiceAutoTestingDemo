package com.testerhome.hogwarts.wework.contact;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Member extends Contact{

    public Response create(HashMap<String,Object> map){
        reset();
        String body = template("/data/member.json",map);
        return requestSpecification
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().log().all().extract().response();
    }

    public Response read(String userid){
        reset();
        return requestSpecification
                .param("userid",userid)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/user/get")
                .then().log().all().extract().response();
    }

    public Response update(HashMap<String,Object> map){
        reset();
        String body = template("/data/member.json",map);
        return requestSpecification
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/user/update")
                .then().log().all().extract().response();
    }

    public Response delete (String userid){
        reset();
        return requestSpecification
                .param("userid",userid)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/user/delete")
                .then().log().all().extract().response();
    }

    public Response getUserSimplelist(String department_id,String fetch_child){
        reset();
        return requestSpecification
                .param("department_id",department_id)
                .param("fetch_child",fetch_child)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/user/simplelist")
                .then().log().all().extract().response();
    }

    public Response batchdelete(){

        ArrayList<String> useridlist = getUserSimplelist("2", "1")
                .then().log().all().extract().path("userlist.userid");
        useridlist.remove("QinZhen");
        reset();
        String body = "{" + "useridlist" + ":" + useridlist + "}";
        body = JsonPath.parse(body).jsonString();
        return requestSpecification
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete")
                .then().log().all().extract().response();
    }
}
