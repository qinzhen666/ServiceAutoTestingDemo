package com.testerhome.hogwarts.wework;

public class WeworkConfig {

    public String agentId = "1000002";
    public String secret = "FEONPjYvrJ67U63BW8E0czXL4jp17jf0f8P-8gjyYM0";
    public String corid = "wwf3beef22f578387a";
    public String contactSecret = "jYw-kCkdLmb8ox2BlxDt89q9jP4Zsn8BTU9IWP7ra6U";

    private static WeworkConfig weworkConfig;
    public static WeworkConfig getInstance(){
        if (weworkConfig == null){
            weworkConfig = new WeworkConfig();
        }
        return weworkConfig;
    }

    public static void load(String path){
        //todo:read from yaml or json
    }
}
