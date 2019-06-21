package com.greenvalley.brainPlatform.brainPlatformApp;

import io.restassured.response.Response;

import java.util.HashMap;

public class PatientManager extends brainPlatformApp{

    private String tokenPattern = "brainPlatform";

    public Response  getPatientInfo(String patientName,String mobilephone){
        HashMap<String,Object> map = new HashMap<>();
        map.put("patientName",patientName);
        map.put("mobilephone",mobilephone);
        return getResponseFromSwagger(
                "/api/brainPlatformApp/PatientManager/restPatient.yaml",
                "/data/brainPlatformApp/AppSwagger.json",
                map,
                tokenPattern);
    }

    public Response createPatient(String patientName,String mobilephone){//
        //创建"patientName": "API测试患者","mobilephone": "13956588889",的患者
        HashMap<String,Object> map = new HashMap<>();
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
        HashMap<String,Object> map = new HashMap<>();
        map.put("uid",uid);
        return getResponseFromSwagger(
                "/api/brainPlatformApp/PatientManager/deletePatient.yaml",
                "/data/brainPlatformApp/AppSwagger.json",
                map,
                tokenPattern);
    }

    public Response getAllPatientInfoByList(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("_file", "/data/brainPlatformApp/PatientManager/getPatientByList.json");
        return getResponseFromYaml(
                "/api/brainPlatformApp/PatientManager/restpatientsList.yaml",
                map,
                tokenPattern);
    }

    public Response getPatientInfoByList(String patientName,Integer medicalHistory){
        HashMap<String,Object> map = new HashMap<>();
        map.put("patientName",patientName);
        map.put("medicalHistory",medicalHistory);
        map.put("_file", "/data/brainPlatformApp/PatientManager/getPatientByList.json");
        return getResponseFromYaml(
                "/api/brainPlatformApp/PatientManager/restpatientsList.yaml",
                map,
                tokenPattern);
    }

    /**
     * 1、选择“编辑患者”，若患者"patientName"和"mobilephone"都不变，则为编辑,"status"传入1
     * @return
     */
    public Response updatePatient(String updateElem,String updateInfo,Integer status){
        HashMap<String,Object> map = new HashMap<>();
        map.put(String.format("$.patient.%s",updateElem),updateInfo);//"上海上海市徐汇区南站"
        map.put("status",status);
        map.put("_file","/data/brainPlatformApp/PatientManager/updatePatient.json");
        return getResponseFromYaml(
                "/api/brainPlatformApp/PatientManager/updatePatient.yaml",
                map,
                tokenPattern
        );
    }

    /**
     * 2、选择“编辑患者”，若患者"patientName"和"mobilephone"都任意一个发生改变，则为新增,"status"传入1
     */
    public Response updatePatient(String patientName,Integer status){
        HashMap<String,Object> map = new HashMap<>();
        map.put("$.patient.patientName",patientName);
        map.put("status",status);
        map.put("_file","/data/brainPlatformApp/PatientManager/updatePatient.json");
        return getResponseFromYaml(
                "/api/brainPlatformApp/PatientManager/updatePatient.yaml",
                map,
                tokenPattern
        );
    }
}
