package org.shared;
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
	 * Variables
	 */
	private FastTable<String[]> ArrLRows = new FastTable<String[]>();
	private String[] ArrColumnHeader;
	private int ColumnNumber;
	private String strDelimiter;

	/**
	 * Constructors
	 */
	
	public StringTable() {
		super();
	}//end
	
	
	public StringTable(String strConRaw, String strDelimiter) {
		super();
		this.strDelimiter=strDelimiter;
		ContructHeader(strConRaw);		
	}//end
	
	public StringTable(String strHeaderRaw, String strDelimiter, String StrFileName) {
		super();
		this.strDelimiter=strDelimiter;
		ContructHeader(strHeaderRaw);
		insertfiletoFastTableCL(StrFileName);
	}//end
	
	private void ContructHeader(String strConRaw){
		int CurOccurence=this.numberOfStringOccurence(strConRaw, strDelimiter);
		//System.out.println("Occurence>"+CurOccurence);
		
		if(CurOccurence==-2){
			this.ColumnNumber=1;
		}else{
			this.ColumnNumber=CurOccurence;	
		}
		
		//System.out.println(CurOccurence+"-+-+-"+ColumnNumber);
		ArrColumnHeader= new String[this.ColumnNumber];
		
		if(CurOccurence==-2){
			this.ArrColumnHeader[0]=strConRaw.trim();
		}else{
			//System.out.print(strDelimiter);
			String[] results = strConRaw.split(strDelimiter); 
			
			
			if(ArrColumnHeader.length!=results.length){
				System.err.println("insertStringColumn Error : ArrColumn.length!=results.length");
			}
			
			for (int i = 0; i < this.ArrColumnHeader.length; i++) {
				ArrColumnHeader[i] = results[i];
				//System.out.println("Debug>Token:"+ArrColumnHeader[i]+""+results[i]);
			}
		}
	}//end ContructHeader
	
	/**
	 * Used to obtain a string array of the table's header
	 * @return
	 */
	
	public String[] getArrColumnHeader() {
		return ArrColumnHeader;
	}

	/**
	 * Used to obtain the number of Columns
	 * @return
	 */
	
	public int getColumnNumber() {
		return ColumnNumber;
	}

	/**
	 * Used to obtain the Delimiter used by the table 
	 * @return
	 */
	
	public String getStrDelimiter() {
		return strDelimiter;
	}
	
	/**
	 * Used to get Table columns and rows without the header
	 * @return
	 */
	public FastTable<String[]> getArrLRows() {//Get 
		return ArrLRows;
	}//end getArrLRows()
	
	/**
	 * Used to get size of Table columns and rows without the header
	 * @return
	 */
	public int getArrLRowsSize(){
		return ArrLRows.size();
	}//end getArrLRowsSizes

	/** 
	 * Used to clear the Table columns and rows. it keeps the header the same
	 */
	public void clearArrLRows(){
		ArrLRows.clear();
	}//end ClearArrLRows
    

	public void setArrColumnHeader(String[] arrColumnHeader, String StrDelimiter) {
		this.strDelimiter=StrDelimiter;
		ColumnNumber=arrColumnHeader.length;
		ArrColumnHeader = arrColumnHeader;
		ArrLRows.clear();
	}

	public void Remove1stArrLRows(){
		ArrLRows.remove(0);
	}
	
	public void insertStringColumn(String strConRaw) {
		int intNumberOfStringOccurence=this.numberOfStringOccurence(strConRaw,strDelimiter);
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>"+intNumberOfStringOccurence+"\n\tstrConRaw="+strConRaw);
		
		if (intNumberOfStringOccurence == -2) {
			//System.err.println("Only one Column");
		} else {
			if (this.ColumnNumber != (intNumberOfStringOccurence)) {
				System.err
						.println("insertStringColumn>Columns does not match number of columns\t"+
								this.ColumnNumber+"\t"+intNumberOfStringOccurence);
				
				System.err.println(strConRaw);
				return;
			}
		}
		
		if(intNumberOfStringOccurence==-2){
			ArrLRows.add(new String[]{strConRaw});
		}else{
			String[] ArrColumn = new String[this.ColumnNumber];

			String[] results = strConRaw.split(strDelimiter); 
			
			
			if(ArrColumn.length!=results.length){
				System.err.println("insertStringColumn Error : " +
						"ArrColumn.length!=results.length\t"+ArrColumn.length+"\t"+results.length);
			}
			
			for (int i = 0; i < this.ColumnNumber; i++) {
				ArrColumn[i] = results[i].trim();
				//System.out.println("Debug>Token:"+ArrColumn[i]);
			}
			
			//StringTokenizer sTok = new StringTokenizer(strConRaw, strDelimiter);
			//for (int i = 0; i < this.ColumnNumber; i++) {
			//	ArrColumn[i] = sTok.nextToken().trim();
			//	System.out.println("Debug>Token:"+ArrColumn[i]);
			//}
			ArrLRows.add(ArrColumn);
		}
	}// end InsertStringColumn()
	
	public void insertStringArray(String[] inputArray) {
		if(this.ColumnNumber!=inputArray.length){
			System.err.println("Columns does not match number of columns. Table has "+this.ColumnNumber+", trying to insert with "+ inputArray.length);
			
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return;
		}
		ArrLRows.add(inputArray);		
	}//end InsertStringColumn()
	
	public void insertfiletoFastTableCL(String StrFile){
		try {// Main
			ArrLRows.clear();
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
					insertStringColumn(CurrentLine);
					///////////////////////
				}
			}
			in.close(); // Close the input stream
		} catch (Exception e) {// Catch exception if any
			System.err.println("insertfiletoFastTableCL Error: " + e.getMessage());
			e.printStackTrace();
		}// End Main
	}//end filetoFastTable()
	
	@Override
	public String toString() {
		TextBuilder Output=new TextBuilder("");
		for(int i =0;i<ColumnNumber;i++){
			if(ColumnNumber==0){
				Output.append(ArrColumnHeader[i]);
			}else{
				if(i>=ColumnNumber-1){
					Output.append(ArrColumnHeader[i]);
				}else{
					Output.append(ArrColumnHeader[i]+strDelimiter);
				}	
			}
		}
		Output.append("\n"+toStringBodyDel());
		//Output.append("<--End-->";
		return Output.toString();
	}//end toString()
	
	public String toStringBodyDel() {
		TextBuilder Output=new TextBuilder("");
		
		for(int i=0;i<ArrLRows.size();i++){
			String[] temp=ArrLRows.get(i);
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
		for(int j =0;j<ArrColumnHeader.length;j++){
			if(ArrColumnHeader[j].equals(strColumnName)){
				Count=j;
				break;
			}
		}
		return Count;
	}//end strColumnName
	

	public String getColumnValueArray(int intArrayIndex,int intIndex){
		
		if ((intIndex>=0 && intIndex<ArrLRows.size())==false){
			System.err.println("ERROR-Out of Range");
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "ERROR-Out of Range";
		}	
		
		return ArrLRows.get(intIndex)[intArrayIndex].trim();
	}
	
	public String getColumnValue(String strColumnName,int intIndex){
		int curColumnID=getColumnID(strColumnName);
		
		if(curColumnID==-1){
			System.err.println("ERROR-Column Name not found = "+strColumnName);
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "ERROR-Column Name not found = "+strColumnName;
		}
		
		if ((intIndex>=0 && intIndex<ArrLRows.size())==false){
			System.err.println("ERROR-Out of Range");
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "ERROR-Out of Range";
		}
		
		
		String TempString=ArrLRows.get(intIndex)[curColumnID].trim();
		
		
		if(TempString==null){
			TempString="";
		}else if(TempString.equals("null")){
			TempString="";
		}
		
		//System.out.println(TempString);
		
		
		
		return TempString;
	}//end
	
	public String[] get(int intIndex){
				
		if ((intIndex>=0 && intIndex<ArrLRows.size())==false){
			System.err.println("ERROR-Out of Range");
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new String[]{"ERROR-Out of Range"};
		}				
		return ArrLRows.get(intIndex);
	}//end
		
	public FastTable<String> FilterUniqueStringTable(String strColumn) {
		FastTable<String> ArrLTemp= new FastTable<String>();
		
		int curColumnID=getColumnID(strColumn);
		
		if(curColumnID==-1){
			System.err.println("ERROR-Column Name not found");
			ArrLTemp.add("ERROR-Column Name not found");
			return ArrLTemp;
		}
		
		
		for (int i = 0; i < getArrLRows().size(); i++) {
			if (!ArrLTemp.contains(getArrLRows().get(i)[curColumnID])) {
				ArrLTemp.add(getArrLRows().get(i)[curColumnID]);
			}
		}
				
		return ArrLTemp;
	}//end
	
	
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
		for (int i = 0; i < ArrLRows.size(); i++) {
			if (ArrLRows.get(i)[curColumnID].equalsIgnoreCase(strFind)) {
				// found
				// System.out.println(Arrays.toString(ArrLRows.get(i)));
				ArrLTemp.add(ArrLRows.get(i));
			} else {

			}
		}

		if (blnSave == true) {
			ArrLRows.clear();
			ArrLRows=ArrLTemp;
			
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
		for (int k = 0; k < ArrLRows.size(); k++) {// FilterArray
			String Table1ColumnValue=this.getColumnValue(Table1Column, k);
			//System.out.println(Table1ColumnValue);
			
			//System.out.println("\nTable2:");
			for (int j = 0; j < Table2.ArrLRows.size(); j++) {// FilterArray
				String Table2ColumnValue=Table2.getColumnValue(Table2Column, j);
				boolean blnMatch=Table1ColumnValue.equalsIgnoreCase(Table2ColumnValue);
				//System.out.println(Table1ColumnValue+"\t"+Table2ColumnValue+"\t="+blnMatch);
				
				if(blnMatch==true){
					ArrLTemp.add(ArrLRows.get(k));
					break;
				}
			}// end_Loop_>_FilterArray_FinishedGrants.txt
		}// end_Loop_>_FilterArray_FinishedGrants.txt

		if (blnSave == true) {
			ArrLRows.clear();
			ArrLRows=ArrLTemp;
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
		for (int k = 0; k < ArrLRows.size(); k++) {// FilterArray
			String Table1ColumnValue=this.getColumnValue(Table1Column, k);
			//System.out.println(Table1ColumnValue);
			
			//System.out.println("\nTable2:");
			for (int j = 0; j < Table2.ArrLRows.size(); j++) {// FilterArray
				String Table2ColumnValue=Table2.getColumnValue(Table2Column, j);
				boolean blnMatch=Table1ColumnValue.contains(Table2ColumnValue);
				//System.out.println(Table1ColumnValue+"\t"+Table2ColumnValue+"\t="+blnMatch);
				
				if(blnMatch==true){
					ArrLTemp.add(ArrLRows.get(k));
					break;
				}
			}// end_Loop_>_FilterArray_FinishedGrants.txt
		}// end_Loop_>_FilterArray_FinishedGrants.txt

		if (blnSave == true) {
			ArrLRows.clear();
			ArrLRows=ArrLTemp;
		}
		
		return ArrLTemp;
	}//end sub
	
	public FastTable<String[]> IncludeFilterContainFastTable(FastTable<String> FastTable1dstr,String Table1Column){
		return IncludeFilterContainFastTable(FastTable1dstr,Table1Column,false);
	}//end sub
	
	public FastTable<String[]> IncludeFilterContainFastTable(FastTable<String> FastTable1dstr,String Table1Column,boolean blnSave){
		FastTable<String[]> ArrLTemp= new FastTable<String[]>();
		
		int Table1curColumnID=this.getColumnID(Table1Column);
			
		if(Table1curColumnID==-1){
			System.err.println("ERROR-Column Name not found");
			ArrLTemp.add(new String[]{"ERROR-Column Name not found"});
			return ArrLTemp;
		}
		
		//System.out.println("Table1:");
		for (int k = 0; k < ArrLRows.size(); k++) {// FilterArray
			String Table1ColumnValue=this.getColumnValue(Table1Column, k);
			//System.out.println(Table1ColumnValue);
			
			//System.out.println("\nTable2:");
			for (int j = 0; j < FastTable1dstr.size(); j++) {// FilterArray
				String Table2ColumnValue=FastTable1dstr.get( j);
				boolean blnMatch=Table1ColumnValue.contains(Table2ColumnValue);
				//System.out.println(Table1ColumnValue+"\t"+Table2ColumnValue+"\t="+blnMatch);
				
				if(blnMatch==true){
					ArrLTemp.add(ArrLRows.get(k));
					break;
				}
			}// end_Loop_>_FilterArray_FinishedGrants.txt
		}// end_Loop_>_FilterArray_FinishedGrants.txt

		if (blnSave == true) {
			ArrLRows.clear();
			ArrLRows=ArrLTemp;
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
		
		for (int k = 0; k < ArrLRows.size(); k++) {// FilterArray
			boolean GrantMatch = false;
			String Table1ColumnValue=this.getColumnValue(Table1Column, k);
			//System.out.println(Table1ColumnValue);
			
			//System.out.println("\nTable2:");
			for (int j = 0; j < Table2.ArrLRows.size(); j++) {// FilterArray
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
				ArrLTemp.add(ArrLRows.get(k));
			}
		}// end_Loop_>_FilterArray_FinishedGrants.txt

		if (blnSave == true) {
			ArrLRows.clear();
			ArrLRows=ArrLTemp;
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
				
				for(int i = 0; i<ArrLRows.size();i++){
					if(ArrLRows.get(i)[curColumnID].equalsIgnoreCase(strFind)){
						//found
					}else{
						//System.out.println(Arrays.toString(ArrLRows.get(i)));
						ArrLTemp.add(ArrLRows.get(i));
					}
				}//end loop
				
				if (blnSave == true) {
					ArrLRows.clear();
					ArrLRows=ArrLTemp;
					
				}
				
				return ArrLTemp;
	}//end ExcludeFilterStringTable
	
	/**
	 * 
	 * @param strColumnId
	 */
	
	public void qsort(String strColumnId) {
		int curColumnID=getColumnID(strColumnId);
		
		if(curColumnID==-1){
			System.err.println("ERROR-Column Name not found >Sort ="+strColumnId);
			return;
		}
		
		quicksort(ArrLRows, 0, ArrLRows.size() - 1,curColumnID);
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
