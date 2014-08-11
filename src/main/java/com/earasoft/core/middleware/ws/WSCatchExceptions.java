package com.earasoft.core.middleware.ws;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.vertx.java.core.http.ServerWebSocket;

import com.earasoft.core.http.BaseWSRequestHandler;

public class WSCatchExceptions extends BaseWSRequestHandler {

	
    public WSCatchExceptions(BaseWSRequestHandler next) {
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
