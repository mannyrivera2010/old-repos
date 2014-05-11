package org.interfaces;

import java.net.DatagramPacket;

import org.networking.server.UDPServer;
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
	public void handleReceivedMessage(UDPServer serverObj, DatagramPacket datagramPacket, MessageObject message);
}
