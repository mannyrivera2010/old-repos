package com.earasoft.site1.requestHandlers.middleware;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.DatatypeConverter;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Container;

import com.earasoft.site1.requestHandlers.util.BaseRequestHander;

public class MiddlewareBasicAuth extends BaseRequestHander {
    
    private ConcurrentMap<String, Object> sessionsSet;
	
    public MiddlewareBasicAuth(BaseRequestHander next) {
        super(next);
        this.container = next.getContainer();
        this.vertx = next.getVertx();
        
        this.sessionsSet = vertx.sharedData().getMap("users.sessions");
    }

    @Override
    public void get(HttpServerRequest httpServerRequest) {
        String authorization = httpServerRequest.headers().get("authorization");
        String cookie = httpServerRequest.headers().get("cookie");
        System.out.println("-----------------------------------");
        Map<String, String> userInfoMap = extractUserInfo(authorization);
        String cookieID = parseCookieString(cookie).get("id");
        
        System.out.println("get-infoMap>" + userInfoMap.toString());
        System.out.println("get-cookie>"+cookieID);
        
        if(userInfoMap.size() == 0 && cookieID == null){
            System.out.println("userInfoMap.size() == 0 && cookieID == null  >> TRUE");
            handleFail(httpServerRequest);
        }else{
            System.out.println("userInfoMap.size() == 0 && cookieID == null  >> FALSE");
            if(cookieID == null){
                System.out.println("cookieID == null >> TRUE");
                if(checkUserAndPassword(userInfoMap)){
                    UUID idOne = UUID.randomUUID();
                    System.out.println("get-Passed the Check > UUID >>" + idOne );
                    httpServerRequest.response().putHeader("Set-Cookie", "id="+idOne+"; path=/");
                    sessionsSet.put(idOne.toString(),"");
                    handleSuccess(httpServerRequest);
                }else{
                    System.out.println("if(checkUserAndPassword(userInfoMap)){  FALSE");
                    handleFail(httpServerRequest);
                }
            }else{
                if(validateCookie(cookieID, httpServerRequest)){
                    System.out.println("if(validateCookie(cookieID, httpServerRequest)){  TRUE");
                    handleSuccess(httpServerRequest);
                    
                }else{
                    System.out.println("if(validateCookie(cookieID, httpServerRequest)){  FALSE");
                    handleFail(httpServerRequest);
                }
            }
        }
    }
    
    private boolean validateCookie(String cookieID, HttpServerRequest httpServerRequest){
        System.out.println("validateCookie-cookieID>" + cookieID);
        if(checkCookieSession(cookieID)){
            return true;
        }else{
            return false;
        }
    }
    private boolean checkCookieSession(String cookieAuth){
        System.out.println("checkCookieSession-entering>" + cookieAuth);
        System.out.println("checkCookieSession-sessionsSet>" + sessionsSet);
        if(sessionsSet == null)
           return false;
        System.out.println("checkCookieSession-cookieAuth>" + cookieAuth);
        
        boolean idInSessionStore = sessionsSet.containsKey(cookieAuth);
        System.out.println("checkCookieSession-cookieAuthContains>" + sessionsSet.containsKey(cookieAuth));
        
        
        if(idInSessionStore){
            return true;
        }else{
            return false;
        }
    }
    
    private Map<String, String> extractUserInfo(String authorization){
        Map<String, String> infoMap = new HashMap<String, String>();
        if(authorization == null)
            return infoMap;
        
        try{
            String[] authorizationSplit = authorization.trim().split(" ");
            String decode1= new String(DatatypeConverter.parseBase64Binary(authorizationSplit[1]));
            System.out.println("checkUserAndPassword-decode1>"+decode1);
            String[] userPasswordSplit = decode1.split(":");
            if(userPasswordSplit.length == 2){
                infoMap.put("username", userPasswordSplit[0]);
                infoMap.put("password", userPasswordSplit[1]);
            }
        }catch(Exception e){
            throw new RuntimeException("Error Extracting User Info");
        }
        return infoMap;
    }
    
    
    private boolean checkUserAndPassword(Map<String, String> infoUserMap){
        System.out.println("checkUserAndPassword-infoMap>"+infoUserMap.toString());
        
        String inputUsername = "manny";
        String inputPassword = "pass";
        
        String userUserName = infoUserMap.get("username").trim();
        String userPassword = infoUserMap.get("password");
        
        boolean sameUsername = inputUsername.equals(userUserName);
        boolean samePassword = inputPassword.equals(userPassword);
 
        System.out.println("sameUsername && samePassword >>" + (sameUsername && samePassword));
        if( sameUsername && samePassword ){
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
        httpServerRequest.response().putHeader("Set-Cookie", "id=INVALID; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT");
        httpServerRequest.response().putHeader("WWW-Authenticate", "Basic realm=\"Authentication required\"");
        httpServerRequest.response().end("Not Authorized");
    }
}
