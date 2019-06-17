package com.testerhome.hogwarts.wework.brainPlatformApp;

import io.restassured.response.Response;

import java.util.HashMap;

public class PatientManager extends brainPlatformApp{

    String tokenPattern = "brainPlatform";

    public Response  getPatientInfo(){
        HashMap<String,Object> map = new HashMap<>();
        return getResponseFromSwagger(
                "/api/brainPlatformApp/PatientManager/restPatient.yaml",
                "/data/brainPlatform/swagger.json",
                map,
                tokenPattern);
    }
}
