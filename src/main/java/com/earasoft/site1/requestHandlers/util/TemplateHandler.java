package com.earasoft.site1.requestHandlers.util;


import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Container;

public class TemplateHandler extends BaseRequestHander {

    public TemplateHandler(Container container, Vertx vertx) {
        super(container, vertx);
    }

    @Override
    public void get(HttpServerRequest httpServerRequest) {
        //httpServerRequest.response().sendFile("templates/ws.html");
    }
    
}
