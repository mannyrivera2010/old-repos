package org.local;

import java.util.ArrayList;

import org.exceptions.SubscriptionException;
import org.interfaces.MessageListenerI;
import org.utils.MessageObject;

/**
 * The Class Demo.
 */
public class Demo {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws SubscriptionException the subscription exception
	 */
	public static void main(String[] args){
		//Making Main Message Bus
		MessageBus mainMessageBus = new MessageBus();

		//Creating Client Alice
		Client alice = new Client("alice");
		alice.connect(mainMessageBus);
		try {
			alice.subscribe("foo");
		} catch (SubscriptionException e) {
			e.printStackTrace();
		}
		
		//Creating Client Bob
		Client bob = new Client("bob");
		bob.connect(mainMessageBus);
		try {
			bob.subscribe("bar");
		} catch (SubscriptionException e) {
			e.printStackTrace();
		}
		
		//Creating Client carol
		Client carol = new Client("carol");
		carol.connect(mainMessageBus);
		try {
			carol.subscribe("bar");
			carol.subscribe("foo");
		} catch (SubscriptionException e) {
			e.printStackTrace();
		}

		
		//Add Message Listeners for Clients
		alice.addMessageListener(new MessageListenerI(){
			@Override
			public void handleReceivedMessage(MessageObject message) {
				System.out.printf("Alice - %s\n", message.getMessage());
			}
		});
		
		bob.addMessageListener(new MessageListenerI(){
			@Override
			public void handleReceivedMessage(MessageObject message) {
				System.out.printf("Bob - %s\n", message.getMessage());
			}
		});
		
		carol.addMessageListener(new MessageListenerI(){
			@Override
			public void handleReceivedMessage(MessageObject message) {
				System.out.printf("Carol - %s\n", message.getMessage());
			}
		});
		
		//Sending
		//subscriberB.publish("A", "hello1");
		
		alice.publish("foo", "Hello, World!");
		System.out.println("===========");
		alice.publish("bar", "Hello, World!");
		System.out.println("===========");
		carol.publish("fiz", "Goodbye, cruel world");
	}

}
