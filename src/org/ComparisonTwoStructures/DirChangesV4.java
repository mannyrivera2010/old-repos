package org.ComparisonTwoStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class DirChangesV4 {
	private HashSet<String> PreviousFileNames = new HashSet<String>();
	private HashSet<String> CurrentFileNames  = new HashSet<String>();

	public void InsertPrevious(String FileName){
		PreviousFileNames.add(FileName);
	}
	
	public void InsertCurrent(String FileName){
		CurrentFileNames.add(FileName);
	}
	
	
	public void ClearCurrent(){
		CurrentFileNames.clear();

	}
	
	public static void main(String[] args) {

		DirChangesV1 NewTest=new DirChangesV1();
		
		NewTest.InsertPrevious("file1.txt");
		NewTest.InsertPrevious("file2.txt");
		NewTest.InsertPrevious("file3.txt");

		NewTest.InsertCurrent("file6.txt");
		NewTest.InsertCurrent("file9.txt");
		NewTest.InsertCurrent("file8.txt");
		
		NewTest.InsertCurrent("file1.txt");

		System.out.println(NewTest.CompareAndReport());
		
		NewTest.CurrentToPrevious();
		
		NewTest.InsertCurrent("file41.txt");
		
		System.out.println(NewTest.CompareAndReport());
	}
	

	public ArrayList<String> GetAddedFiles() {
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
	public ArrayList<String> GetRemovedFiles() {
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
