package com.greenvalley.brainPlatform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.HashMap;


public class WeworkConfig {

    public String agentId = "1000002";
    public String secret = "FEONPjYvrJ67U63BW8E0czXL4jp17jf0f8P-8gjyYM0";
    public String corid = "wwf3beef22f578387a";
    public String contactSecret = "jYw-kCkdLmb8ox2BlxDt89q9jP4Zsn8BTU9IWP7ra6U";

    private static WeworkConfig weworkConfig;

    public String currentEnv = "test";
    public HashMap<String,HashMap<String,String>> env;

    public static WeworkConfig getInstance(){
        if (weworkConfig == null){
            weworkConfig =load("/conf/WeworkConfig.yaml");
            System.out.println(weworkConfig.env);
        }
        return weworkConfig;
    }

    public static WeworkConfig load(String path){
        //todo:read from yaml or json
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//        ObjectMapper mapper1 = new ObjectMapper(new JsonFactory());
        try {
            return mapper.readValue(WeworkConfig.class.getResourceAsStream(path),WeworkConfig.class);
//            System.out.println(mapper1.writerWithDefaultPrettyPrinter().writeValueAsString(WeworkConfig.getInstance()));
//            System.out.println(mapper.writeValueAsString(WeworkConfig.getInstance()));
        } catch ( IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
