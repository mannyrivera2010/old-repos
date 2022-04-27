package org;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.shared.IPFinder;
import org.shared.JavaGetUrl;
import org.shared.Util;

public class ExternalIP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JavaGetUrl HtmlCode = new JavaGetUrl();
		Util UtilObj = new Util();
		String WebsiteHtmlCode= HtmlCode.getWebSiteHtml("http://whatismyipaddress.com/");
		//System.out.println(WebsiteHtmlCode);
		
		System.out.println(IPFinder.findIPAddresses(WebsiteHtmlCode).get(0));
		
	}
	
	
	
	


}



