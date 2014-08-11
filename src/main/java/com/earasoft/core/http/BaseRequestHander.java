package com.earasoft.core.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Container;

import com.earasoft.site1.service.SingletonService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class BaseRequestHander implements Handler<HttpServerRequest>, IHttpServerRequestVerbs {

    protected Vertx vertx;
	protected Container container;
    protected Handler<HttpServerRequest> next;
    
    public BaseRequestHander(Container container, Vertx vertx){
        this.container = container;
    	this.vertx = vertx;
    }
       
    public BaseRequestHander(BaseRequestHander next) {
        this.next = next;
    }

	public BaseRequestHander(Container container, Vertx vertx,
			Handler<HttpServerRequest> next) {
        this.container = container;
    	this.vertx = vertx;
    	this.next = next;
	}

	public Vertx getVertx() {
		return vertx;
	}

	public Container getContainer() {
		return container;
	}

    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        try {
            switch (httpServerRequest.method()) {
                case "GET":
                  get(httpServerRequest);
                  break;
                case "PUT":
                  put(httpServerRequest);
                  break;
                case "POST":
                  post(httpServerRequest);
                  break;
                case "DELETE":
                  delete(httpServerRequest);
                  break;
                case "OPTIONS":
                  options(httpServerRequest);
                  break;
                case "HEAD":
                  head(httpServerRequest);
                  break;
                case "TRACE":
                  trace(httpServerRequest);
                  break;
                case "PATCH":
                  patch(httpServerRequest);
                  break;
                case "CONNECT":
                  connect(httpServerRequest);
                  break;
              }
            } catch (Exception e) {
                displayExceptions(httpServerRequest, e);
            }
    }
    
    public void unsupportedOperationException(HttpServerRequest httpServerRequest){
        throw new UnsupportedOperationException("Verb Error: " + httpServerRequest.method() + " - " + httpServerRequest.path());
    }
    
    public void get(HttpServerRequest httpServerRequest) throws Exception{
        unsupportedOperationException(httpServerRequest);
    }

    @Override
    public void post(HttpServerRequest httpServerRequest) throws Exception{
        unsupportedOperationException(httpServerRequest);
    }

    @Override
    public void delete(HttpServerRequest httpServerRequest) throws Exception{
        unsupportedOperationException(httpServerRequest);
    }

    @Override
    public void head(HttpServerRequest httpServerRequest) throws Exception{
        unsupportedOperationException(httpServerRequest);
    }

    @Override
    public void options(HttpServerRequest httpServerRequest) throws Exception{
        unsupportedOperationException(httpServerRequest);
    }

    @Override
    public void patch(HttpServerRequest httpServerRequest) throws Exception{
        unsupportedOperationException(httpServerRequest);
    }

    @Override
    public void put(HttpServerRequest httpServerRequest) throws Exception{
        unsupportedOperationException(httpServerRequest);
    }

    @Override
    public void trace(HttpServerRequest httpServerRequest) throws Exception{
        unsupportedOperationException(httpServerRequest);
    }

    @Override
    public void connect(HttpServerRequest httpServerRequest) throws Exception{
        unsupportedOperationException(httpServerRequest);
    }
    
    public String render(String fileName, Map<String, Object> data ) throws IOException, TemplateException{
        ByteArrayOutputStream baStream = new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(baStream);
        Configuration cfg = SingletonService.getInstance(vertx).getCfg();
        Template template;
        template = cfg.getTemplate(fileName);
        template.process(data, out);
        out.flush();
        out.close();
        return baStream.toString();
    }
    
    public void displayExceptions(HttpServerRequest httpServerRequest, Exception e){
        //HttpServerRequest httpServerRequest = (HttpServerRequest) this;
        
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        
        boolean debug = true;
        
        if(debug){
            httpServerRequest.response().setStatusCode(500);
            httpServerRequest.response().end(exceptionAsString);
        }else{
            httpServerRequest.response().setStatusCode(500);
            httpServerRequest.response().end("Internal Error");
        }
    }

    public Map<String, String> parseCookieString(String cookies) {
        Map<String, String> output = new HashMap<String, String>();
        Pattern cookiePattern = Pattern.compile("([^=]+)=([^\\;]*);?\\s?");
        if(cookies == null)
            return output;
        
        try{
            Matcher matcher = cookiePattern.matcher(cookies);
            while (matcher.find()) {
                int groupCount = matcher.groupCount();
                //System.out.println("matched: " + matcher.group(0));
                for (int groupIndex = 0; groupIndex <= groupCount; ++groupIndex) {
                    //System.out.println("group[" + groupIndex + "]=" + matcher.group(groupIndex));
                }
                String cookieKey = matcher.group(1);
                String cookieValue = matcher.group(2);
                output.put(cookieKey, cookieValue);
            }
        }catch(Exception e){
            throw new RuntimeException("Error Parsing Cookie");
        }
        return output;
    }
    
}
