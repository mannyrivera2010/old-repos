package org.logging;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

import org.shared.Util;
 
// TODO: Auto-generated Javadoc
/**
 * The Class LogFileLimit.
 */
public class LogFileLimit {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(LogFileLimit.class.getName());
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		LogFileLimit logg = new LogFileLimit();
		
		logg.warning("log","TEST");
	
		//AddFile(LogFileLimit.class.getName());
	}
	

	
	/**
	 * Warning.
	 *
	 * @param strSubject the str subject
	 * @param strMsg the str msg
	 */
	public void warning(String strSubject,String strMsg){
		try {
			FileHandler handler;
			Util Utils=new Util();
			Utils.folderExistAndMake("log");
			handler = new FileHandler("log//"+strSubject+".log",true);
			handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
            logger.setUseParentHandlers(true); 
            
            logger.warning(strMsg);
            
            handler.flush();
            handler.close();
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}//end
	
	
	/**
	 * Severe.
	 *
	 * @param strSubject the str subject
	 * @param strMsg the str msg
	 */
	public void severe(String strSubject,String strMsg){
		try {
			FileHandler handler;
			Util Utils=new Util();
			Utils.folderExistAndMake("log");
			handler = new FileHandler("log//"+strSubject+".log",true);
			handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
            logger.setUseParentHandlers(true); 
            
            logger.severe(strMsg);
            
            handler.flush();
            handler.close();
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}//end
	
	
	
}