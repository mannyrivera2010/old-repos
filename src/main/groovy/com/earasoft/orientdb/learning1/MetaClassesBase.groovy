package com.earasoft.orientdb.learning1

import groovy.json.*

import java.nio.charset.Charset
import java.util.concurrent.locks.ReentrantLock

import org.slf4j.Logger
import org.slf4j.LoggerFactory


/**
 * Groovy Metaclasses
 * @author riverema
 *
 */

class MetaClassesBase {
    private static final Logger logger = LoggerFactory.getLogger(MetaClassesBase.class)
    
    static main(args){
        String defaultCharacterEncoding = System.getProperty("file.encoding");
        System.out.println("defaultCharacterEncoding by property: " + defaultCharacterEncoding);
        System.out.println("defaultCharacterEncoding by charSet: " + Charset.defaultCharset());
        
        
    }
    
    public static void load() {
		//Maps
		Map.metaClass.toJsonString = { new JsonBuilder(delegate).toString() }
		Map.metaClass.toJsonBuilder = { new JsonBuilder(delegate) }
		Map.metaClass.toJsonPrettyString = { return JsonOutput.prettyPrint(new JsonBuilder(delegate).toString()) }
		Map.metaClass.checkRequiredKeys = { List keys ->  GeneralUtils.checkMapRequiredKeys(delegate, keys) ; return delegate}
        
		//List
		List.metaClass.toJsonString = { new JsonBuilder(delegate).toString() }
		List.metaClass.toJsonPrettyString = { return JsonOutput.prettyPrint(new JsonBuilder(delegate).toString()) }
		List.metaClass.normalizeToUpper = { return delegate.collect{ record -> record == null ? null: record.toUpperCase().trim()}.findAll{it != null} }

		//Strings
		String.metaClass.toFile = { new File(delegate) }
		String.metaClass.toFileOutputStream = { new FileOutputStream(delegate) }
        //String.metaClass.toFileOutputStream = { new FileOutputStream(delegate) }
		
		String.metaClass.jsonToObject = {
			if (delegate.substring(0, 1).equals("\"")) {
				return new JsonSlurper().parseText(delegate.substring(1, delegate.size() - 1))
			} else {
				return new JsonSlurper().parseText(delegate)
			}
		}

		String.metaClass.checkFileNotExistException = { String exceptionDesc ->
			if(delegate.toFile().exists() == false)
				throw new Exception(exceptionDesc)
		}
        

		//Locks
		ReentrantLock.metaClass.withLock = {critical->
			lock()
			try{critical()}
			finally{unlock()}
		}
		//logger.info("MetaClassesBase Loaded")
    }

}
