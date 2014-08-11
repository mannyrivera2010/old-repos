package com.earasoft.core.http;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.platform.Container;

public abstract class BaseWSRequestHandler implements Handler<ServerWebSocket> {

    protected Vertx vertx;
	protected Container container;
    protected Handler<ServerWebSocket> next;
    
    public BaseWSRequestHandler(Container container, Vertx vertx){
        this.container = container;
    	this.vertx = vertx;
    }
       
    public BaseWSRequestHandler(BaseWSRequestHandler next) {
        this.next = next;
    }

    public Vertx getVertx() {
		return vertx;
	}

	public Container getContainer() {
		return container;
	}

}
