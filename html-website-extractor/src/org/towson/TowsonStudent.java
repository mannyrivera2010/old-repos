package org.towson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.shared.JavaGetUrl;
import org.shared.Util;

public class TowsonStudent {
		static Util UtilObj = new Util();
		static JavaGetUrl HtmlCode = new JavaGetUrl();
		static String SysLine=System.getProperty("line.separator");
		
	public static void main(String[] args) throws IOException {		

		
		UtilObj.writeStringToFile("Last Name\tFirst Name\tWebsite", "StudentInfo.csv", false);
		for(int i=65;i<=90;i++){
			String Letter= ((char) i)+"";
			System.out.println("<!-- Letter "+ Letter.toLowerCase()+" -->");
			
			
			String WebsiteHtmlCode= HtmlCode.getWebSiteHtml("http://tiger.towson.edu/studenthomepages/"+Letter.toLowerCase()+".html");
	        
			ArrayList<String> FormatedExtracted=FormatArrayList(ExtractInfo(WebsiteHtmlCode));
			//System.out.println(WebsiteHtmlCode);
			//System.out.println();
		
			UtilObj.writeStringToFile(SysLine+UtilObj.ArrayListToString(FormatedExtracted, false),"StudentInfo.csv",true);
		}
		//65
	}
	

	public static ArrayList<String> FormatArrayList(ArrayList<String> Input){
		for(int i=0;i<Input.size();i++){
			String CurrentLine=Input.get(i).trim();
			
			//System.out.println(CurrentLine+"<<<<<<<<<");
			
			if(!CurrentLine.contains(",")){
				String LastName=CurrentLine.substring(4, CurrentLine.indexOf("-")-1).trim();
				
				String Website="";
				
				Pattern p = Pattern.compile("href=\"(.*?)\"", Pattern.DOTALL);
				Matcher m = p.matcher(CurrentLine);

				while (m.find()) 
				{
					Website=m.group(1);
				    break;
				}
				
				Input.set(i, LastName+"\t"+"?"+"\t"+Website);
				
			}else{
				
				String LastName=CurrentLine.substring(4, CurrentLine.indexOf(",")).trim();
				String FirstName=CurrentLine.substring(CurrentLine.indexOf(",")+1,CurrentLine.indexOf("<a")).trim();
				
				if(FirstName.contains("-")){
					if(FirstName.substring(FirstName.length()-1).equals("-")){
						FirstName=FirstName.substring(0, FirstName.length()-1).trim();
					}
				}
				
				
				String Website="";
				
				Pattern p = Pattern.compile("href=\"(.*?)\"", Pattern.DOTALL);
				Matcher m = p.matcher(CurrentLine);

				while (m.find()) 
				{
					Website=m.group(1);
				    break;
				}
				
				
				//Temp
				/*
				boolean blnDefaultPage=true;
				if(HtmlCode.getWebSiteHtml(Website).contains("My Temporary Home Page")){
					blnDefaultPage=true;
				}else{
					blnDefaultPage=false;
					System.out.println(Website);
				}
				*/
				
				
				
				Input.set(i, LastName+"\t"+FirstName+"\t"+Website);
				//System.out.println(LastName+"\t"+FirstName+"\t"+Website);	
			}
			
		}
		
		
		return Input;
	}
	
	
	public static ArrayList<String> ExtractInfo(String strInput) throws IOException{
		String strLine="";
		ArrayList<String> strLineOutput=new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new StringReader(strInput));
		
		while ((strLine = reader.readLine()) != null) {
			if(OnRecordFuncLine(strLine)==true && strLine.trim().length()>=1){
				//System.out.println(strLine);
				strLineOutput.add(strLine+"");
			}

			if(OnRecordPre==true){
				OnRecord=false;
			}
		}
		return strLineOutput;
	}
	
	static Boolean OnRecord=false;
	static Boolean OnRecordPre=false;
	
	public static Boolean OnRecordFuncLine(String StrLine){
		OnRecordPre=false;
		if(StrLine.contains("<li>")){
			OnRecord=true;	
		}
	
		if(StrLine.contains("</li>")){
			OnRecordPre=true;	
		}
		
		return OnRecord;
	}
	
}
