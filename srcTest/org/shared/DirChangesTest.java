package org.shared;

import org.shared.DirChanges;

// TODO: Auto-generated Javadoc
/**
 * The Class DirChangesTest.
 */
public class DirChangesTest {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		DirChanges NewTest=new DirChanges(); //Create new class for Directory Comparison
	
		//"WorkingDir;;;FileName;;;CheckSum;;;GrantID"
		NewTest.insertPrevious("C:/","file2.txt","AED","1");
		NewTest.insertPrevious("C:/","file3.txt","AEA","1");

		NewTest.insertCurrent("C:/","file6.txt","AED","1");
		NewTest.insertCurrent("C:/","file9.txt","AED","1");
		NewTest.insertCurrent("C:/","file8.txt","AED","2");
		NewTest.insertCurrent("C:/","file1.txt","AED","2");

		/*
		 * It compares the file structure 
		 */
		System.out.println(NewTest.compareAndReportStringTable());
		
		NewTest.currentToPrevious();	
		NewTest.insertPrevious("dor","fil.txt","AED","IDfil.txt");
		NewTest.insertCurrent("dor","file9.txt","AED","IDfile9.txt");	
		System.out.println(NewTest.compareAndReportString());
		System.out.println(NewTest.compareAndReportStringTable());
	}

}
