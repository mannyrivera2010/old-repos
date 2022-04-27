package org.list;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.thoughtworks.xstream.XStream;


public class DirListXML implements DirListI{

	private TreeSet<String> fileDescSet= new TreeSet<String>();
	
	
	public TreeSet<String> getFileDescSet() {
		return fileDescSet;
	}


	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		DirListXML DirListObj= new DirListXML();
		DirListObj.loadDir("C:\\Users\\manny01\\Desktop\\Test");
		
		//System.out.println(DirListObj.getXML());
		
		DirListObj.saveXML("Test 1-19-2013 After.xml");
		DirListObj.loadXML("Test 1-19-2013 After.xml");
		    
	}


	public void loadDir(String strDirectory) throws IOException{
		Collection<File> found = FileUtils.listFiles(new File(strDirectory),
				TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for (File f : found) {
			//System.out.println("Found file: " + f.toString());
			fileDescSet.add(f.toString());
		}
	}


	/**
	 * Load xml.
	 *
	 * @param FileName the file name
	 */
	public void loadXML(String FileName){
		
		XStream xstream = new XStream();

		this.fileDescSet.clear();
		
		File file = new File(FileName);
		String lines = null;
		try {
			lines = FileUtils.readFileToString(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TreeSet<String> tempTable=(TreeSet<String>)xstream.fromXML(lines.toString());

		this.fileDescSet=tempTable;
	}
	
	/**
	 * Gets the xml.
	 *
	 * @return the xml
	 */
	public String getXML(){		
		XStream xstream = new XStream(); 
		String xml = xstream.toXML(this.fileDescSet);
		return xml;
	}
	
	/**
	 * Save xml.
	 *
	 * @param FileName the file name
	 */
	public void saveXML(String FileName){
		File file = new File(FileName);
		try {
			FileUtils.writeStringToFile(file, this.getXML());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void loadTXT(String string) {
		// TODO Auto-generated method stub
		
	}


	public void saveTXT(String string) {
		// TODO Auto-generated method stub
		
	}

	

}
