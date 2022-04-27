package org.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;
import java.util.Iterator;

import org.list.DirListI;
import org.list.DirListXML;

public class DirChanges {
	private TreeSet<String> directory1FileNames = new TreeSet<String>();
	private TreeSet<String> directory2FileNames  = new TreeSet<String>();
	
	public DirChanges(){
		
	}
	
	public DirChanges(DirListI dirList1,DirListI dirList2){
		this.directory1FileNames=dirList1.getFileDescSet();
		this.directory2FileNames=dirList2.getFileDescSet();
	}
	
	public void setDirectory1(TreeSet<String> previousFileNames) {
		directory1FileNames = previousFileNames;
	}
	
	public void setDirectory1(ArrayList<String> previousFileNames) {
		directory1FileNames = new TreeSet<String>(previousFileNames);
	}

	public void setDirectory2(TreeSet<String> currentFileNames) {
		directory2FileNames = currentFileNames;
	}
	
	public void setDirectory2(ArrayList<String> currentFileNames) {
		
		directory2FileNames = new TreeSet<String>(currentFileNames);
		//System.err.println(CurrentFileNames.size());
	}
	
	public void insertFile1(String FileName){
		directory1FileNames.add(FileName);
	}
	
	public void insertFile2(String FileName){
		directory2FileNames.add(FileName);
	}

	public ArrayList<String> GetRemovedFiles() {
		ArrayList<String> temp = new ArrayList<String>();

		// Loops Check for Added Files
		Iterator<String> itr=directory1FileNames.iterator();
		
		while(itr.hasNext()){
		    String c=itr.next();
		    if(this.directory2FileNames.contains(c)){
		    	
		    }else{
		    	temp.add(c);
		    }
		    
		}
		Collections.sort(temp);
		return temp;
	}
	/**
	 * Used to get Removed files
	 * @return
	 */
	public ArrayList<String> GetAddedFiles() {
		ArrayList<String> temp = new ArrayList<String>();

		// Loops Check for Added Files
		Iterator<String> itr=directory2FileNames.iterator();
		
		while(itr.hasNext()){
		    String c=itr.next();
		    if(this.directory1FileNames.contains(c)){
		    	
		    }else{
		    	temp.add(c);
		    }
		    
		}
		Collections.sort(temp);
		return temp;
	}
	
	/**
	 * Used to Compare and Report
	 * @return
	 */
	public String CompareAndReport(){
		
		StringBuilder Output=new StringBuilder();
		Output.append("Comparison Report\n=============\n");
		ArrayList<String> ALRemovedFiles= this.GetRemovedFiles();
		
		Output.append("\nRemoved Files:\n-----------------------------------------\n");
		
		for(int i=0;i<ALRemovedFiles.size();i++){
			Output.append(""+ALRemovedFiles.get(i)+"\n");
		}//MainLoop
		Output.append("\nAdded Files:\n-----------------------------------------\n");
		ArrayList<String> ALAddedFile= this.GetAddedFiles();
		
		for(int i=0;i<ALAddedFile.size();i++){
			Output.append(""+ALAddedFile.get(i)+"\n");
		}//MainLoop
		
		return Output.toString();
	}



}
