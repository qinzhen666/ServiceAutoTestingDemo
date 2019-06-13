package com.testerhome.hogwarts.wework.contact;

import com.testerhome.hogwarts.wework.Api;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;


public class Department extends Api {

    /**
     * 列出部门
     * @param id
     * @return
     */
    public Response list(String id,String tokenPattern){
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",id);
        return getResponseFromYaml("/api/list.yaml",map,tokenPattern);
    }

    /**
     * 创建部门
     * @param name
     * @param parentid
     * @return
     */
    public Response create(String name,String parentid,String tokenPattern){
        //让用例更清晰
        HashMap<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("parentid",parentid);
        map.put("_file","/data/create.json");
        return getResponseFromYaml("/api/create.yaml",map,tokenPattern);
    }

    public Response create(HashMap<String,Object> map,String tokenPattern){
        map.put("_file","/data/create.json");
        return getResponseFromYaml("/api/create.yaml",map,tokenPattern);
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    public Response delete(String id,String tokenPattern){
    HashMap<String,Object> map = new HashMap<>();
    map.put("id",id);
    return getResponseFromYaml("/api/delete.yaml",map,tokenPattern);
    }

    public Response deleteAll(String tokenPattern){
        List<Integer> idlist = list("",tokenPattern).then().log().all().extract().path("department.id");
        System.out.println(idlist);
        for (Integer id: idlist) {
            delete(id.toString(),tokenPattern);
        }
        return null;
    }

    /**
     * 更新部门
     * @param id
     * @param name
     * @return
     */
    public Response update(String id,String name,String tokenPattern){
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("_file","/data/update.json");
        return getResponseFromYaml("/api/update.yaml",map,tokenPattern);
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
