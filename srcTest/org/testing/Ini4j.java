package org.testing;
/**
 * @author Emanuel Rivera
 */

import java.io.File;
import java.io.IOException;
import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.ini4j.InvalidFileFormatException;


// TODO: Auto-generated Javadoc
/**
 * The Class Ini4j.
 */
public class Ini4j {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws InvalidFileFormatException the invalid file format exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws InvalidFileFormatException, IOException {
		
		Ini File=new Ini(new File("./Settings.ini"));
		
		IniPreferences prefs = new IniPreferences(File);
		String user_auth=prefs.node("Email").get("user_auth", null).trim();
        System.out.println(user_auth);
        
        prefs.node("test").put("hello1", "work");
        
        System.out.println(File.get("Email", "user_auth"));
        
        String test=prefs.node("test").get("hello", null).trim();
        File.store();
        System.out.println(test);
	}
	
	
	
	
}//end class
