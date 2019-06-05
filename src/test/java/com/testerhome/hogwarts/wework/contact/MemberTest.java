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

    Random ra = new Random();
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
        map.put("mobile",151 + member.random.substring(0,7) + ra.nextInt(9));
        map.put("email",member.random.substring(0,7)+ ra.nextInt(9) + "@qq.com");
        member.create(map).then().statusCode(200).body("errcode",equalTo(0));
    }
}