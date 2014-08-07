/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 *
 */
package com.mycompany.myproject;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

import com.mycompany.myproject.requestHandlers.BasicStaticHandler;
import com.mycompany.myproject.requestHandlers.IndexHandler;
import com.mycompany.myproject.requestHandlers.NoMatchHandler;
import com.mycompany.myproject.requestHandlers.TestHandler;
import com.mycompany.myproject.requestHandlers.WSHandler;
import com.mycompany.myproject.requestHandlers.middleware.RequestHandlerBasicAuth;
import com.mycompany.myproject.requestHandlers.middleware.RequestHandlerCatchExceptions;
import com.mycompany.myproject.service.ClassicSingleton;


/*
 * This is a simple Java verticle which receives `ping` messages on the event bus and sends back `pong` replies
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class PingVerticle extends Verticle {
    
    public static final int DEFAULT_PORT = 8080;
    public static final String DEFAULT_ADDRESS = "0.0.0.0";
    
    public void start() {
        final Logger logger = container.logger();
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(requestHandlerPreChain());
        server.websocketHandler(getIndexWSHandler());
        
        ClassicSingleton.getInstance(vertx);
        
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
    
    protected Handler<ServerWebSocket> getIndexWSHandler() {
        return new Handler<ServerWebSocket>() {
            final Logger logger = container.logger();
            
            @Override
            public void handle(final ServerWebSocket ws) {
                final String id = ws.textHandlerID();
                logger.info("registering new connection with id: " + id + "");
                // vertx.sharedData().getSet("chat.room." + chatRoom).add(id);
                
                ws.closeHandler(new Handler<Void>() {
                    @Override
                    public void handle(final Void event) {
                        logger.info("un-registering connection with id: " + id
                                + "");
                        // vertx.sharedData().getSet("chat.room." +
                        // chatRoom).remove(id);
                    }
                });
                
                ws.dataHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(final Buffer data) {
                        ws.writeTextFrame(data.toString());
                        
                    }
                });
            }
        };
    }
    
    private Handler<HttpServerRequest> requestHandlerPreChain(){
        return new RequestHandlerCatchExceptions(vertx, routeMatcher());
    }

    protected RouteMatcher routeMatcher() {
        RouteMatcher matcher = new RouteMatcher();
        matcher.all("/", new IndexHandler(vertx));
        matcher.all("/ws", new WSHandler(vertx));
        matcher.all("/test", new RequestHandlerBasicAuth(vertx, new TestHandler(vertx)));
        matcher.allWithRegEx("/static/?(\\S*)", new BasicStaticHandler(vertx));
        matcher.noMatch(new NoMatchHandler(vertx));
        return matcher;
    }
    
}
