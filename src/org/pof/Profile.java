package org.pof;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.shared.JavaGetUrl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Profile {
	JavaGetUrl HtmlCode = new JavaGetUrl();
	
	public String URL;
	public String RawHtml;
	Document doc;
	
	TreeMap<String,Object> info_map= new TreeMap<String,Object>();
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//http://www.pof.com/viewprofile.aspx?profile_id=25866052
		//http://www.pof.com/viewprofile.aspx?profile_id=63853283
		Profile Person = new Profile("http://www.pof.com/viewprofile.aspx?profile_id=25441510");
		Person.Profile_Download();
		System.out.println(Person.getPrettyJson());	
	}

	public Profile(String inURL){
		this.URL=inURL;
	}
	
	public void Profile_Download(){
		//this.RawHtml=HtmlCode.getWebSiteHtml(this.URL);
		//doc = Jsoup.parse(this.RawHtml);
		try {
			Connection ConnectionMain = Jsoup.connect(this.URL)
					  .userAgent("Mozilla/5.0 (Windows;U;Windows NT 6.1;en-GB;rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)")
					  .timeout(3000);
			
			doc = ConnectionMain.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {			
			info_map.put("Profile ID", Utils.splitQuery(this.URL).get("profile_id"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element MainContrainer = doc.select("div#container").first();
		info_map.put("Username_headline", MainContrainer.select("div.username-bar").text());
		
		//INFO Section
		Elements info = MainContrainer.select("div#user-details-wrapper");
		Elements info_wide = info.select("div.user-details-wide,div.user-details-narrow");

		for (Element current : info_wide) {
			//System.out.println(current.select("div.profileheadtitle").text() +"\t"+current.select("span.txtGrey").text());
			String strHeading=current.select("div.profileheadtitle").text();
			String strContent;
					
			if(current.select("div.profileheadcontent").text().trim().length()==0){
				strContent=current.select("div.profileheadcontent-narrow").text().trim();
			}else{
				strContent=current.select("div.profileheadcontent").text().trim();
			}
			
			if(strHeading.equals("About")){
				strHeading="Raw_About";
				int temp=strContent.indexOf("with");
				String strHeadingSmoking = strContent.substring(0, temp).trim();
				String strHeadingBodyType = strContent.substring(temp+4).trim();
				info_map.put("Smoking", strHeadingSmoking);
				info_map.put("Body Type", strHeadingBodyType);				
			}else if(strHeading.equals("Details")){
				strHeading="Raw_Details";
				try{
					String[] StrTemp=strContent.split(",");
					info_map.put("Age", StrTemp[0].split(" ")[0].trim());
					info_map.put("Gender", StrTemp[0].split(" ")[3].trim());
					info_map.put("Height", StrTemp[1].trim());
					info_map.put("Religion", StrTemp[2].trim());
				}catch(Exception E){
					System.err.println(E);
				}
			}else if(strHeading.equals("Ethnicity")){
				strHeading="Raw_Ethnicity";
				try{
					String[] StrTemp=strContent.split("with");
					String[] StrTemp1=StrTemp[0].trim().split(" ");					
					if(StrTemp1.length==2){
						info_map.put("Race", StrTemp1[0].trim());
						info_map.put("Sign", StrTemp1[1].trim());
					}else if(StrTemp1.length==3){
						info_map.put("Race", StrTemp1[0].trim());
						info_map.put("Sign", StrTemp1[2].trim());
					}
					info_map.put("Hair Color", StrTemp[1].replace("hair","").trim());
				}catch(Exception E){
					System.err.println(E);
				}
			}
			info_map.put(strHeading, strContent);
		}

		//Images Section
		Elements images = MainContrainer.select("div#images");
		ArrayList<String> ImagesList = new ArrayList<String>();  
		
		for (Element current : images.select("a[href]")) {
			String cur=current.select("img").attr("onmouseover");
			ImagesList.add(cur.substring(cur.indexOf("'")+1, cur.lastIndexOf("'")));
			//info_map.put(current.select("div.profileheadtitle").text(),current.select("span.txtGrey").text());	
		}
	
		info_map.put("Images",ImagesList);
		//System.out.println(outlist);
		
		//LowerInfo
		Elements lower_info = MainContrainer.select("div.box");
		
		//LowerInfo_Table
		Element lower_info_table = MainContrainer.select("div.box div.aligncenter table tbody").first();
		String strHeadLineTemp="";
		//System.out.println(lower_info_table);
		for (Element current : lower_info_table.select("span.headline,span.txtGrey")) {
			if(current.attr("class").contains("headline")){
				strHeadLineTemp=current.text();
			}else if(current.attr("class").contains("txtGrey")){
				info_map.put(strHeadLineTemp,current.text());
			}
			//System.out.println(current.text());
		}
		
		//LowerInfo_Interest_AboutMe_FirstDate
		for (Element current : lower_info.select("div#white-box")) {
			//System.out.println(current);
			Element headline = current.select("span.headline").first();
			ArrayList<String> InterestList = new ArrayList<String>(); 
			if(headline.text().contains("Interest")){
				//System.out.println(headline);
				for (Element currentLv2 : current.select("div#profile-interests-wrapper").select("a[href]")) {
					String cur=currentLv2.text();
					//System.out.println(cur);
					 InterestList.add(cur);
				}
				info_map.put("interest",InterestList);			
			}else if(headline.text().contains("About")){
				//System.out.println(headline);
				info_map.put("About me",current.select("div#box").text());
				//System.out.println(current.select("div#box").text());
			}else if(headline.text().contains("Date")){
				//System.out.println(headline);
				info_map.put("First Date",current.select("div#box").text());
				//System.out.println(current.select("div#box").text());
			}
			
		}
		//System.out.println(lower_info.select("div#white-box "));
		//System.out.println(info_wide);
		//System.out.println(images);
	}

	public String getJson(){
		Gson gson = new Gson();
		return gson.toJson(info_map);
	}
	
	public String getPrettyJson(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(info_map);	
	}
	
	@Override
	public String toString() {
		return getPrettyJson();
	}
		
}
