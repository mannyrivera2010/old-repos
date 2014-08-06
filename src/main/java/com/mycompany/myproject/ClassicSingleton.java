package com.mycompany.myproject;

import java.io.File;
import java.io.IOException;

import org.vertx.java.core.Vertx;
import org.vertx.java.core.file.impl.PathAdjuster;
import org.vertx.java.core.impl.VertxInternal;

import freemarker.template.Configuration;

public class ClassicSingleton {
    private static ClassicSingleton instance = null;
    private Configuration cfg;
    
    public Configuration getCfg() {
        return cfg;
    }
    protected ClassicSingleton(Vertx vertx) {
        
        this.cfg = new Configuration();
        try {
            cfg.setDirectoryForTemplateLoading(new File(PathAdjuster.adjust((VertxInternal) vertx,"")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static ClassicSingleton getInstance(Vertx vertx) {
       if(instance == null) {
          instance = new ClassicSingleton(vertx);
       }
       return instance;
    }
 }