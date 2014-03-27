package org.shared;

/**
 * @author Emanuel Rivera
 */

import java.io.File;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.logging.LogFileLimit;

// TODO: Auto-generated Javadoc
/**
 * The Class IniEngine.
 */
public class IniEngine {
	
	/** The logg. */
	private LogFileLimit logg = new LogFileLimit();

	/**
	 * Sets the ini string value.
	 *
	 * @param FileName the file name
	 * @param Heading the heading
	 * @param Field the field
	 * @param Value the value
	 */
	public void setIniStringValue(String FileName, String Heading,
			String Field, String Value) {
		Util UtilS = new Util();
		try {
			if (!UtilS.fileExist(FileName)) { // IF Not Exist Create new File
				UtilS.writeStringToFile("", FileName, false); // Empty File
			}

			Ini File = new Ini(new File(FileName));
			IniPreferences prefs = new IniPreferences(File);

			prefs.node(Heading).put(Field, Value);

			File.store();
		} catch (Exception e) {
			System.out.println("Ini Error");
			logg.severe("ini","Error Setting String Value");
			//e.printStackTrace();
			System.out.println("End Ini Error");
		}// End Timer Wait
	}

	/**
	 * Gets the ini int value.
	 *
	 * @param FileName the file name
	 * @param Heading the heading
	 * @param Field the field
	 * @return the ini int value
	 */
	public int getIniIntValue(String FileName, String Heading, String Field) {
		int intCurrent = 0;

		try {
			String Current = getIniStringValue(FileName, Heading, Field);
			intCurrent = Integer.parseInt(Current);
		} catch (Exception e) {
			System.out.println("Ini Error");
			logg.severe("ini","Error Getting Integer Value. Check for Filename: "
							+ FileName);
			//e.printStackTrace();
			System.out.println("End Ini Error");
		}// End Timer Wait

		return intCurrent;
	}// end

	/**
	 * Gets the ini string value.
	 *
	 * @param FileName the file name
	 * @param Heading the heading
	 * @param Field the field
	 * @return the ini string value
	 */
	public String getIniStringValue(String FileName, String Heading,
			String Field) {
		String Output = "";
		try {
			IniPreferences prefsa = new IniPreferences(new Ini(new File(
					FileName)));
			return prefsa.node(Heading).get(Field, null).trim();
		} catch (Exception e) {
			System.out.println("Ini Error");
			logg.severe("ini","Error Getting String Value. Check for Filename: "
							+ FileName);
			e.printStackTrace();
			System.out.println("End Ini Error");
		}// End Timer Wait
		return Output.trim();
	}// end

}
