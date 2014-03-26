package org.ComparisonTwoStructures;

import java.util.ArrayList;
import java.util.Collections;

public class DirChangesV1 {
	private ArrayList<String> PreviousFileNames = new ArrayList<String>();
	private ArrayList<String> CurrentFileNames  = new ArrayList<String>();

	public void InsertPrevious(String FileName){
		PreviousFileNames.add(FileName);
	}
	
	public void InsertCurrent(String FileName){
		CurrentFileNames.add(FileName);
	}
	
	public void CurrentToPrevious(){
		PreviousFileNames.clear();
		
		
		for(int intList2=0;intList2<=CurrentFileNames.size()-1;intList2++){
			PreviousFileNames.add(CurrentFileNames.get(intList2));
		}
		
		
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
		Collections.sort(PreviousFileNames);
		Collections.sort(CurrentFileNames);
		ArrayList<String> temp = new ArrayList<String>();

		// Loops Check for Added Files
		for (int intList2 = 0; intList2 <= CurrentFileNames.size() - 1; intList2++) {
			boolean FileExist = false;

			String StrList2 = CurrentFileNames.get(intList2).toString();

			for (int intList1 = 0; intList1 <= PreviousFileNames.size() - 1; intList1++) {
				String StrList1 = PreviousFileNames.get(intList1).toString();
				FileExist = StrList2.equals(StrList1);

				// System.out.println(StrList2+"-"+StrList1+"="+FileExist);

				if (FileExist == true) {
					break;
				}
			}// InnerLoop

			if (FileExist == false) {
				temp.add(StrList2);
			}
		}// MainLoop
		return temp;
	}
	/**
	 * Used to get Removed files
	 * @return
	 */
	public ArrayList<String> GetRemovedFiles() {
		Collections.sort(PreviousFileNames);
		Collections.sort(CurrentFileNames);
		ArrayList<String> temp = new ArrayList<String>();

		for (int intList1 = 0; intList1 <= PreviousFileNames.size() - 1; intList1++) {
			boolean FileExist = false;

			String StrList1 = PreviousFileNames.get(intList1).toString();

			for (int intList2 = 0; intList2 <= CurrentFileNames.size() - 1; intList2++) {
				String StrList2 = CurrentFileNames.get(intList2).toString();
				FileExist = StrList1.equals(StrList2);

				// System.out.println(StrList1+"-"+StrList2+"="+FileExist);

				if (FileExist == true) {
					break;
				}
			}// InnerLoop

			if (FileExist == false) {
				temp.add(StrList1.trim());
			}

		}// MainLoop

		return temp;
	}
	
	/**
	 * Used to Compare and Report
	 * @return
	 */
	public String CompareAndReport(){

		String Output="";
		
		ArrayList<String> ALRemovedFiles= this.GetRemovedFiles();
		
		for(int i=0;i<ALRemovedFiles.size();i++){
			Output+="Removed:"+ALRemovedFiles.get(i)+"\n";
		}//MainLoop
		
		ArrayList<String> ALAddedFile= this.GetAddedFiles();
		
		for(int i=0;i<ALAddedFile.size();i++){
			Output+="Added:"+ALAddedFile.get(i)+"\n";
		}//MainLoop
		
		return Output;
	}



}
