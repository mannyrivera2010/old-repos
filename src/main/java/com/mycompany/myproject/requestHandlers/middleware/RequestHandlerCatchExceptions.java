package com.mycompany.myproject.requestHandlers.middleware;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;

import com.mycompany.myproject.requestHandlers.util.BaseRequestHander;

public class RequestHandlerCatchExceptions extends BaseRequestHander {

    public RequestHandlerCatchExceptions(Vertx vertx, Handler<HttpServerRequest> next) {
        super(vertx, next);
    }

    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        try{
            next.handle(httpServerRequest);
        }catch(Exception e){
            displayExceptions(httpServerRequest, e);
        }
    }
    
}
