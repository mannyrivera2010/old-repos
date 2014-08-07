package com.mycompany.myproject.requestHandlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;

import com.mycompany.myproject.requestHandlers.util.BaseRequestHander;

import freemarker.template.TemplateException;

public class IndexHandler extends BaseRequestHander {

    public IndexHandler(Vertx vertx) {
        super(vertx);
    }

    @Override
    public void post(HttpServerRequest httpServerRequest) throws Exception{
        ConcurrentMap<String ,Integer> indexCounter = vertx.sharedData().getMap("Index");
        indexCounter.put("counter", indexCounter.getOrDefault("counter", 0) + 1); 

        String FileName = "templates\\index.ftl".replace("\\\\", "\\");
        System.out.println("Current dir using System:" +FileName);
        
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("message", "Hello World!" + indexCounter.getOrDefault("counter", -1));
        
        // Language list
        List<String> language = new ArrayList<String>();
        language.add("Java");
        language.add("C++");
        language.add("Ceylon");
        language.add("Cobol");
        
        data.put("languages", language);
        
        httpServerRequest.response().putHeader("Set-Cookie", "aaa=111; path=/");
        httpServerRequest.response().end(render("templates\\index.ftl", data));
    }
    
}
