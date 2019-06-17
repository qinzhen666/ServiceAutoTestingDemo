package com.testerhome.hogwarts.wework.brainPlatformApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class PatientManagerTest {

    PatientManager patientManager;
    @BeforeEach
    void setup(){
        if (patientManager == null){
            patientManager = new PatientManager();
        }
    }

    @Test
    void getPatientInfo() {
        patientManager.getPatientInfo().then().statusCode(200).body("status",equalTo("1"));
    }
}