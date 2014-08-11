package com.earasoft.site1.requestHandlers;


import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Container;

import com.earasoft.site1.requestHandlers.util.BaseRequestHander;

public class NoMatchHandler extends BaseRequestHander {

    public NoMatchHandler(Container container, Vertx vertx) {
    	super(container, vertx);
    }

    @Override
    public void get(HttpServerRequest httpServerRequest) {
        httpServerRequest.response().setStatusCode(404).end("NOT FOUND ERROR");
    }
    
}
