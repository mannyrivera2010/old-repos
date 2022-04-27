package org;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.shared.JavaGetUrl;
import org.shared.Util;

public class Indeed {
	static String SysLine=System.getProperty("line.separator");
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		getJobInfoSingleThread("Indeed\\location.txt","Indeed\\jobs.txt","Indeed\\info.csv");
	}

	public static void getJobInfoSingleThread(String strLocations,String strJobs, String strFileName ) throws IOException{
		StringBuilder StrOutput= new StringBuilder();
		
		Util UtilObj= new Util();

		ArrayList<String> AlLocations = UtilObj.fileto1dArrayList(strLocations, false);
		ArrayList<String> AlJobs= UtilObj.fileto1dArrayList(strJobs,false);


		System.out.print("Region"+"|");
		StrOutput.append("Region"+"|");
		for(int i=0;i<AlJobs.size();i++){
			System.out.print(AlJobs.get(i)+"|");
			StrOutput.append(AlJobs.get(i)+"|");
		}
		System.out.println();
		StrOutput.append(SysLine);

		String strRegion="";

		for(int i=0;i<AlLocations.size();i++){
			String CurrentLocation=AlLocations.get(i);


			if(CurrentLocation.contains("$")){
				//System.out.println(CurrentLocation);
				strRegion=CurrentLocation.replace("$", "").replace("-", "").trim();
			}else{
				System.out.print(strRegion+"|"+CurrentLocation+"|");
				for(int j=1;j<AlJobs.size();j++){
					String CurrentJob=AlJobs.get(j);
					System.out.print(FindJobCount(CurrentJob,CurrentLocation,"50")+"|");
					StrOutput.append(FindJobCount(CurrentJob,CurrentLocation,"50")+"|");

				}
				System.out.print("\n");
				StrOutput.append(SysLine);
			}
		}
		UtilObj.writeStringToFile(StrOutput.toString(), strFileName, false);
	}


	public static String FindJobCount(String JobDesc,String State,String radiusMiles) throws IOException{
		String strNumber="";

		JobDesc=JobDesc.trim().replace(" ", "+");
		JobDesc=JobDesc.trim().replace("#", "%23");


		State=State.trim().replace(", ", "+");
		State=State.trim().replace(",", "+");
		State=State.trim().replace(" ", "+");

		radiusMiles=radiusMiles.trim().replace(" ", "+");

		String Website="http://www.indeed.com/jobs?q="+JobDesc+"&l="+State+"&radius="+radiusMiles;

		JavaGetUrl HtmlCode = new JavaGetUrl();
		String WebsiteHtmlCode= HtmlCode.getWebSiteHtml(Website);

		ArrayList<String> ALText=ConvertToAL(WebsiteHtmlCode);

		for(int i = 0;i<ALText.size();i++){
			if(ALText.get(i).contains("<div id=\"searchCount\">")){
				strNumber=(ALText.get(i).trim());
				break;
			}
		}
		//System.err.println( "|"+JobDesc+"|\t|"+State+"|\t|"+strNumber+"|\t|"+strNumber.trim().length());

		if(strNumber.trim().length()>=1){
			return strNumber.substring(strNumber.lastIndexOf(" ")+1, strNumber.length()-6)+"";
		}else{
			return "0";
		}

	}//end Sub


	public static ArrayList<String> ConvertToAL(String strInput) throws IOException{
		ArrayList<String> Output= new ArrayList<String>();

		String strLine="";
		BufferedReader reader = new BufferedReader(new StringReader(strInput));

		while ((strLine = reader.readLine()) != null) {
			if(strLine.trim().length()>=1){
				Output.add(strLine);
			}
		}//end while

		return Output;
	}


}
