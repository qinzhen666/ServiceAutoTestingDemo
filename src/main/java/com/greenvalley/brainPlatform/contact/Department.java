package com.greenvalley.brainPlatform.contact;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;


public class Department extends Contact {
    static String tokenPattern = "wechat";

    /**
     * 列出部门
     * @param id
     * @return
     */
    public Response list(String id){
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",id);
        return getResponseFromYaml("/api/weChat/list.yaml",map,tokenPattern);
    }

    /**
     * 创建部门
     * @param name
     * @param parentid
     * @return
     */
    public Response create(String name,String parentid){
        //让用例更清晰
        HashMap<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("parentid",parentid);
        map.put("_file", "/data/weChat/create.json");
        return getResponseFromYaml("/api/weChat/create.yaml",map,tokenPattern);
    }

    public Response create(HashMap<String,Object> map){
        map.put("_file", "/data/weChat/create.json");
        return getResponseFromYaml("/api/weChat/create.yaml",map,tokenPattern);
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    public Response delete(String id){
    HashMap<String,Object> map = new HashMap<>();
    map.put("id",id);
    return getResponseFromYaml("/api/weChat/delete.yaml",map,tokenPattern);
    }

    public Response deleteAll(){
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
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("_file", "/data/weChat/update.json");
        return getResponseFromYaml("/api/weChat/update.yaml",map,tokenPattern);
    }

    public Response update(HashMap<String,Object> map){
        //todo:
        return null;
    }

    public Response updateAll(HashMap<String,Object> map){
        //todo:
        return null;
    }


}
