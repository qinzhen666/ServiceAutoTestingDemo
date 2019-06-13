package com.testerhome.hogwarts.wework.contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.HashMap;
import java.util.Random;
import static org.hamcrest.Matchers.equalTo;

class DepartmentTest {

    static String tokenPattern = "wechat";
    Random random = new Random();
    Department department;
    @BeforeEach
    void setUp(){
        System.out.println("开始setup");
        if (department == null){
            department  = new Department();
            department.deleteAll(tokenPattern);
            System.out.println("结束setup");
        }
    }

    @Test
    void list() {
        department.list("",tokenPattern).then().statusCode(200).body("department.id[0]",equalTo(1));
    }

    @Test
    void create() {
        department.create("秦朕DBM_1"+random,"2",tokenPattern).then().body("errcode",equalTo(0));
//        department.create("秦朕DBM_1","3").then().body("errcode",equalTo(60008));
    }

    @Test
    void createByMap(){
        HashMap<String,Object> map = new HashMap<String,Object>(){{
            put("name","李大燕" + random.nextLong());
            put("parentid",2);
        }
        };
        department.create(map,tokenPattern).then().body("errcode",equalTo(0));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/createWithDup.csv")
    void createWithDup(String name,Integer expectCode){
        department.create(name+random.nextLong(),"1",tokenPattern).then().body("errcode",equalTo(0));
        department.create(name+random.nextLong(),"1",tokenPattern).then().body("errcode",equalTo(expectCode));
    }

    @Test
    void delete(){
        String oldName = "updateDepartment_1"+ random.nextLong();
        department.create(oldName,"1",tokenPattern);
        String id = department.list("",tokenPattern).path("department.find{it.name=='"+ oldName +"'}.id").toString();
        department.delete(id,tokenPattern).then().body("errcode",equalTo(0));
        department.list(id,tokenPattern).then().body("errcode",equalTo(60123));
    }

    @Test
    void update(){
        String oldName = "updateDepartment_1"+ random.nextLong();
        department.create(oldName,"2",tokenPattern);
        String id = department.list("",tokenPattern).path("department.find{it.name=='"+ oldName +"'}.id").toString();
        department.update(id,"updateDepartment_2"+random.nextLong(),tokenPattern).then().body("errcode",equalTo(0));
//        department.list(id).then().body("department.name[0]",equalTo("updateDepartment_2"));
    }

    @Test
    void deleteAll() {
        department.deleteAll(tokenPattern);
    }
}