package org.shared;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Emanuel Rivera
 */

public class Util {

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
	



}//End Class
