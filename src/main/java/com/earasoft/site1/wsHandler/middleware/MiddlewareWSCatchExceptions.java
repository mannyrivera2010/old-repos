package com.earasoft.site1.wsHandler.middleware;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.ServerWebSocket;

import com.earasoft.site1.requestHandlers.util.BaseRequestHander;
import com.earasoft.site1.wsHandler.util.BaseWSRequestHandler;

public class MiddlewareWSCatchExceptions extends BaseWSRequestHandler {

	
    public MiddlewareWSCatchExceptions(BaseWSRequestHandler next) {
        super(next);
        this.container = next.getContainer();
        this.vertx = next.getVertx();
        
    }

    @Override
    public void handle(ServerWebSocket wsRequestHander) {
        try{
            next.handle(wsRequestHander);
        }catch(Exception e){
            displayExceptions(wsRequestHander, e);
        }
    }
       
    public void displayExceptions(ServerWebSocket wsRequestHander, Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        
        boolean debug = true;
        
        if(debug){
        	wsRequestHander.writeTextFrame(exceptionAsString);
        }else{
        	wsRequestHander.writeTextFrame("Internal Error");
        }
    }
}
