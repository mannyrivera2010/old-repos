package com.earasoft.site1.requestHandlers;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Container;

import com.earasoft.core.http.BaseRequestHander;

import freemarker.template.TemplateException;

public class BootHandler extends BaseRequestHander {

    public BootHandler(Container container, Vertx vertx) {
        super(container, vertx);
    }

    @Override
    public void get(HttpServerRequest httpServerRequest) {
    	Map oba = new HashMap();
    	try {
			httpServerRequest.response().end(render("templates\\boot.ftl", oba));
		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpServerRequest.response().end(e.toString());
		}
    }
    
}
