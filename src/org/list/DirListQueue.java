package org.list;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;

import com.thoughtworks.xstream.XStream;


public class DirListQueue implements DirListI{

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
		
		
		DirListQueue DirListObj= new DirListQueue();
		DirListObj.loadDir("Test");
		
		//System.out.println(DirListObj.getXML());
		
		DirListObj.saveXML("Test 1-19-2013 After.xml");
		DirListObj.loadXML("Test 1-19-2013 After.xml");
		    
	}


	public void loadDir(String strDirectory) throws IOException{
		Queue<String> q = new LinkedList<String>();

		q.add(new File(strDirectory).toString());

		while(!q.isEmpty()){	
			try{
				File dir = new File(q.poll());
				File listDir[] = dir.listFiles();
				for (int i = 0; i < listDir.length; i++) {

					if (listDir[i].isDirectory()&&(!FileUtils.isSymlink(listDir[i]))) {
						System.out.println("Dir:\t"+listDir[i].getAbsolutePath());
						q.add(listDir[i].getAbsolutePath());       
					}

					if (listDir[i].isFile()) {
						//System.out.println("File:\t"+listDir[i].getAbsolutePath());
						this.fileDescSet.add(listDir[i].getAbsolutePath());
					}       

				}   
			}catch(Exception e){
				//NOthing
				System.out.println("ERROR under directory");
				//q.poll();
			}

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
		this.fileDescSet.clear();
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(string)));
			String line;
			while ((line = br.readLine()) != null) {
			   // process the line.
				if(line.trim().length()!=0){
					this.fileDescSet.add(line.trim());	
				}
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void saveTXT(String string) {
		// TODO Auto-generated method stub
		try {
		       FileWriter fw = new FileWriter(new File(string));
		       BufferedWriter bw = new BufferedWriter(fw);
		       for (String FilePath : this.fileDescSet) {
		       bw.write(FilePath + "\n");
		       }
		       bw.flush();
		       bw.close();
		   } catch (IOException e) {
		       e.printStackTrace();
		   }
	}
	

}
