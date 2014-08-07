package com.mycompany.myproject.requestHandlers;
import java.util.Map;

import org.vertx.java.core.MultiMap;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;

import com.mycompany.myproject.requestHandlers.util.BaseRequestHander;

public class BasicStaticHandler extends BaseRequestHander {

    public BasicStaticHandler(Vertx vertx) {
        super(vertx);
    }

    @Override
    public void get(HttpServerRequest httpServerRequest) {
        //httpServerRequest.response().sendFile("templates/ws.html");
        httpServerRequest.response().setChunked(true);
        httpServerRequest.response().putHeader("Text-Content","text/html");
        MultiMap requestParams = httpServerRequest.params();
        httpServerRequest.response().write(requestParams.get("param0").toString()+ "<br>Hello");

        
        httpServerRequest.response().end();
        
        

    }
    
}
