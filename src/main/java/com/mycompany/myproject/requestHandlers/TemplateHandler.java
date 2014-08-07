package com.mycompany.myproject.requestHandlers;


import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;

import com.mycompany.myproject.requestHandlers.util.BaseRequestHander;

public class TemplateHandler extends BaseRequestHander {

    public TemplateHandler(Vertx vertx) {
        super(vertx);
    }

    @Override
    public void get(HttpServerRequest httpServerRequest) {
        //httpServerRequest.response().sendFile("templates/ws.html");
    }
    
}
