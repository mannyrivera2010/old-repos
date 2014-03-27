package org.gui;

import java.io.File;
import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.shared.Util;

// TODO: Auto-generated Javadoc
/**
 * The Class PreLoad.
 */
public class PreLoad {

	/** The Util s. */
	private Util UtilS = new Util();
	
	/**
	 * Check for settings.
	 */
	public void CheckForSettings(){
		try {
			if (!UtilS.fileExist("Settings.ini")) { // IF Not Exist Create new File
				UtilS.writeStringToFile("", "Settings.ini", false); //Empty File
		
			Ini File = new Ini(new File("Settings.ini"));
			IniPreferences prefs = new IniPreferences(File);

			prefs.node("Database").put("engine", "SQLITE4WRAPPER");
			prefs.node("Database").put("SQLite_path", "data\\data.db");
			prefs.node("Database").put("MySQL_url", "127.0.0.1:3306/grantprocess_test");
			prefs.node("Database").put("MySQL_user", "admin");
			prefs.node("Database").put("MySQL_password", "A07EE31E46CD4488E328CBBE9B69FDD0C6BBCCC9640FA9C9");
			
			prefs.node("Email").put("user_auth", "email@domain.com");
			prefs.node("Email").put("user_password", "A07EE31E46CD4488E328CBBE9B69FDD0C6BBCCC9640FA9C9");
			prefs.node("Email").put("host", "smtp.gmail.com");
			prefs.node("Email").put("port", "465");
			prefs.node("Email").put("send_mail_As", "mail@domain.com");
			prefs.node("Email").put("TLS_not_SSL", "no");
			prefs.node("Email").put("SendEmails", "on");
			
			prefs.node("History_LogOutput").put("GrantListPath", ".//GrantList.html");
			prefs.node("History_LogOutput").put("FolderFilesPath", ".//FolderFiles.html");
			prefs.node("History_LogOutput").put("IndividualFilesPath", ".//IndividualFiles.html");
			prefs.node("History_LogOutput").put("ProcessedGrantListPath ", ".//ProcessedGrantList.html");
			prefs.node("History_LogOutput").put("CompleteWebPath", ".//WebCom");
			
			prefs.node("Timer").put("GrantListHistory", "off");
			prefs.node("Timer").put("FolderFilesHistory", "off");
			prefs.node("Timer").put("IndividualFilesHistory", "off");
			prefs.node("Timer").put("ProcessedGrantListHistory ", "off");
			prefs.node("Timer").put("CompleteWebHistory", "off");
			prefs.node("Timer").put("Wait", "15");
			
			prefs.node("Grant_Counter").put("CurrentID", "0");

			File.store();
			
			}//end if
		} catch (Exception e) {
			System.out.println("Ini Error");
			e.printStackTrace();
			System.out.println("End Ini Error");
		}// End Timer Wait
	}
}
