package org.testing.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class Log4J.
 */
public class Log4J {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Log4J.class);

		logger.error("Hello World0");
		logger.debug("Hello World1");
	}

}


