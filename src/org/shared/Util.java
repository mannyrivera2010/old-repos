package org.shared;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.util.ArrayList;

/**
 * @author Emanuel Rivera
 */

public class Util {


	static String SysLine=System.getProperty("line.separator");
	
	public void PrintSepLine() {
		System.out.println("===========================");
	}// end PrintSepLine()

	
	/**
	 * Used to get the current system time and convert it to formatted date 
	 * @return
	 */
	public String getCurrentFormattedDate(){
		java.util.Date Curdate= new java.util.Date();
		
		String strDate = new Timestamp(Curdate.getTime())+"";
		
		try {
		    SimpleDateFormat DATESource = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date date = DATESource.parse(strDate);
		    SimpleDateFormat DATEFinish = new SimpleDateFormat("MMM d,yyyy hh:mm:ss aa");
		    strDate = DATEFinish.format(date);
		    // System.out.println("Converted date is : " + strDate);

		} catch (Exception e) {
		    // System.out.println("Parse Exception : " + pe);
		    return "*ERROR*";
		}
		
		return strDate;
	}
	
	/**
	 * Used to format Date from 24-hour to 12-hour format
	 * @param input
	 * @return
	 */
    public String formatDate(String input) {
	String strDate = input;
	try {
	    SimpleDateFormat DATESource = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date date = DATESource.parse(strDate);
	    SimpleDateFormat DATEFinish = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
	    strDate = DATEFinish.format(date);
	    // System.out.println("Converted date is : " + strDate);

	} catch (Exception e) {
	    // System.out.println("Parse Exception : " + pe);
	    return "*ERROR*";
	}

	return strDate;
    }
	
    /**
     * Sorting of ArrayList with 1 column
     * @param ArrInput
     */
	public void qsortArrayList(ArrayList<String> ArrInput) {
		quicksortArrayList(ArrInput, 0, ArrInput.size() - 1);
	}//end qsort

	/**
	 * Supporting method for Sorting
	 * @param list
	 * @param p
	 * @param r
	 */
	private void quicksortArrayList(ArrayList<String> list, int p, int r) {
		if (p < r) {
			int q = partitionArrayList(list, p, r);
			if (q == r) {
				q--;
			}
			quicksortArrayList(list, p, q);
			quicksortArrayList(list, q + 1, r);
		}
	}//end quicksort

	/**
	 * Supporting method for Sorting
	 * @param list
	 * @param p
	 * @param r
	 * @return
	 */
	private int partitionArrayList(ArrayList<String> list, int p, int r) {
		String pivot = list.get(p);
		int lo = p;
		int hi = r;

		while (true) {
			while (list.get(hi).compareTo(pivot) >= 0 && lo < hi) {
				hi--;
			}
			while (list.get(lo).compareTo(pivot) < 0 && lo < hi) {
				lo++;
			}
			if (lo < hi) {
				String T = list.get(lo);
				list.set(lo, list.get(hi));
				list.set(hi, T);
				
			} else
				return hi;
		}
	}//end partition
	
	public ArrayList<String> applyUniqueKey(ArrayList<String> a1) {
		ArrayList<String> a2 = new ArrayList<String>();
		for (int i = 0; i < a1.size(); i++) {
			if (!a2.contains(a1.get(i))) {
				a2.add(a1.get(i));
			}
		}
		return a2;
	}
	
	/**
	 * Used to Split string with a Delimiter 
	 * @param strInput
	 * @param strDelimeter
	 * @return
	 */
	public ArrayList<String> OccurrenceSplit(String strInput,String strDelimeter){
		ArrayList<String> ArrCurrent= new ArrayList<String>();
		if(strInput.trim().length()==0){//if nothing 
			return ArrCurrent;
		}
						
		if(strDelimeter.trim().length()==0||strInput.indexOf(strDelimeter)<=0){
			ArrCurrent.add(strInput);
		}
		
		return ArrCurrent;
	}
	
	
	public String insertTxtTemplate(String strTemplateFile ,String strFind, String strReplaceBy){
		String TemplateString=this.filetoString(strTemplateFile);		
		TemplateString=TemplateString.replace(strFind, strReplaceBy);
		return TemplateString;
	}
	
	/**
	 * Used to replace text
	 * @param strSource
	 * @param strFind
	 * @param strReplaceBy
	 * @return
	 */
	public String insertStringTemplate(String strSource ,String strFind, String strReplaceBy){
		String TemplateString=strSource.replace(strFind, strReplaceBy);
		return TemplateString;
	}
	
	/**
	 * Used to see if string is a integer
	 * @param Input
	 * @return
	 */
	public boolean IsNum(String Input) {
		boolean isnum = true;

		try {
			Integer.parseInt(Input);
		} catch (Exception e) {
			isnum = false;
		}

		return isnum;
	}
	
	/**
	 * used to Convert data in file to 1 columned ArrayList
	 * @param StrFile
	 * @return
	 */
	public ArrayList<String> fileto1dArrayList(String StrFile){
		return fileto1dArrayList(StrFile,true);
	}
	
	/**
	 * used to Convert data in file to 1 columned ArrayList
	 * @param StrFile
	 * @return
	 */
	
	public ArrayList<String> fileto1dArrayList(String StrFile,boolean blnIncludeBlankLines){
		ArrayList<String> ArrLLog = new ArrayList<String>();		
		
		try {// Main
			ArrLLog.clear();// Clear ArrayList
			String StrFILENAME = StrFile;
			FileInputStream fstream = new FileInputStream(StrFILENAME);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
		
				if(strLine.trim().length()>=0&&blnIncludeBlankLines==true){
					ArrLLog.add(strLine.trim());
				}else if (strLine.trim().length()>=1&&blnIncludeBlankLines==false){
					ArrLLog.add(strLine.trim());
				}

			}
			in.close(); // Close the input stream
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}// End Main
		return ArrLLog;
	}//end fileto1dArrayList()
	
	/**
	 * used to Convert data in file to string
	 * @param StrFile
	 * @return
	 */
	
	public String filetoString(String StrFile){
		String Output="";
		try {// Main
			String StrFILENAME = StrFile;
			FileInputStream fstream = new FileInputStream(StrFILENAME);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				Output+=(strLine.trim())+"\n";
			}
			in.close(); // Close the input stream
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}// End Main
		return Output;
	}//end fileto1dArrayList()
	
	
		
	public void writeStringToFile(String Results,String Path){
		writeStringToFile(Results,Path,false);
	}//end void

	
	public void writeStringToFile(String Results,String Path,boolean append){
	
		try {
			FileWriter fstream = new FileWriter(Path, append);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(Results);
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}//end void
	

	
	public String ArrayListToString(ArrayList<String> ArrInput){
		return ArrayListToString(ArrInput,true);
	}
	
	public String ArrayListToString(ArrayList<String> ArrInput,boolean blnIncludeBlankLines){
		StringBuilder Output= new StringBuilder();
		for(int i=0;i<ArrInput.size();i++){
			
			if(ArrInput.get(i).trim().length()>=0&&blnIncludeBlankLines==true){
				Output.append((ArrInput.get(i))+SysLine);
			}else if (ArrInput.get(i).trim().length()>=1&&blnIncludeBlankLines==false){
				Output.append(ArrInput.get(i)+SysLine);
			}
		}
		return Output.toString();	
	}//end String

}//End Class
