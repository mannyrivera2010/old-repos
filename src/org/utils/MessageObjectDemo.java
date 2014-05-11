package org.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageObjectDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MessageObject msgObj1 = new MessageObject();
		msgObj1.setMessage("message");
		msgObj1.setError(true);
		
		System.out.println(msgObj1.toString());
		
		
		Gson gson = new Gson();
		MessageObject msgObjF = gson.fromJson(msgObj1.toString(), MessageObject.class);
		System.out.println(msgObjF);
		
		
	}

}
