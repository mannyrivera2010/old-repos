package com.mycompany.myproject.requestHandlers;


import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;

import com.mycompany.myproject.requestHandlers.util.BaseRequestHander;

public class NoMatchHandler extends BaseRequestHander {

    public NoMatchHandler(Vertx vertx) {
        super(vertx);
    }

    @Override
    public void get(HttpServerRequest httpServerRequest) {
        httpServerRequest.response().setStatusCode(404).end("NOT FOUND ERROR");
    }
    
}
