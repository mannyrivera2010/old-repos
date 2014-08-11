package com.earasoft.site1.wsHandler.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.platform.Container;

import com.earasoft.site1.service.ClassicSingleton;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class BaseWSRequestHandler implements Handler<ServerWebSocket> {

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

	@Override
	public void handle(ServerWebSocket event) {
		throw new UnsupportedOperationException("Child Class missing handle event method");
		
	}
}
