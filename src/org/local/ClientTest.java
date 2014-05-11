package org.local;

import static org.junit.Assert.*;

import org.interfaces.MessageBusI;
import org.interfaces.MessageListenerI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.utils.MessageObject;

public class ClientTest {

	@Test
	public void testDemo() {
		//Making Main Message Bus
		MessageBusI mainMessageBus = new MessageBus();

		//Creating Client Alice
		Client alice = new Client("alice");
		alice.connect(mainMessageBus);
		alice.subscribe("foo");

		//Creating Client Bob
		Client bob = new Client("bob");
		bob.connect(mainMessageBus);
		bob.subscribe("bar");

		
		//Creating Client carol
		Client carol = new Client("carol");
		carol.connect(mainMessageBus);
		carol.subscribe("bar");
		carol.subscribe("foo");
		
		//Messages
		final StringBuilder aliceMessages = new StringBuilder();
		final StringBuilder bobMessages = new StringBuilder();
		final StringBuilder carolMessages = new StringBuilder();

		//Add Message Listeners for Clients
		alice.addMessageListener(new MessageListenerI(){
			@Override
			public void handleReceivedMessage(MessageObject message) {
				aliceMessages.append(String.format("Alice - %s\n", message.getMessage()));
			}
		});
		
		bob.addMessageListener(new MessageListenerI(){
			@Override
			public void handleReceivedMessage(MessageObject message) {
				bobMessages.append(String.format("Bob - %s\n", message.getMessage()));
				
			}
		});
		
		carol.addMessageListener(new MessageListenerI(){
			@Override
			public void handleReceivedMessage(MessageObject message) {
				carolMessages.append(String.format("Carol - %s\n", message.getMessage()));
			}
		});
		
		//Sending
		alice.publish("foo", "Hello, World!");
		System.out.println("===========");
		alice.publish("bar", "Hello, World!");
		System.out.println("===========");
		carol.publish("fiz", "Goodbye, cruel world");
		
		String expectedAliceMessage = "Alice - Hello, World!\n";
		String expectedBobMessage = "Bob - Hello, World!\n";
		String expectedCarolMessage = "Carol - Hello, World!\nCarol - Hello, World!\n";
		
		Assert.assertEquals(expectedAliceMessage, aliceMessages.toString());
		Assert.assertEquals(expectedBobMessage, bobMessages.toString());
		Assert.assertEquals(expectedCarolMessage, carolMessages.toString());
	}

}
