package com.greenvalley.brainPlatform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.junit.jupiter.api.Test;

import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;
import io.swagger.util.Yaml;
import io.swagger.util.Json;


class ApiTest {

    Api api = new Api();

    @Test
    void templateFromYaml() {

        api.getResponseFromYaml("/api/weChat/list.yaml",null,"wechat").then().statusCode(200);
    }

    @Test
    void getApiFromHar() {

        System.out.println(api.getApiFromHar("/api/weChat/app.har.json", ".*tid=67.*").url);
        System.out.println(api.getApiFromHar("/api/weChat/app.har.json", "tid=41").url);
    }


    @Test
    void getResponseFromHar() {

        api.getResponseFromHar("/api/weChat/app.har.json",".*tid=67.*",null,"weichat");
    }

    @Test
    void mustache() throws Exception{
        HashMap<String, Object> scopes = new HashMap<String, Object>();
        scopes.put("name", "Mustache");

        Writer writer = new OutputStreamWriter(System.out);
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader("name: wwwsss {{name}}"), "example");
        mustache.execute(writer, scopes);
        writer.flush();
    }

    @Test
    void readSwagger() throws JsonProcessingException {
        OpenAPI openAPI = new OpenAPIV3Parser().read("http://47.103.47.170:8080/brainPlatformApp/v2/api-docs?group=app");
//        OpenAPI openAPI1 = new OpenAPIV3Parser().read("/data/AppSwagger.json");
//        Swagger swagger = new SwaggerParser().read("http://47.103.47.170:8080/brainPlatform/v2/api-docs?group=ap");
        String yamlOutput = Yaml.pretty().writeValueAsString(openAPI);
        String jsonOutput = Json.pretty(openAPI);
        System.out.println(jsonOutput);

            }


    @Test
    void getApiFromSwagger() throws NoSuchMethodException {

        api.getApiFromSwagger("/api/brainPlatformApp/SwaggerDemo.yaml", "/data/brainPlatformApp/AppSwagger.json");
    }


    @Test
    void getResponseFromSwagger() {

        api.getResponseFromSwagger(
                "/api/brainPlatformApp/SwaggerDemo.yaml", "/data/brainPlatformApp/AppSwagger.json",null,"brainPlatformTest").then().statusCode(200);
    }

    @Test
    void getResponseFromYaml() {
        api.getResponseFromYaml("/api/weChat/create.yaml",null,"wechat");
    }
}


