package org.table;
/**
 * @author Emanuel Rivera
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javolution.text.TextBuilder;
import javolution.util.FastTable;

public class StringTable {
	/*
	 * Private Variables
	 * fTRows=The Actual Table
	 * columnSize
	 * strDelimiter= The Actual Delimiter
	 */
	private FastTable<String[]> fTRows = new FastTable<String[]>();
	private String[] arrColumnHeader;
	private int columnSize;
	private String strDelimiter;



	/*
	 * The Constructors
	 */
	public StringTable() {
		super();
	}//end Constructor
	
	public StringTable(String strConRaw, String strDelimiter) {
		super();
		this.strDelimiter=strDelimiter;
		ContructHeader(strConRaw);		
	}//end Constructor
	
	public StringTable(String strHeaderRaw, String strDelimiter, String StrFileName) {
		super();
		this.strDelimiter=strDelimiter;
		ContructHeader(strHeaderRaw);
		insertfiletoFastTableCL(StrFileName);
	}//end Constructor
	
	
	/*
	 * Supporting Methods for Constructors
	 */
	
	private void ContructHeader(String strConRaw){
		int CurOccurence=this.numberOfStringOccurence(strConRaw, strDelimiter);
		//System.out.println("Occurence>"+CurOccurence);
		
		if(CurOccurence==-2){
			this.columnSize=1;
		}else{
			this.columnSize=CurOccurence;	
		}
		
		//System.out.println(CurOccurence+"-+-+-"+ColumnNumber);
		arrColumnHeader= new String[this.columnSize];
		
		if(CurOccurence==-2){
			this.arrColumnHeader[0]=strConRaw.trim();
		}else{
			//System.out.print(strDelimiter);
			String[] results = strConRaw.split(strDelimiter); 
			
			
			if(arrColumnHeader.length!=results.length){
				System.err.println("insertStringColumn Error : ArrColumn.length!=results.length");
			}
			
			for (int i = 0; i < this.arrColumnHeader.length; i++) {
				arrColumnHeader[i] = results[i];
				//System.out.println("Debug>Token:"+ArrColumnHeader[i]+""+results[i]);
			}
		}
	}//end ContructHeader
	
	
	/*
	 * File Support
	 */
	public void insertfiletoFastTableCL(String StrFile){
		try {// Main
			fTRows.clear();
			String StrFILENAME = StrFile;
			FileInputStream fstream = new FileInputStream(StrFILENAME);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {	
				if (strLine.trim().length()>=1){
					String CurrentLine=strLine.trim();
					//System.out.println("CurLine>>"+CurrentLine);
					///////////////////////
					insertStringEntry(CurrentLine);
					///////////////////////
				}
			}
			in.close(); // Close the input stream
		} catch (Exception e) {// Catch exception if any
			System.err.println("insertfiletoFastTableCL Error: " + e.getMessage());
		}// End Main
	}//end filetoFastTable()
	
	
	/*
	 * Table Creation Method and Deletion 
	 */
	
	public void setArrColumnHeader(String[] arrColumnHeader, String StrDelimiter) {
		this.strDelimiter=StrDelimiter;
		this.columnSize=arrColumnHeader.length;
		this.arrColumnHeader = arrColumnHeader;
		this.fTRows.clear();
	}
	
	public void Remove1stArrLRows(){
		fTRows.remove(0);
	}
	
	public void clearArrLRows(){
		fTRows.clear();
	}//end ClearArrLRows
	
	
	
	/*
	 * Methods that Get Table Information
	 */	
	
	public String[] getTableColumnNames() {
		return arrColumnHeader;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public String getStrDelimiter() {
		return strDelimiter;
	}
	
	public FastTable<String[]> getTableRows() {//Get 
		return fTRows;
	}//end getArrLRows()
	
	public int getTableRowSize(){
		return fTRows.size();
	}//end getArrLRowsSizes
  	
	
	/*
	 * Methods for Table Output
	 */
	public String toString() {
		TextBuilder Output=new TextBuilder("");
		for(int i =0;i<columnSize;i++){
			if(columnSize==0){
				Output.append(arrColumnHeader[i]);
			}else{
				if(i>=columnSize-1){
					Output.append(arrColumnHeader[i]);
				}else{
					Output.append(arrColumnHeader[i]+strDelimiter);
				}	
			}
		}
		Output.append("\n"+toStringTableNoHeader());
		//Output.append("<--End-->";
		return Output.toString();
	}//end toString()
	
	public String toStringTableNoHeader() {
		TextBuilder Output=new TextBuilder("");
		
		for(int i=0;i<fTRows.size();i++){
			String[] temp=fTRows.get(i);
			for(int j =0;j<temp.length;j++){
				
				if(temp.length==0){
					Output.append(temp[j]);
				}else{
					if(j>=temp.length-1){
						Output.append(temp[j]);
					}else{
						Output.append(temp[j]+strDelimiter);
					}	
				}
	
			}
			Output.append("\n");
		}
		return Output.toString();
	}//end toString()
	
	
	/*
	 * Methods Used to Insert Data
	 */
	
	public void insertStringEntry(String strConRaw) {
		int intNumberOfStringOccurence=this.numberOfStringOccurence(strConRaw,strDelimiter);
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>"+intNumberOfStringOccurence+"\n\tstrConRaw="+strConRaw);
		
		if (intNumberOfStringOccurence == -2) {
			//System.err.println("Only one Column");
		} else {
			if (this.columnSize != (intNumberOfStringOccurence)) {
				System.err
						.println("insertStringColumn>Columns does not match number of columns\t"+
								this.columnSize+"\t"+intNumberOfStringOccurence);
				
				System.err.println(strConRaw);
				return;
			}
		}
		
		if(intNumberOfStringOccurence==-2){
			fTRows.add(new String[]{strConRaw});
		}else{
			String[] ArrColumn = new String[this.columnSize];

			String[] results = strConRaw.split(strDelimiter); 
			
			
			if(ArrColumn.length!=results.length){
				System.err.println("insertStringColumn Error : " +
						"ArrColumn.length!=results.length\t"+ArrColumn.length+"\t"+results.length);
			}
			
			for (int i = 0; i < this.columnSize; i++) {
				ArrColumn[i] = results[i].trim();
				//System.out.println("Debug>Token:"+ArrColumn[i]);
			}
			
			//StringTokenizer sTok = new StringTokenizer(strConRaw, strDelimiter);
			//for (int i = 0; i < this.ColumnNumber; i++) {
			//	ArrColumn[i] = sTok.nextToken().trim();
			//	System.out.println("Debug>Token:"+ArrColumn[i]);
			//}
			fTRows.add(ArrColumn);
		}
	}// end InsertStringColumn()
	
	public void insertStringArray(String[] inputArray) {
		if(this.columnSize!=inputArray.length){
			System.err.println("Columns does not match number of columns");
			return;
		}
		fTRows.add(inputArray);		
	}//end InsertStringColumn()
	
	
	/*
	 * Supporting Methods
	 */
	private int numberOfStringOccurence(String strInput, String strFind) {
		//System.out.println("numberOfStringOccurence(Debug)>"+strDelimiter);
		if(strDelimiter.length()<=0){
			return -2;
		}
		
		String[] results = strInput.split(strDelimiter); 
		int count = results.length;
		
		//System.out.println(Arrays.toString(results)+">"+count);
		
		//System.out.println("*****"+strInput.length()+"-"+strFind.length());
		
		if(strInput.length()>=1&&strFind.length()==0){
			return -2;
		}else if(count==0){
			return -1;
		}else if(strFind.length()==0){
			return -1;
		}else{
			return count;
		}
	}//NumberOfStringOccurence()
	

	private int getColumnID(String strColumnName){
		int Count=-1;
		for(int j =0;j<arrColumnHeader.length;j++){
			if(arrColumnHeader[j].equals(strColumnName)){
				Count=j;
				break;
			}
		}
		return Count;
	}//end strColumnName
	

	public String getColumnValueArray(int intArrayIndex,int intIndex){
		
		if ((intIndex>=0 && intIndex<fTRows.size())==false){
			System.err.println("ERROR-Out of Range");
			return "ERROR-Out of Range";
		}	
		
		return fTRows.get(intIndex)[intArrayIndex].trim();
	}
	
	public String getColumnValue(String strColumnName,int intIndex){
		int curColumnID=getColumnID(strColumnName);
		
		if(curColumnID==-1){
			System.err.println("ERROR-Column Name not found = "+strColumnName);
			return "ERROR-Column Name not found = "+strColumnName;
		}
		
		if ((intIndex>=0 && intIndex<fTRows.size())==false){
			System.err.println("ERROR-Out of Range");
			return "ERROR-Out of Range";
		}
		
		
		String TempString=fTRows.get(intIndex)[curColumnID].trim();
		
		
		if(TempString==null){
			TempString="";
		}else if(TempString.equals("null")){
			TempString="";
		}
		
		//System.out.println(TempString);
		return TempString;
	}//end strColumnName
	
	protected String[] get(int intIndex){
		if ((intIndex>=0 && intIndex<fTRows.size())==false){
			System.err.println("ERROR-Out of Range");
			return new String[]{"ERROR-Out of Range"};
		}				
		return fTRows.get(intIndex);
	}//end strColumnName
		
	
	/*
	 * Methods for Filtering
	 */
	public FastTable<String> FilterUniqueStringTable(String strColumn) {
		FastTable<String> ArrLTemp= new FastTable<String>();
		
		int curColumnID=getColumnID(strColumn);
		
		if(curColumnID==-1){
			System.err.println("ERROR-Column Name not found");
			ArrLTemp.add("ERROR-Column Name not found");
			return ArrLTemp;
		}
		
		
		for (int i = 0; i < getTableRows().size(); i++) {
			if (!ArrLTemp.contains(getTableRows().get(i)[curColumnID])) {
				ArrLTemp.add(getTableRows().get(i)[curColumnID]);
			}
		}
				
		return ArrLTemp;
	}
	
	
	public FastTable<String[]> IncludeFilterStringTable(String strColumn,String strFind){
		return IncludeFilterStringTable(strColumn,strFind,false);
	}//end sub
	
	public FastTable<String[]> IncludeFilterStringTable(String strColumn,String strFind,boolean blnSave){
		FastTable<String[]> ArrLTemp= new FastTable<String[]>();
		
		int curColumnID=getColumnID(strColumn);
		
		if(curColumnID==-1){
			System.err.println("ERROR-Column Name not found");
			ArrLTemp.add(new String[]{"ERROR-Column Name not found"});
			return ArrLTemp;
		}

		//System.err.println("ad");
		for (int i = 0; i < fTRows.size(); i++) {
			if (fTRows.get(i)[curColumnID].equalsIgnoreCase(strFind)) {
				// found
				// System.out.println(Arrays.toString(ArrLRows.get(i)));
				ArrLTemp.add(fTRows.get(i));
			} else {

			}
		}

		if (blnSave == true) {
			fTRows.clear();
			fTRows=ArrLTemp;
			
		}
		return ArrLTemp;
	}//end sub
	
	
	public FastTable<String[]> IncludeFilterTable(StringTable Table2,String Table1Column,String Table2Column,String strFind){
		return IncludeFilterTable(Table2,Table1Column,Table2Column,false);
	}//end sub
	
	public FastTable<String[]> IncludeFilterTable(StringTable Table2,String Table1Column,String Table2Column,boolean blnSave){
		FastTable<String[]> ArrLTemp= new FastTable<String[]>();
		
		int Table1curColumnID=this.getColumnID(Table1Column);
		int Table2curColumnID=Table2.getColumnID(Table2Column);
		
		if(Table1curColumnID==-1 || Table2curColumnID==-1){
			System.err.println("ERROR-Column Name not found");
			ArrLTemp.add(new String[]{"ERROR-Column Name not found"});
			return ArrLTemp;
		}
		
		//System.out.println("Table1:");
		for (int k = 0; k < fTRows.size(); k++) {// FilterArray
			String Table1ColumnValue=this.getColumnValue(Table1Column, k);
			//System.out.println(Table1ColumnValue);
			
			//System.out.println("\nTable2:");
			for (int j = 0; j < Table2.fTRows.size(); j++) {// FilterArray
				String Table2ColumnValue=Table2.getColumnValue(Table2Column, j);
				boolean blnMatch=Table1ColumnValue.equalsIgnoreCase(Table2ColumnValue);
				//System.out.println(Table1ColumnValue+"\t"+Table2ColumnValue+"\t="+blnMatch);
				
				if(blnMatch==true){
					ArrLTemp.add(fTRows.get(k));
					break;
				}
			}// end_Loop_>_FilterArray_FinishedGrants.txt
		}// end_Loop_>_FilterArray_FinishedGrants.txt

		if (blnSave == true) {
			fTRows.clear();
			fTRows=ArrLTemp;
		}
		
		return ArrLTemp;
	}//end sub
	
	public FastTable<String[]> IncludeFilterContainTable(StringTable Table2,String Table1Column,String Table2Column,String strFind){
		return IncludeFilterContainTable(Table2,Table1Column,Table2Column,false);
	}//end sub
	
	public FastTable<String[]> IncludeFilterContainTable(StringTable Table2,String Table1Column,String Table2Column,boolean blnSave){
		FastTable<String[]> ArrLTemp= new FastTable<String[]>();
		
		int Table1curColumnID=this.getColumnID(Table1Column);
		int Table2curColumnID=Table2.getColumnID(Table2Column);
		
		if(Table1curColumnID==-1 || Table2curColumnID==-1){
			System.err.println("ERROR-Column Name not found");
			ArrLTemp.add(new String[]{"ERROR-Column Name not found"});
			return ArrLTemp;
		}
		
		//System.out.println("Table1:");
		for (int k = 0; k < fTRows.size(); k++) {// FilterArray
			String Table1ColumnValue=this.getColumnValue(Table1Column, k);
			//System.out.println(Table1ColumnValue);
			
			//System.out.println("\nTable2:");
			for (int j = 0; j < Table2.fTRows.size(); j++) {// FilterArray
				String Table2ColumnValue=Table2.getColumnValue(Table2Column, j);
				boolean blnMatch=Table1ColumnValue.contains(Table2ColumnValue);
				//System.out.println(Table1ColumnValue+"\t"+Table2ColumnValue+"\t="+blnMatch);
				
				if(blnMatch==true){
					ArrLTemp.add(fTRows.get(k));
					break;
				}
			}// end_Loop_>_FilterArray_FinishedGrants.txt
		}// end_Loop_>_FilterArray_FinishedGrants.txt

		if (blnSave == true) {
			fTRows.clear();
			fTRows=ArrLTemp;
		}
		
		return ArrLTemp;
	}//end sub
	
	public FastTable<String[]> includeFilterContain(FastTable<String> FastTable1dstr,String Table1Column){
		return includeFilterContain(FastTable1dstr,Table1Column,false);
	}//end sub
	
	public FastTable<String[]> includeFilterContain(FastTable<String> FastTable1dstr,String Table1Column,boolean blnSave){
		FastTable<String[]> ArrLTemp= new FastTable<String[]>();
		
		int Table1curColumnID=this.getColumnID(Table1Column);
			
		if(Table1curColumnID==-1){
			System.err.println("ERROR-Column Name not found");
			ArrLTemp.add(new String[]{"ERROR-Column Name not found"});
			return ArrLTemp;
		}
		
		//System.out.println("Table1:");
		for (int k = 0; k < fTRows.size(); k++) {// FilterArray
			String Table1ColumnValue=this.getColumnValue(Table1Column, k);
			//System.out.println(Table1ColumnValue);
			
			//System.out.println("\nTable2:");
			for (int j = 0; j < FastTable1dstr.size(); j++) {// FilterArray
				String Table2ColumnValue=FastTable1dstr.get( j);
				boolean blnMatch=Table1ColumnValue.contains(Table2ColumnValue);
				//System.out.println(Table1ColumnValue+"\t"+Table2ColumnValue+"\t="+blnMatch);
				
				if(blnMatch==true){
					ArrLTemp.add(fTRows.get(k));
					break;
				}
			}// end_Loop_>_FilterArray_FinishedGrants.txt
		}// end_Loop_>_FilterArray_FinishedGrants.txt

		if (blnSave == true) {
			fTRows.clear();
			fTRows=ArrLTemp;
		}
		
		return ArrLTemp;
	}//end sub
	
	public FastTable<String[]> ExcludeFilterTable(StringTable Table2,String Table1Column,String Table2Column,String strFind){
		return ExcludeFilterTable(Table2,Table1Column,Table2Column,false);
	}//end sub
	
	public FastTable<String[]> ExcludeFilterTable(StringTable Table2,String Table1Column,String Table2Column,boolean blnSave){
		FastTable<String[]> ArrLTemp= new FastTable<String[]>();
		
		int Table1curColumnID=this.getColumnID(Table1Column);
		int Table2curColumnID=Table2.getColumnID(Table2Column);
		
		if(Table1curColumnID==-1 || Table2curColumnID==-1){
			System.err.println("ERROR-Column Name not found");
			ArrLTemp.add(new String[]{"ERROR-Column Name not found"});
			return ArrLTemp;
		}
		
		//System.out.println("Table1:");
		
		for (int k = 0; k < fTRows.size(); k++) {// FilterArray
			boolean GrantMatch = false;
			String Table1ColumnValue=this.getColumnValue(Table1Column, k);
			//System.out.println(Table1ColumnValue);
			
			//System.out.println("\nTable2:");
			for (int j = 0; j < Table2.fTRows.size(); j++) {// FilterArray
				String Table2ColumnValue=Table2.getColumnValue(Table2Column, j);
				boolean blnMatch=Table1ColumnValue.equalsIgnoreCase(Table2ColumnValue);
				//System.out.println(Table1ColumnValue+"\t"+Table2ColumnValue+"\t="+blnMatch);
				
				if(blnMatch==true){
					GrantMatch = true;
					break;
				}
				
			}// end_Loop_>_FilterArray_FinishedGrants.txt

			//System.err.println(Table1ColumnValue+">"+GrantMatch);
			if (GrantMatch == false){
				ArrLTemp.add(fTRows.get(k));
			}
		}// end_Loop_>_FilterArray_FinishedGrants.txt

		if (blnSave == true) {
			fTRows.clear();
			fTRows=ArrLTemp;
		}
		
		return ArrLTemp;
	}//end sub
	
	public FastTable<String[]> ExcludeFilterStringTable(String strColumn,String strFind){
		return ExcludeFilterStringTable(strColumn,strFind,false);
	}//end sub
	
	public FastTable<String[]> ExcludeFilterStringTable(String strColumn,String strFind, boolean blnSave){
		//Select first,last,age where age=age and age=18 
				FastTable<String[]> ArrLTemp= new FastTable<String[]>();
				
				int curColumnID=getColumnID(strColumn);
				
				if(curColumnID==-1){
					System.err.println("ERROR-Column Name not found");
					ArrLTemp.add(new String[]{"ERROR-Column Name not found"});
					return ArrLTemp;
				}
				
				for(int i = 0; i<fTRows.size();i++){
					if(fTRows.get(i)[curColumnID].equalsIgnoreCase(strFind)){
						//found
					}else{
						//System.out.println(Arrays.toString(ArrLRows.get(i)));
						ArrLTemp.add(fTRows.get(i));
					}
				}//end loop
				
				if (blnSave == true) {
					fTRows.clear();
					fTRows=ArrLTemp;
					
				}
				
				return ArrLTemp;
	}//end ExcludeFilterStringTable
	
	
	/*
	 * Methods for Quick Sorting
	 */
	public void qsort(String strColumnId) {
		int curColumnID=getColumnID(strColumnId);
		
		if(curColumnID==-1){
			System.err.println("ERROR-Column Name not found >Sort ="+strColumnId);
			return;
		}
		
		quicksort(fTRows, 0, fTRows.size() - 1,curColumnID);
	}//end qsort

	private void quicksort(FastTable<String[]> list, int p, int r,int id) {
		if (p < r) {
			int q = partition(list, p, r,id);
			if (q == r) {
				q--;
			}
			quicksort(list, p, q,id);
			quicksort(list, q + 1, r,id);
		}
	}//end quicksort

	private int partition(FastTable<String[]> list, int p, int r,int id) {
		String pivot = list.get(p)[id];
		int lo = p;
		int hi = r;

		while (true) {
			while (list.get(hi)[id].compareTo(pivot) >= 0 && lo < hi) {
				hi--;
			}
			while (list.get(lo)[id].compareTo(pivot) < 0 && lo < hi) {
				lo++;
			}
			if (lo < hi) {
				String[] T = list.get(lo);
				list.set(lo, list.get(hi));
				list.set(hi, T);
				
			} else
				return hi;
		}
	}//end partition
	
}
