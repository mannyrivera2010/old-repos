package com.earasoft.site1.requestHandlers;


import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Container;

import com.earasoft.core.http.BaseRequestHander;

public class TestHandler extends BaseRequestHander {

    public TestHandler(Container container, Vertx vertx) {
    	super(container, vertx);
    }

    @Override
    public void get(HttpServerRequest httpServerRequest) {
        httpServerRequest.response().end("Works");
    }
    
}
