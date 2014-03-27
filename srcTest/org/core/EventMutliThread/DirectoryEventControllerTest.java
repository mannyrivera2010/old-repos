package org.core.EventMutliThread;

import org.enums.DatabaseEngineEnum;

// TODO: Auto-generated Javadoc
/**
 * The Class DirectoryEventControllerTest.
 */
public class DirectoryEventControllerTest {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		DirectoryEventController DeC = new DirectoryEventController(DatabaseEngineEnum.SQLITE4WRAPPER);
		DeC.runActions();
	}	
	
}
