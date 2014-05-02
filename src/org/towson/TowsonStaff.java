package org.towson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.shared.JavaGetUrl;
import org.shared.Util;

public class TowsonStaff {

	public static void main(String[] args) throws IOException {
		JavaGetUrl HtmlCode = new JavaGetUrl();
		Util UtilObj = new Util();
		//String WebsiteHtmlCode= HtmlCode.getWebSiteHtml("http://inside.towson.edu/GeneralCampus/phonebook/Public/PublicEmployeeSearchResults.cfm?LastName=A");
        
		//ExtractInfo(WebsiteHtmlCode);
		//System.out.println(WebsiteHtmlCode);
		
		StringBuilder FinalOutput=new StringBuilder();
		
		//65
		
		FinalOutput.append(UtilObj.filetoString("TowsonStaff\\header"));
		
		for(int i=65;i<=90;i++){
			char Letter= (char) i;
			System.out.println("<!-- Letter "+ Letter+" -->");
			
			String WebsiteHtmlCode= HtmlCode.getWebSiteHtml("http://inside.towson.edu/GeneralCampus/phonebook/Public/PublicEmployeeSearchResults.cfm?LastName="+Letter);
	        
			Boolean OnRecord=false;
			FinalOutput.append(ExtractInfo(WebsiteHtmlCode));
			
		}
		
		FinalOutput.append(UtilObj.filetoString("TowsonStaff\\footer"));
		
		System.out.println(FinalOutput);	
		
		
		UtilObj.writeStringToFile(FinalOutput.toString(), "TowsonStaff\\TowsonStaff.html",false);
	}

	public static String ExtractInfo(String strInput) throws IOException{
		String strLine="";
		StringBuilder strLineOutput=new StringBuilder();
		BufferedReader reader = new BufferedReader(new StringReader(strInput));
		
		while ((strLine = reader.readLine()) != null) {
			if(OnRecordFuncLine(strLine)==true && strLine.trim().length()>=1){
				//System.out.println(strLine);
				strLineOutput.append(strLine+"\n");
			}

			if(OnRecordPre==true){
				OnRecord=false;
			}
		}
		
		return strLineOutput.toString();
	}
	
	static Boolean OnRecord=false;
	static Boolean OnRecordPre=false;
	
	public static Boolean OnRecordFuncLine(String StrLine){
		OnRecordPre=false;
		if(StrLine.contains("<tr bgcolor=\"EFEFEF\" valign=top>")){
			OnRecord=true;	
		}
		
		if(StrLine.contains("<tr bgcolor=\"FFFFFF\" valign=top>")){
			OnRecord=true;	
		}
		
		
		if(StrLine.contains("<title>Faculty / Staff Phonebook - Towson University</title>")){
			OnRecord=false;	
		}
		
	
		if(StrLine.contains("</tr>")){
			OnRecordPre=true;	
		}
		
		return OnRecord;
	}
	
}
