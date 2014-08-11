package com.earasoft.site1.requestHandlers;
import org.vertx.java.core.MultiMap;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Container;

import com.earasoft.core.http.BaseRequestHander;

public class BasicStaticHandler extends BaseRequestHander {

    public BasicStaticHandler(Container container, Vertx vertx) {
        super(container, vertx);
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
