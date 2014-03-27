package org.shared.encryption;
/**
 * @author Emanuel Rivera
 */

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

// TODO: Auto-generated Javadoc
/**
 * The Class Encoder.
 */
public class Encoder {
	
	/** The Constant StrPassword. */
	private final static String StrPassword="3D3J4KJKFLDSI903N393NFKJD903KJ";// Random password
	// variable can't be reassigned after initialization
	
	/**
	 * Method to encode String.
	 *
	 * @param Input the input
	 * @return the string
	 */
	public static String Encode(String Input) {
		//Creates a StandardPBEStringEncryptor
		StandardPBEStringEncryptor textEncryptor = new StandardPBEStringEncryptor();
		textEncryptor.setPassword(StrPassword);
		textEncryptor.setStringOutputType("hex");

		String myEncryptedText = textEncryptor.encrypt(Input);
		return myEncryptedText;
	}// END SUB
	
	/**
	 * Method to decode string.
	 *
	 * @param Input the input
	 * @return the string
	 */
	public static String Decode(String Input) {
		StandardPBEStringEncryptor textEncryptor = new StandardPBEStringEncryptor();
		textEncryptor.setPassword(StrPassword);
		textEncryptor.setStringOutputType("hex");
		
		String plainText = textEncryptor.decrypt(Input);
		return plainText;
	}// END SUB
	
}
