package com.earasoft.core.http;

import org.vertx.java.core.http.HttpServerRequest;

public interface IHttpServerRequestVerbs {
    public void get(HttpServerRequest  httpServerRequest) throws Exception;
    public void post(HttpServerRequest  httpServerRequest) throws Exception;
    public void delete(HttpServerRequest  httpServerRequest) throws Exception;
    public void head(HttpServerRequest  httpServerRequest) throws Exception;
    public void options(HttpServerRequest  httpServerRequest) throws Exception;
    public void patch(HttpServerRequest  httpServerRequest) throws Exception;
    public void put(HttpServerRequest  httpServerRequest) throws Exception;
    public void trace(HttpServerRequest  httpServerRequest) throws Exception;
    public void connect(HttpServerRequest  httpServerRequest) throws Exception;
}
