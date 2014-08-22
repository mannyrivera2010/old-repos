package com.earasoft.site1.requestHandlers;
import org.vertx.java.core.MultiMap;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Container;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.vertx.java.core.Handler;
import org.vertx.java.core.file.FileProps;
import org.vertx.java.core.file.FileSystem;
import org.vertx.java.core.json.JsonObject;




import com.earasoft.core.http.BaseRequestHander;
import com.earasoft.site1.service.SingletonService;

import freemarker.template.Configuration;

public class BasicStaticHandler extends BaseRequestHander {

	private FileSystem fileSystem;
	
    public BasicStaticHandler(Container container, Vertx vertx) {
        super(container, vertx);
        this.fileSystem = vertx.fileSystem();
    }

    @Override
    public void get(HttpServerRequest httpServerRequest) {
    	 // browser gzip capability check
    	//String acceptEncoding =  httpServerRequest.headers().get(Headers.ACCEPT_ENCODING);
    	//boolean acceptEncodingGzip = acceptEncoding == null ? false : acceptEncoding.contains("gzip");
    	
        //httpServerRequest.response().sendFile("templates/ws.html");
        
        //httpServerRequest.response().putHeader("Text-Content","text/html");
        MultiMap requestParams = httpServerRequest.params();
        
        File vertxPath = SingletonService.getInstance(vertx).getVertxPath();
        String paramFullPath = vertxPath.getAbsolutePath() +"/static/" + requestParams.get("param0").toString();
        Boolean fileExist = fileSystem.existsSync(paramFullPath);
        
        if(fileExist){
        	httpServerRequest.response().sendFile(paramFullPath);
        }else{
    	  httpServerRequest.response().setChunked(true);
          httpServerRequest.response().write("<html><body>");
          httpServerRequest.response().write("Param:" + requestParams.get("param0").toString() +"<br>");
          httpServerRequest.response().write("Vertx Path:" + vertxPath.getAbsolutePath()+"<br>");
          httpServerRequest.response().write("Path:" + paramFullPath+"<br>");
          httpServerRequest.response().write("Exist"+ fileExist +"<br>");
          httpServerRequest.response().write("</body></html>");
          httpServerRequest.response().end();
        }
      
        
    }
    
}
