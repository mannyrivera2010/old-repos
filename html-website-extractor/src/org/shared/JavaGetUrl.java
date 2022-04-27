package org.shared;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.LineNumberInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.text.html.HTMLDocument;

public class JavaGetUrl {

	public String getWebSiteHtml(String strURL) {
		String Output="";

		try {
			URL url = new URL(strURL);
			URLConnection urlc = url.openConnection();
			urlc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
			BufferedInputStream buffer = new BufferedInputStream(
					urlc.getInputStream());

			StringBuilder builder = new StringBuilder();
			int byteRead;
			while ((byteRead = buffer.read()) != -1)
				builder.append((char) byteRead);

			buffer.close();

			Output+=builder.toString().trim();
			Output+="\n\n[The size of the web page is "+ builder.length() / 1024 + " Kilobytes.]";

			//Object content =
				//	   new URL("http://www.brainbench.com").getContent();
		 	
			LineNumberInputStream i;
			AtomicLong a;
			
			
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return Output;
	}



}
