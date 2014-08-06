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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.file.impl.PathAdjuster;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.http.impl.WebSocketMatcher;
import org.vertx.java.core.http.impl.WebSocketMatcher.Match;
import org.vertx.java.core.impl.VertxInternal;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.core.sockjs.SockJSServer;
import org.vertx.java.platform.Verticle;
import org.rythmengine.Rythm;

import freemarker.template.Configuration;
import freemarker.template.Template;

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
        server.requestHandler(routeMatcher());
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
    
    protected RouteMatcher routeMatcher() {
        RouteMatcher matcher = new RouteMatcher();
        matcher.get("/", getIndexHandler());
        matcher.get("/ws", getWSHandler());
        matcher.get("/test", getTestHandler());
        matcher.get("/auth", getAuthHandler());
        matcher.noMatch(noMatchHandler());
        return matcher;
    }
    
    protected Handler<HttpServerRequest> getIndexHandler() {
        return new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest httpServerRequest) {
                Configuration cfg = ClassicSingleton.getInstance(vertx).getCfg();

                try {
                       
                    ConcurrentMap<String ,Integer> indexCounter = vertx.sharedData().getMap("Index");
                    
                    indexCounter.put("counter", indexCounter.getOrDefault("counter", 0) + 1); 
                    
                    // Load the template
                    
                    String FileName = "templates\\index.ftl".replace("\\\\", "\\");
                    System.out.println("Current dir using System:" +FileName);
                    Template template = cfg.getTemplate(FileName);
                    
                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("message", "Hello World!" + indexCounter.getOrDefault("counter", -1));
                    
                    // Language list
                    List<String> language = new ArrayList<String>();
                    language.add("Java");
                    language.add("C++");
                    language.add("Ceylon");
                    language.add("Cobol");
                    
                    data.put("languages", language);
                    
                    ByteArrayOutputStream baStream = new ByteArrayOutputStream();
                    Writer out = new OutputStreamWriter(baStream);
                    template.process(data, out);
                    System.out.println(baStream.toString());
                    
                    httpServerRequest.response().putHeader("Set-Cookie", "aaa=111; path=/");
                    
                    httpServerRequest.response().end(baStream.toString());
                    
                    out.flush();
                } catch (IOException e) {
                    System.out.println(e);
                    e.printStackTrace();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            };
        };
    }
    
    protected Handler<HttpServerRequest> getWSHandler() {
        return new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest httpServerRequest) {
                httpServerRequest.response().sendFile("templates/ws.html");
            };
        };
    }
    
    protected Handler<HttpServerRequest> getAuthHandler() {
        return new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest httpServerRequest) {
                try{
                    String authorization = httpServerRequest.headers().get("authorization");
                    
                    if(authorization == null){
                        httpServerRequest.response().setStatusCode(401);
                        httpServerRequest.response().putHeader("WWW-Authenticate", "Basic realm=\"Authentication required\"");
                        
                        System.out.print(httpServerRequest.headers().toString());
                        httpServerRequest.response().end("ERROR");
                        System.out.print("getAuthHandler() End");
                    }else{
                        String cookie = httpServerRequest.response().headers().get("cookie");
                        
                        if(cookie == null){
                            if(authorization.equals("Basic bWFubnk6")){
                                httpServerRequest.response().putHeader("Set-Cookie", "auth="+authorization+"; path=/");
                                httpServerRequest.response().end();
                                return;
                            }else{
                                
                                httpServerRequest.response().setStatusCode(401);
                                httpServerRequest.response().putHeader("WWW-Authenticate", "Basic realm=\"Authentication required\"");
                                httpServerRequest.response().end(authorization);
                            }
                        }else{
                            
                        }
                        httpServerRequest.response().setChunked(true);
                        httpServerRequest.response().write(httpServerRequest.response().headers().get("cookie")+"<br>");
                        httpServerRequest.response().end(authorization);                      
                        

                    }
                }catch(Exception E){
                    httpServerRequest.response().end(E.toString());
                }
            };
        };
    }
    
    
    protected Handler<HttpServerRequest> getTestHandler() {
        return new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest httpServerRequest) {
                httpServerRequest.response().end("Test");
            };
        };
    }
    
    protected Handler<HttpServerRequest> noMatchHandler() {
        return new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest httpServerRequest) {
                httpServerRequest.response().setStatusCode(404).end("ERROR");
            };
        };
    }
    
}
