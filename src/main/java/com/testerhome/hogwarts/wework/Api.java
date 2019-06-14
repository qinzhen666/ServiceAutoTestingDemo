package com.testerhome.hogwarts.wework;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.swagger.reader.SwaggerReader;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

public class Api {
    HashMap<String, Object> query = new HashMap<>();

    public Api(){
        useRelaxedHTTPSValidation();
    }

    public RequestSpecification getDefaultRequestSpecification(String tokenPattern) {
        RequestSpecification requestSpecification = given().log().all();
        return requestSpecification;
    }

    public static String template(String path, HashMap<String, Object> map) {
        DocumentContext documentContext = JsonPath.parse(Api.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return documentContext.jsonString();
    }


//    public static String templateFromMustache(String path,HashMap<String,Object> map){
//        MustacheFactory mf = new DefaultMustacheFactory();
//        Mustache mustache = mf.compile("/data/create.mustache");
//        mustache.execute(new PrintWriter(System.out), new Example()).flush());
//    }

    public Restful getApiFromYaml(String yamlPath){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(WeworkConfig.class.getResourceAsStream(yamlPath), Restful.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Restful getApiFromHar(String jsonPath,String pattern){
        HarReader harReader = new HarReader();
        try {
            Har har = harReader.readFromFile(new File(
                    URLDecoder.decode(
                            getClass().getResource(jsonPath).getPath(),"utf-8"
                    )));
            HarRequest request = new HarRequest();
            Boolean match = false;
            for (HarEntry entry : har.getLog().getEntries()) {
                request = entry.getRequest();
                if (request.getUrl().matches(pattern)) {
                    match = true;
                    break;
                }
            }
            if (match == false){
                request = null;
                throw new Exception("error url");
            }
            Restful restful = new Restful();
            restful.method = request.getMethod().toString();
            //todo:去掉url中的query部分
            System.out.println(request.getUrl());
            restful.url = request.getUrl().split("\\?")[0];
            System.out.println(restful.url);
            request.getQueryString().forEach(q->{
                restful.query.put(q.getName(),q.getValue());
            });
            restful.body = request.getPostData().getText();
            return restful;
        } catch (HarReaderException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Restful getApiFromSwagger(String yamlPath,String jsonPath){
        //todo:从swagger中获取接口数据
        HashMap<String,Object> map = new HashMap<>();
        Restful restful = getApiFromYaml(yamlPath);
        System.out.println(restful.uri);
        DocumentContext documentContext = JsonPath.parse(Api.class
                .getResourceAsStream(jsonPath));
        String queryString = documentContext.read(String.format("$.paths.%s.*.parameters", restful.uri)).toString();
        System.out.println(queryString);

        for (Object q : JSONArray.parseArray(queryString)){
            ArrayList<Object> list = new Gson().fromJson(String.valueOf(q), ArrayList.class);
            System.out.println(list.size());
            if (list.size() > 1){
                //第一个都是token，不需要获取，从第二个数据开始获取queryParam
                for (int i = 1; i < list.size(); i++) {
                    System.out.print(list.get(i)+"\t");
                        map = new Gson().fromJson(String.valueOf(list.get(i)),HashMap.class);
                        restful.query.put(map.get("name").toString(),map.get("value").toString());
                        System.out.println("name=="+map.get("name"));
                    }
                }
        }
        if (documentContext.read("$.paths").toString().contains(restful.uri)){
            if (restful.method.toLowerCase().equals("post")){
                restful.body = documentContext.read(
                        String.format("$.paths.%s.*.requestBody.body", restful.uri))
                        .toString().split("\\[|\\]")[1];
                System.out.println(restful.body);
                return restful;
            }
        }
        return restful;
    }

    public Restful updateApiFromMap(Restful restful,HashMap<String,Object> map){
        if (map == null){
            return restful;
        }
        if (restful.method.toLowerCase().contains("get")) {
            map.entrySet().forEach(entry -> {
                restful.query.replace(entry.getKey(), entry.getValue());
            });
        }
        if (restful.method.toLowerCase().contains("post")) {
            if (map.containsKey("_body")) {
                restful.body = map.get("_body").toString();
            }
            if (map.containsKey("_file")) {
                String filePath = map.get("_file").toString();
                map.remove("_file");
                restful.body = template(filePath, map);
            }
        }
        return restful;
    }

    public Response getResponseFromApi(Restful restful,String tokenPattern){
//        RequestSpecification requestSpecification = getDefaultRequestSpecification(tokenPattern);
        RequestSpecification requestSpecification = getDefaultRequestSpecification(tokenPattern);
        if (restful.query != null) {
            restful.query.entrySet().forEach(entry -> {
                requestSpecification.queryParam(entry.getKey(), entry.getValue());
            });
        }
        if (restful.body != null) {
            requestSpecification.body(restful.body);
        }

        String[] UPurl = updateUrl(restful.url);
        return requestSpecification.log().all()
                .header("Host",UPurl[0])
                .request(restful.method, UPurl[1])
                .then().log().all().extract().response();
    }

    private String[] updateUrl(String url) {
        //fixed:多环境支持，替换url，更新host的header
        HashMap<String, String> hosts = WeworkConfig.getInstance().env.get(WeworkConfig.getInstance().currentEnv);
        String host = "";
        String urlNew = "";
        for (Map.Entry<String,String> entry : hosts.entrySet()){
            System.out.println(entry.getKey());
            if (url.contains(entry.getKey())){
                host = entry.getKey();
                urlNew = url.replace(entry.getKey(),entry.getValue());
            }
        }return new String[]{host,urlNew};

    }

    public Response getResponseFromYaml(String yamlPath, HashMap<String, Object> map,String tokenPattern) {
        //fixed:根据yaml生成接口定义并发送
            Restful restful = getApiFromYaml(yamlPath);
            restful = updateApiFromMap(restful, map);
            return getResponseFromApi(restful,tokenPattern);
    }

    public Response getResponseFromHar(String jsonPath, String pattern, HashMap<String,Object> map,String tokenPattern){
        //fixed:支持从har文件中读取接口定义并发送
        Restful restful = getApiFromHar(jsonPath, pattern);
        restful = updateApiFromMap(restful,map);
        return getResponseFromApi(restful,tokenPattern);
    }

    public Response getResponseFromSwagger(String yamlPath,String jsonPath, HashMap<String,Object> map,String tokenPattern){
        Restful restful = getApiFromSwagger(yamlPath,jsonPath);
        restful = updateApiFromMap(restful,map);
        return getResponseFromApi(restful,tokenPattern);
    }
}
