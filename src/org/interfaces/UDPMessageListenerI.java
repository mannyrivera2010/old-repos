package org.interfaces;

import java.net.DatagramPacket;

import org.utils.MessageObject;

/**
 * The Interface MessageListenerI.
 */
public interface UDPMessageListenerI {
	
	/**
	 * Handle received message
	 *
	 * @param message the message
	 */
	public void handleReceivedMessage(DatagramPacket datagramPacket, MessageObject message);
}
