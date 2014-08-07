package com.mycompany.myproject.requestHandlers.middleware;



import java.util.Set;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import io.netty.handler.codec.base64.Base64;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;

import com.mycompany.myproject.requestHandlers.util.BaseRequestHander;

public class RequestHandlerBasicAuth extends BaseRequestHander {
    
    public RequestHandlerBasicAuth(Vertx vertx, Handler<HttpServerRequest> next) {
        super(vertx, next);
    }

    @Override
    public void get(HttpServerRequest httpServerRequest) {
        String authorization = httpServerRequest.headers().get("authorization");
        String cookie = httpServerRequest.headers().get("cookie");
        System.out.println(authorization);
        System.out.println(cookie);
        if(authorization == null && cookie == null){
            handleFail(httpServerRequest);
        }else{
            if(cookie == null){
                if(checkUserAndPassword(authorization)){
                    UUID idOne = UUID.randomUUID();
                    
                    httpServerRequest.response().putHeader("Set-Cookie", "id="+idOne+"; path=/");
                    Set<String> sessionsSet = vertx.sharedData().getSet("users.sessions");
                    sessionsSet.add(idOne.toString());
                    
                    handleSuccess(httpServerRequest);
                }else{
                    handleFail(httpServerRequest);
                }
            }else{
                if(validateCookie(cookie, httpServerRequest) == true){
                    handleSuccess(httpServerRequest);
                }
            }
        }
    }
    
    private boolean validateCookie(String cookie, HttpServerRequest httpServerRequest){
       
        String cookieID = parseCookieString(cookie).get("id");
        if(cookieID == null)
            //handleFail(httpServerRequest);
            return false;
        System.out.println(cookieID);
        if(checkCookieSession(cookieID)){
            //handleSuccess(httpServerRequest);
            return true;
        }else{
            return false;
        }
    }
    private boolean checkCookieSession(String cookieAuth){
        Set<String> sessionsSet = vertx.sharedData().getSet("users.sessions");
        
        
        if(sessionsSet.contains(cookieAuth)){
            return true;
        }else{
            return false;
        }
    }
    
    private boolean checkUserAndPassword(String authorization){
        if(authorization == null)
            return false;
        String[] authorizationSplit = authorization.trim().split(" ");
        String decode1= new String(DatatypeConverter.parseBase64Binary(authorizationSplit[1]));
        System.out.println(decode1);
        String[] userPasswordSplit = decode1.split(":");
        if(userPasswordSplit.length != 2){
            return false;
        }
        String userName = userPasswordSplit[0];
        String password = userPasswordSplit[1];
        
        if(userName.trim().equalsIgnoreCase("manny") && password.equals("pass")){
            return true;
        }else{
            return false;
        }
    }
    
    
    private void handleSuccess(HttpServerRequest httpServerRequest){
        next.handle(httpServerRequest);
    }
    private void handleFail(HttpServerRequest httpServerRequest){
        httpServerRequest.response().setStatusCode(401);
        httpServerRequest.response().putHeader("WWW-Authenticate", "Basic realm=\"Authentication required\"");
        httpServerRequest.response().end("Not Authorized");
    }
}
