package com.testerhome.hogwarts.wework.brainPlatformApp;

import io.restassured.response.Response;

import java.util.HashMap;

public class PatientManager extends brainPlatformApp{

    String tokenPattern = "brainPlatform";
    HashMap<String,Object> map = new HashMap<>();
    public Response  getPatientInfo(){

        return getResponseFromSwagger(
                "/api/brainPlatformApp/PatientManager/restPatient.yaml",
                "/data/brainPlatformApp/AppSwagger.json",
                map,
                tokenPattern);
    }

    public Response createPatient(String patientName,String mobilephone){//
        //创建"patientName": "API测试患者","mobilephone": "13956588889",的患者
        map.put("$.patient.patientName",patientName);
        map.put("$.patient.mobilephone",mobilephone);
        map.put("_file","/data/brainPlatformApp/PatientManager/createPatient.json");
        return getResponseFromSwagger(
                "/api/brainPlatformApp/PatientManager/createPatient.yaml",
                "/data/brainPlatformApp/AppSwagger.json",
                map,
                tokenPattern
        );
    }

    public Response deletePatient(Integer uid){
        map.put("uid",uid);
        return getResponseFromSwagger(
                "/api/brainPlatformApp/PatientManager/deletePatient.yaml",
                "/data/brainPlatformApp/AppSwagger.json",
                map,
                tokenPattern);
    }

    public Response getAllPatientInfoByList(){
        map.put("_file", "/data/brainPlatformApp/PatientManager/getAllPatient.json");
        return getResponseFromYaml("/api/brainPlatformApp/PatientManager/restpatientsList.yaml",map,tokenPattern);
    }
}
