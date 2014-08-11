package com.earasoft.site1;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

import com.earasoft.core.middleware.BasicAuth;
import com.earasoft.core.middleware.CatchExceptions;
import com.earasoft.core.middleware.ws.WSCatchExceptions;
import com.earasoft.site1.requestHandlers.BasicStaticHandler;
import com.earasoft.site1.requestHandlers.IndexHandler;
import com.earasoft.site1.requestHandlers.NoMatchHandler;
import com.earasoft.site1.requestHandlers.TestHandler;
import com.earasoft.site1.requestHandlers.WSHandler;
import com.earasoft.site1.service.SingletonService;
import com.earasoft.site1.wsHandler.IndexWSHandler;

/*
 * This is a simple Java is FrameWork
 *
 * @author <a href="http://earasoft.com.org">Emanuel Rivera</a>
 */
public class MainVerticle extends Verticle {
    
    public static final int DEFAULT_PORT = 8080;
    public static final String DEFAULT_ADDRESS = "0.0.0.0";
    
    public void start() {
        final Logger logger = container.logger();
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(requestHandlerPreChain());
        server.websocketHandler(requestWSHandlerPreChain());
        
        SingletonService.getInstance(vertx);
        
        server.listen(DEFAULT_PORT, DEFAULT_ADDRESS,
                new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> ar) {
                if (!ar.succeeded()) {
                    // result.setFailure();
                    System.out.println(ar.cause());
                    logger.info(ar.cause());
                } else {
                    // result.setResult(null);
                    logger.info("Succesed" + ar.result());
                }
            }
        });
        logger.info("WebServer started");
    }
    
    private Handler<ServerWebSocket> requestWSHandlerPreChain(){
    	//WebSocketMatcher matcher = new WebSocketMatcher();
    	//matcher.addPattern("/app", );
    	Handler<ServerWebSocket> middlewareWSCatchExceptions = new WSCatchExceptions(new IndexWSHandler(container, vertx));
    	return middlewareWSCatchExceptions;
    }

    private Handler<HttpServerRequest> requestHandlerPreChain(){
    	Handler<HttpServerRequest> routeMatcher = routeMatcher();
    	Handler<HttpServerRequest> middlewareCatchExceptions = new CatchExceptions(container, vertx, routeMatcher); 
        return middlewareCatchExceptions;
    }

    protected RouteMatcher routeMatcher() {
        RouteMatcher matcher = new RouteMatcher();
        matcher.all("/", new IndexHandler(container, vertx));
        matcher.all("/ws", new WSHandler(container, vertx));
        matcher.all("/test", new BasicAuth(new TestHandler(container, vertx)));
        matcher.allWithRegEx("/static/?(\\S*)", new BasicStaticHandler(container, vertx));
        matcher.noMatch(new NoMatchHandler(container, vertx));
        return matcher;
    }
    
}
