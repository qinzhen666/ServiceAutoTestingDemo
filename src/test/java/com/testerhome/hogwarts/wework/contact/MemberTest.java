package com.testerhome.hogwarts.wework.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    static String tokenPattern = "weichat";
    static Random ra = new Random();
    static Member member;
    @BeforeEach
    void setUp() {
        member  = new Member();
    }

    @ParameterizedTest
    @ValueSource(strings = {"qinzhen_","liyan_"})
    void create(String name) {
        String nameNew = name + member.random;
        HashMap<String,Object> map = new HashMap<>();
        map.put("userid",nameNew);
        map.put("name",nameNew);
        map.put("department", Arrays.asList(1,2));
        map.put("mobile",151 + member.random.substring(0+5,8+5) );
        map.put("email",member.random.substring(0+5,8+5) + "@qq.com");
        member.create(map,tokenPattern).then().statusCode(200).body("errcode",equalTo(0));
    }

    @Test
    static HashMap createForTest(String name){
        String nameNew = name + member.random;
        HashMap<String,Object> map = new HashMap<>();
        map.put("userid",nameNew);
        map.put("name",nameNew);
        map.put("department", Arrays.asList(1,2));
        map.put("mobile",151 + member.random.substring(0,7) + ra.nextInt(9));
        map.put("email",member.random.substring(0,7)+ ra.nextInt(9) + "@qq.com");
        return map;
    }

    @Test
    void read() {
        //path("department.find{it.name=='"+ oldName +"'}.id").toString();
        HashMap map = MemberTest.createForTest("readUser_");
        member.create(map,tokenPattern);
        String userid = String.valueOf(map.get("userid"));
        System.out.println("userID是" + userid);
        member.read(userid,tokenPattern).then().statusCode(200).body("errcode",equalTo(0));
    }

    @Test
    void update() {
        HashMap map = MemberTest.createForTest("updateUser_");
        member.create(map,tokenPattern);
        String userid = String.valueOf(map.get("userid"));
        System.out.println("userID是" + userid);
        map.put("alias","updateman");
        member.update(map,tokenPattern).then().statusCode(200).body("errcode",equalTo(0));
        member.read(userid,tokenPattern).then().statusCode(200).body("alias",equalTo("updateman"));
    }

    @Test
    void delete() {
        HashMap map = MemberTest.createForTest("deleteUser");
        member.create(map,tokenPattern);
        String userid = String.valueOf(map.get("userid"));
        member.delete(userid,tokenPattern).then().statusCode(200).body("errcode",equalTo(0));
        member.read(userid,tokenPattern).then().statusCode(200).body("errcode",equalTo(60111));//用户不存在返回60111
    }

    @Test
    void getUserSimplelist() {
        member.getUserSimplelist("2","1",tokenPattern).then().statusCode(200).body("errcode",equalTo(0));
    }

    @Test
    void batchdelete() {
        member.batchdelete(tokenPattern).then().statusCode(200).body("errcode",equalTo(0));
    }
}