package org.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.interfaces.MessageListenerI;
import org.utils.MessageObject;

public class UDPClientDemo {

	public static void main(String args[]) throws IOException {
		final UDPClient uDPClientObj= new UDPClient("127.0.0.1", 7777);
		
		uDPClientObj.addMessageListener(new MessageListenerI(){
			@Override
			public void handleReceivedMessage(MessageObject message) {
				System.out.printf("%s\n", message);
			}
		});
		
		Thread t1 = new Thread(uDPClientObj);
		t1.start();

		while(true){
			BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
			// take input and send the packet
			System.out.println("Enter message to send : ");
			String s = (String) cin.readLine();
			uDPClientObj.sendMsg(new MessageObject(s));	
		}
		
		
	}

}
