package com.testerhome.hogwarts.wework;

import java.util.HashMap;

public class Restful {
    public String url;
    public String uri;
    public String method;
    public HashMap<String,Object> headers;
    public HashMap<String,Object> query = new HashMap<>();
    public String body;
}
