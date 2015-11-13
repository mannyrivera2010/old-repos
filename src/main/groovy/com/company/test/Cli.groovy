package com.company.test;

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Test Cli Class
 * @author Rivera
 *
 */
public class Cli {
    private static final Logger logger = LoggerFactory.getLogger(Cli.class)
    
    public static void main(String[] args) {
        logger.info("Hello World")
        println args
        
        
    }
    
}
