package com.earasoft.core.middleware;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Container;

import com.earasoft.core.http.BaseRequestHander;

public class CatchExceptions extends BaseRequestHander {
	
    public CatchExceptions(BaseRequestHander next) {
        super(next);
        this.container = next.getContainer();
        this.vertx = next.getVertx();
    }

	public CatchExceptions(Container container, Vertx vertx, Handler<HttpServerRequest> next) {
		super(container, vertx, next);
        this.container = container;
        this.vertx = vertx;
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
