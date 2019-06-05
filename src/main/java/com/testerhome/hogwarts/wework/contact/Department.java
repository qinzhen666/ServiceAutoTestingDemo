package com.testerhome.hogwarts.wework.contact;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.testerhome.hogwarts.wework.Wework;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class Department extends Contact{

    /**
     * 列出部门
     * @param id
     * @return
     */
    public Response list(String id){
        reset();
        return requestSpecification
                .param("id",id)
        .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
        .then().log().all().statusCode(200).extract().response();
    }

    /**
     * 创建部门
     * @param name
     * @param parentid
     * @return
     */
    public Response create(String name,String parentid){
        reset();
        String body = JsonPath.parse(this.getClass()
                .getResourceAsStream("/data/create.json"))
                .set("$.name",name)
                .set("parentid",parentid).jsonString();
        return requestSpecification
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all().statusCode(200).extract().response();
    }

    public Response create(HashMap<String,Object> map){
        reset();
        DocumentContext documentContext = JsonPath.parse(this.getClass()
                .getResourceAsStream("/data/create.json"));
        map.entrySet().forEach(entry->{
            documentContext.set(entry.getKey(),entry.getValue());
        });
        return requestSpecification
                .body(documentContext.jsonString())
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all().statusCode(200).extract().response();
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    public Response delete(String id){

        reset();
        return requestSpecification
                .param("id",id)
              .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
              .then().log().all().statusCode(200).extract().response();
    }

    public Response deleteAll(){
        reset();
        List<Integer> idlist = list("").then().log().all().extract().path("department.id");
        System.out.println(idlist);
        for (Integer id: idlist) {
            delete(id.toString());
        }
        return null;
    }

    /**
     * 更新部门
     * @param id
     * @param name
     * @return
     */
    public Response update(String id,String name){
        reset();
        String body = JsonPath.parse(this.getClass()
                .getResourceAsStream("/data/update.json"))
                .set("name",name)
                .set("id",id).jsonString();

        return requestSpecification
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/update")
                .then().log().all().statusCode(200).extract().response();
    }
}
