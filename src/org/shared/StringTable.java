package org.shared;
/**
 * @author Emanuel Rivera
 */

import javolution.text.TextBuilder;
import javolution.util.FastTable;

// TODO: Auto-generated Javadoc
/**
 * The Class StringTable.
 */
public class StringTable {
	/*
	 * Variables
	 */
	/** The Arr l rows. */
	private FastTable<String[]> ArrLRows = new FastTable<String[]>();  //Creates a FastTable of a String Arrays
	
	/** The Arr column header. */
	private String[] ArrColumnHeader;//Header.  names of the columns
	
	/** The Column number. */
	private int ColumnNumber; //The number of Columns that are in the table
	
	/** The str delimiter. */
	private String strDelimiter; //The Delimiter of the Table

	/**
	 * Constructors.
	 */
	
	public StringTable() {
		//Empty
	}//end
	
	/**
	 * Used to create the table
	 * Sets the header columns and
	 * Sets the strDelimiter.
	 *
	 * @param strConRaw the str con raw
	 * @param strDelimiter the str delimiter
	 */
	public StringTable(String strConRaw, String strDelimiter) {
		super();
		this.strDelimiter=strDelimiter;
		ContructHeader(strConRaw);		
	}//end
		
	/**
	 * Supporting Constructor.
	 *
	 * @param strConRaw the str con raw
	 */
	private void ContructHeader(String strConRaw){
		int CurOccurence=this.numberOfStringOccurence(strConRaw, strDelimiter);
		//System.out.println("Occurence>"+CurOccurence);
		
		if(CurOccurence==-2){
			this.ColumnNumber=1;
		}else{
			this.ColumnNumber=CurOccurence;	
		}
		
		ArrColumnHeader= new String[this.ColumnNumber];
		
		if(CurOccurence==-2){
			this.ArrColumnHeader[0]=strConRaw.trim();
		}else{
			String[] results = strConRaw.split(strDelimiter); 
		
			if(ArrColumnHeader.length!=results.length){
				System.err.println("insertStringColumn Error : ArrColumn.length!=results.length");
			}
			
			for (int i = 0; i < this.ArrColumnHeader.length; i++) {
				ArrColumnHeader[i] = results[i];
			}
		}
	}//end ContructHeader
	
	/**
	 * Used to obtain a string array of the table's header.
	 *
	 * @return the arr column header
	 */
	
	public String[] getArrColumnHeader() {
		return ArrColumnHeader;
	}

	/**
	 * Used to obtain the number of Columns.
	 *
	 * @return the column number
	 */
	
	public int getColumnNumber() {
		return ColumnNumber;
	}

	/**
	 * Used to obtain the Delimiter used by the table.
	 *
	 * @return the str delimiter
	 */
	
	public String getStrDelimiter() {
		return strDelimiter;
	}
	
	/**
	 * Used to get Table columns and rows without the header.
	 *
	 * @return the arr l rows
	 */
	public FastTable<String[]> getArrLRows() {//Get 
		return ArrLRows;
	}//end getArrLRows()
	
	/**
	 * Used to get size of Table columns and rows without the header.
	 *
	 * @return the arr l rows size
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
    

	/**
	 * Sets the arr column header.
	 *
	 * @param arrColumnHeader the arr column header
	 * @param StrDelimiter the str delimiter
	 */
	public void setArrColumnHeader(String[] arrColumnHeader, String StrDelimiter) {
		this.strDelimiter=StrDelimiter;
		ColumnNumber=arrColumnHeader.length;
		ArrColumnHeader = arrColumnHeader;
		ArrLRows.clear();
	}

	/**
	 * Used to Remove the first row of ArrayList.
	 */
	public void Remove1stArrLRows(){
		ArrLRows.remove(0);
	}
	
	/**
	 * Used to insert a row with string that contains delimiters
	 * ex) first,last.
	 *
	 * @param strConRaw the str con raw
	 */
	
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
			String[] ArrColumn = new String[this.ColumnNumber];  //Create new String Array with correct size
			String[] results = strConRaw.split(strDelimiter);   //split String 
			
			//Check to make sure column length match with results length
			if(ArrColumn.length!=results.length){
				System.err.println("insertStringColumn Error : " +
						"ArrColumn.length!=results.length\t"+ArrColumn.length+"\t"+results.length);
				return; 
			}
			
			//  
			for (int i = 0; i < this.ColumnNumber; i++) {
				ArrColumn[i] = results[i].trim();
				//System.out.println("Debug>Token:"+ArrColumn[i]);
			}
			
			
			ArrLRows.add(ArrColumn);
		}
	}// end InsertStringColumn()
	
	/**
	 * Used to insert a row with a String Array.
	 *
	 * @param inputArray the input array
	 */
	public void insertStringArray(String[] inputArray) {
		//Check to make sure column length match with results length
		if(this.ColumnNumber!=inputArray.length){
			System.err.println("Columns does not match number of columns. Table has "+this.ColumnNumber+", trying to insert with "+ inputArray.length);
			
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return; //exit method before it add
		}
		ArrLRows.add(inputArray);		
	}//end InsertStringColumn()
	

	
	/**
	 * toString  Method
	 * Print out the table with the delimiters.
	 *
	 * @return the string
	 */
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
	
	/**
	 * support toString() method
	 * Constuction of toString() body "The rows".
	 *
	 * @return the string
	 */
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

	/**
	 * supports internal method
	 * used for obtain the number of occurence of a string.
	 *
	 * @param strInput the str input
	 * @param strFind the str find
	 * @return the int
	 */
	private int numberOfStringOccurence(String strInput, String strFind) {
		if(strDelimiter.length()<=0){
			return -2;
		}
		
		String[] results = strInput.split(strDelimiter); 
		int count = results.length;

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

	/**
	 * Supporting Method
	 * Find the index of Column.
	 *
	 * @param strColumnName the str column name
	 * @return the column id
	 */
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
	
	/**
	 * Used getting the values of Array.
	 *
	 * @param intArrayIndex the int array index
	 * @param intIndex the int index
	 * @return the column value array
	 */
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
	
	/**
	 * Gets the ColumnValue using the name of column
	 * and the Index value for row.
	 *
	 * @param strColumnName the str column name
	 * @param intIndex the int index
	 * @return the column value
	 */
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
		return TempString;
	}//end
	
	
	/**
	 * Used to obtain the Array Row using the index.
	 * It returns a String Array
	 *
	 * @param intIndex the int index
	 * @return the string[]
	 */
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
				
	/**
	 * Used for Sorting Table based on.
	 *
	 * @param strColumnId the str column id
	 */
	public void qsort(String strColumnId) {
		int curColumnID=getColumnID(strColumnId);
		
		if(curColumnID==-1){
			System.err.println("ERROR-Column Name not found >Sort ="+strColumnId);
			return;
		}
		
		quicksort(ArrLRows, 0, ArrLRows.size() - 1,curColumnID);
	}//end qsort

	/**
	 * Supporting qsort method.
	 *
	 * @param list the list
	 * @param p the p
	 * @param r the r
	 * @param id the id
	 */
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

	/**
	 * Supporting sorting method.
	 *
	 * @param list the list
	 * @param p the p
	 * @param r the r
	 * @param id the id
	 * @return the int
	 */
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
