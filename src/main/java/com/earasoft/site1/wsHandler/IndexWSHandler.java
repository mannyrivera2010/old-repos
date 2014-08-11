package com.earasoft.site1.wsHandler;

import java.util.HashMap;
import java.util.Map;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.http.impl.WebSocketMatcher.Match;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Container;

import com.earasoft.site1.wsHandler.util.BaseWSRequestHandler;


public class IndexWSHandler extends BaseWSRequestHandler{
    final Logger logger;
    
    public IndexWSHandler(Container container, Vertx vertx){
    	super(container, vertx);
    	this.logger = this.container.logger();
    }
    
    class dataHandler implements Handler<Buffer>{
    	ServerWebSocket ws;
    	Map<String, String> resources;
    	
    	public dataHandler(ServerWebSocket ws, Map<String, String> resources){
    		this.ws = ws;
    		this.resources = resources;
    	}
    	
		@Override
		public void handle(Buffer data) {
			//int a = 0/0;
            ws.writeTextFrame(data.toString());
		}
    }
    
    class closeHandler implements Handler<Void>{
    	ServerWebSocket ws;
    	Map<String, String> resources;
    	
    	public closeHandler(ServerWebSocket ws,  Map<String, String> resources){
    		this.ws = ws;
    		this.resources = resources;
    	}
    	
		@Override
		public void handle(Void event) {
			// TODO Auto-generated method stub
			 logger.info("un-registering connection with id: " + resources.get("id")
                     + "");
             // vertx.sharedData().getSet("chat.room." +
             // chatRoom).remove(id);
		}
    	
    }
    
    @Override
    public void handle(final ServerWebSocket ws) {
    	final Map<String, String> resources = new HashMap<String,String>();
    	resources.put("id", ws.textHandlerID());
        logger.info("registering new connection with id: " + resources.get("id") + "");
        // vertx.sharedData().getSet("chat.room." + chatRoom).add(id);
        ws.dataHandler(new dataHandler(ws, resources));
        ws.closeHandler(new closeHandler(ws, resources));
    }
 
}
