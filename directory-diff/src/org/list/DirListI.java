package org.list;

import java.io.IOException;
import java.util.TreeSet;


public interface DirListI{
	public void loadDir(String inputDirectory) throws IOException;
	
	public void loadTXT(String string);
	public void saveTXT(String string);
	
	/**
	 * Load xml.
	 *
	 * @param FileName the file name
	 */
	public void loadXML(String FileName);
	
	/**
	 * Gets the xml.
	 *
	 * @return the xml
	 */
	public String getXML();
	
	/**
	 * Save xml.
	 *
	 * @param FileName the file name
	 */
	public void saveXML(String FileName);

	public TreeSet<String> getFileDescSet();
	
}
