package com.testerhome.hogwarts.wework.brainPlatformApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class PatientManagerTest {

    PatientManager patientManager;
    Random random = new Random();
    @BeforeEach
    void setup(){
        if (patientManager == null){
            patientManager = new PatientManager();
        }
    }

    @Test
    void getPatientInfo() {
        //查询"patientName": "API测试患者","mobilephone": "13956588889",的患者
//        patientManager.createPatient().then().statusCode(200).body("status",equalTo("1"));
        patientManager.getPatientInfo().then().statusCode(200)
                .body("body.patient.patientName",equalTo("API测试患者"))
                .body("body.patient.mobilephone",equalTo("13956588889"));
    }

    @Test
    void createPatient() {
        //创建"patientName": "API测试患者","mobilephone": "13956588889",的患者
        String mobilephone = "1397878";
        for (int i = 0;i < 4; i++){
            mobilephone+= random.nextInt(9);
        }
        patientManager.createPatient("API测试患者"+random.nextLong(),mobilephone)
                .then().statusCode(200).body("status",equalTo("1"));
    }


    @Test
    void deletePatient() {
        patientManager.deletePatient(3).then().statusCode(200).body("status",equalTo("1"));
    }

    @Test
    void getAllPatientInfoByList() {
        patientManager.getAllPatientInfoByList().then().statusCode(200).body("status",equalTo("1"));
    }
}