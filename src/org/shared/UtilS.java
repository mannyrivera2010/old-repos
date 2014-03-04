package org.shared;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.coders.Commons;

import javolution.util.FastTable;


/**
 * @author Emanuel Rivera
 */


public class UtilS {
	
	public static void main(String[] args) {
		//String Test="<!-- Start Content Changed-->\n <h2>google</h2>\n ";
		//insertTxtTemplate(".//HtmlTemps/htmlfiles/template.html","<!-- Start Content-->", Test);
		//System.out.println(OccurrenceSplit("12|34|56|789|12","|"));
		/*FastTable<String> a1 = new FastTable<String>();
		a1.add("A");
		a1.add("B");
		a1.add("C");
		a1.add("27");
		a1.add("A");
		a1.add("B");
		a1.add("C");
		a1.add("27");
		a1.add("A");
		a1.add("B");
		a1.add("C");
		a1.add("27");
		qsortFastTable(a1);
		System.out.println(a1);
		
		 ;
		 String time="2012-06-12 17:24:31.774";
		 System.out.println(time);
		 
		 System.out.println(FormatDate(time));
		 
		 */
		UtilS Newa = new UtilS();
		String Ascii="HTTP/1.1 200 OK \nAccess-Control-Allow-Origin: * \nContent-Length: 198 \nContent-Type: text/plain";
		String Hex="485454502F312E3120323030204F4B0D0A4163636573732D436F6E74726F6C2D416C6C6F772D4F726967696E3A202A0D0A436F6E74656E742D4C656E6774683A203139380D0A436F6E74656E742D547970653A20746578742F706C61696E";
		
		String Match="HTTP|4854|Access|F4 B0|";
		
		Commons.Log(Newa.HexContentMatch(Hex,Ascii,Match)+"");
	}
	
	public boolean HexContentMatch(String strHex,String strAscii,String StrMatch){
		
		ArrayList<String> HexMatch= new ArrayList<String>();
		ArrayList<String> ContentMatch=new ArrayList<String>();
			
		boolean OnHex=true;
		StringBuilder Temp=new StringBuilder();
		
		for(int i = 1;i<=StrMatch.length();i++){
			String Letter=StrMatch.substring(i-1,i);
			
			Temp.append(Letter);
			
			if(Letter.equals("|")||i==StrMatch.length()){
				if (OnHex == false)
					OnHex = true;
				else
					OnHex = false;
				
				
				if(i<StrMatch.length()){
					Temp.deleteCharAt(Temp.length()-1);
				}else if(i==StrMatch.length()&&OnHex==true){
					Temp.deleteCharAt(Temp.length()-1);
				}
				
				
				//Commons.Log(OnHex+">"+Temp.toString());	
				
				if(OnHex){
					HexMatch.add(Temp.toString().trim().replace(" ",""));
				}else{
					ContentMatch.add(Temp.toString());
				}
				
				Temp.delete(0, Temp.length()); //Delete Previous String
			}
			
			
			
		}
		
		
		/////
		boolean OutContent=true;
		for (int i = 0; i < ContentMatch.size(); i++) {
			if(!strAscii.contains(ContentMatch.get(i))){
				OutContent=false;
				break;
			}
		}
	
		
		
		boolean OutHex=true;
		for (int i = 0; i < HexMatch.size(); i++) {
			if(!strHex.contains(HexMatch.get(i))){
				OutHex=false;
				break;
			}
		}
		
		if(OutContent==true && OutHex==true)
			return true;
		else	
			return false;
	}
	
	public void PrintSepLine() {
		System.out.println("===========================");
	}// end PrintSepLine()
	
	public String GetCurrentFormattedDate(){
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
	
    public String FormatDate(String input) {
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
	
	public void qsortFastTable(FastTable<String> ArrInput) {
		quicksortFastTable(ArrInput, 0, ArrInput.size() - 1);
	}//end qsort

	private void quicksortFastTable(FastTable<String> list, int p, int r) {
		if (p < r) {
			int q = partitionFastTable(list, p, r);
			if (q == r) {
				q--;
			}
			quicksortFastTable(list, p, q);
			quicksortFastTable(list, q + 1, r);
		}
	}//end quicksort

	private int partitionFastTable(FastTable<String> list, int p, int r) {
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
	
	public FastTable<String> applyUniqueKey(FastTable<String> a1) {
		FastTable<String> a2 = new FastTable<String>();
		for (int i = 0; i < a1.size(); i++) {
			if (!a2.contains(a1.get(i))) {
				a2.add(a1.get(i));
			}
		}
		return a2;
	}
	
	
	public FastTable<String> OccurrenceSplit(String strInput,String strDelimeter){
		FastTable<String> ArrCurrent= new FastTable<String>();
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
	
	public String insertStringTemplate(String strSource ,String strFind, String strReplaceBy){
		String TemplateString=strSource.replace(strFind, strReplaceBy);
		return TemplateString;
	}
	
	public boolean IsNum(String Input) {//Check if Input is Number
		boolean isnum = true;

		try {
			Integer.parseInt(Input);
		} catch (Exception e) {
			isnum = false;
		}

		return isnum;
	}
	
	
	public FastTable<String> fileto1dFastTable(String StrFile){
		return fileto1dFastTable(StrFile,true);
	}
	
	public FastTable<String> fileto1dFastTable(String StrFile,boolean blnIncludeBlankLines){
		FastTable<String> ArrLLog = new FastTable<String>();		
		
		try {// Main
			ArrLLog.clear();// Clear FastTable
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
	}//end fileto1dFastTable()
	
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
	}//end fileto1dFastTable()
	
	
	public boolean folderExistAndMake(String StringInFolder){
		File CurrentFolder=new File(StringInFolder);
		
		boolean exists = CurrentFolder.exists();
		if(exists==false){
			CurrentFolder.mkdirs();
		}
		return exists;
	}
	
	public boolean folderExist(String StringInFolder){
		File CurrentFolder=new File(StringInFolder);
		boolean exists = CurrentFolder.exists();
		return exists;
	}
	
	public void writeToTextToFile(String Results,String Path){
		writeToTextToFile(Results,Path,false);
	}//end void

	
	public void writeToTextToFile(String Results,String Path,boolean append){
	
		try {
			FileWriter fstream = new FileWriter(Path, append);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(Results);
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}//end void
	

	public String FastTableToString(FastTable<String> ArrInput){
		return FastTableToString(ArrInput,true);
	}
	
	public String FastTableToString(FastTable<String> ArrInput,boolean blnIncludeBlankLines){
		String Output="";
		for(int i=0;i<ArrInput.size();i++){
			
			if(ArrInput.get(i).trim().length()>=0&&blnIncludeBlankLines==true){
				Output+=(ArrInput.get(i))+"\n";
			}else if (ArrInput.get(i).trim().length()>=1&&blnIncludeBlankLines==false){
				Output+=(ArrInput.get(i))+"\n";
			}
		}
		return Output;	
	}//end String

}//End Class
