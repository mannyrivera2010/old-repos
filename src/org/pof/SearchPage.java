package org.pof;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.shared.JavaGetUrl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SearchPage {
	JavaGetUrl HtmlCode = new JavaGetUrl();
	
	public String URL;
	public String RawHtml;
	Document doc;
	
	private TreeSet<String> ProfileIDList = new TreeSet<String>(); 
	
	public int size() {
		return ProfileIDList.size();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SearchPage Page1 = new SearchPage("m",20,20,"21230","f",35,1);
		Page1.List_Download();
		

		
		for(int i = 2 ; i <= 65 ; i ++){
			Page1.appendSearchPage("m",20,20,"21230","f",35,i);
			System.out.println(i + " out of " + 65);
			System.out.println(Page1.size());
			
		}
	
		System.out.println(Page1.toString());	
	}

	public SearchPage(String imam,Integer minage,Integer maxage,String city,String seekinga, Integer miles, Integer page){
		//http://www.pof.com/basicsearch.aspx?iama=m&minage=20&maxage=20&city=21230&seekinga=f&ethnicity=0&sorting=0&miles=35&country=1&imagesetting=0&page=2&count=600#in
		this.URL="http://www.pof.com/basicsearch.aspx?iama="+imam+"&minage="+minage.toString()+"&maxage="+maxage.toString()+"&city="+city+"&seekinga="+seekinga+"&ethnicity=0&sorting=0&miles="+miles+"&country=1&imagesetting=0&page="+page+"&count=600#in";
	}
	
	public void appendSearchPage(String imam,Integer minage,Integer maxage,String city,String seekinga, Integer miles, Integer page){
		this.URL="http://www.pof.com/basicsearch.aspx?iama="+imam+"&minage="+minage.toString()+"&maxage="+maxage.toString()+"&city="+city+"&seekinga="+seekinga+"&ethnicity=0&sorting=0&miles="+miles+"&country=1&imagesetting=0&page="+page+"&count=600#in";
		this.List_Download();
	}
	
	public void List_Download(){
		List_Download(this.URL);
	}
	
	public void List_Download(String url){
		try {
			Connection ConnectionMain = Jsoup.connect(url)
					  .userAgent("Mozilla/5.0 (Windows;U;Windows NT 6.1;en-GB;rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)")
					  .timeout(3000);
			
			doc = ConnectionMain.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Element MainContrainer = doc.select("div#container").first();
		Elements info_wide = MainContrainer.select("div.white-row1,div.white-row2");
		
		//System.out.println(MainContrainer.text());
		for (Element current : info_wide) {
			
			Element profile  = current.select("div.profile a[href]").first();
			try {
				//System.out.println(Utils.splitQuery("http://www.pof.com/"+profile.attr("href")).get("profile_id"));
				ProfileIDList.add(Utils.splitQuery("http://www.pof.com/"+profile.attr("href")).get("profile_id"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	
	}
	
	public String getJson(){
		Gson gson = new Gson();
		return gson.toJson(this.ProfileIDList);
	}
	
	public String getPrettyJson(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this.ProfileIDList);	
	}
	
	@Override
	public String toString() {
		return getPrettyJson();
	}
		
}
