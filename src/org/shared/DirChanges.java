package org.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

public class DirChanges {
	private HashSet<String> PreviousFileNames = new HashSet<String>();
	private HashSet<String> CurrentFileNames  = new HashSet<String>();
	
	
	public void setPreviousFileNames(HashSet<String> previousFileNames) {
		PreviousFileNames = previousFileNames;
	}
	
	public void setPreviousFileNames(ArrayList<String> previousFileNames) {
		PreviousFileNames = new HashSet<String>(previousFileNames);
	}

	public void setCurrentFileNames(HashSet<String> currentFileNames) {
		CurrentFileNames = currentFileNames;
	}
	
	public void setCurrentFileNames(ArrayList<String> currentFileNames) {
		
		CurrentFileNames = new HashSet<String>(currentFileNames);
		//System.err.println(CurrentFileNames.size());
	}
	
	public void InsertPrevious(String FileName){
		PreviousFileNames.add(FileName);
	}
	
	public void InsertCurrent(String FileName){
		CurrentFileNames.add(FileName);
	}
	
	
	public void ClearCurrent(){
		CurrentFileNames.clear();

	}
	
	@SuppressWarnings("unchecked")
	public void CurrentToPrevious(){
		this.PreviousFileNames=(HashSet<String>) this.CurrentFileNames.clone();
		this.ClearCurrent();
	}
	
	public ArrayList<String> GetRemovedFiles() {
		ArrayList<String> temp = new ArrayList<String>();

		// Loops Check for Added Files
		Iterator<String> itr=PreviousFileNames.iterator();
		
		while(itr.hasNext()){
		    String c=itr.next();
		    if(this.CurrentFileNames.contains(c)){
		    	
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
		Iterator<String> itr=CurrentFileNames.iterator();
		
		while(itr.hasNext()){
		    String c=itr.next();
		    if(this.PreviousFileNames.contains(c)){
		    	
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
		
		ArrayList<String> ALRemovedFiles= this.GetRemovedFiles();
		
		for(int i=0;i<ALRemovedFiles.size();i++){
			Output.append("Removed:"+ALRemovedFiles.get(i)+"\n");
		}//MainLoop
		
		ArrayList<String> ALAddedFile= this.GetAddedFiles();
		
		for(int i=0;i<ALAddedFile.size();i++){
			Output.append("Added:"+ALAddedFile.get(i)+"\n");
		}//MainLoop
		
		return Output.toString();
	}



}
