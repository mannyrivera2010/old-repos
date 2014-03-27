package org.testing;
/**
 * @author Emanuel Rivera
 */

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

// TODO: Auto-generated Javadoc
/**
 * The Class Jasypt.
 */
public class Jasypt {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		StandardPBEStringEncryptor textEncryptor = new StandardPBEStringEncryptor();
		textEncryptor.setPassword("pass");
		textEncryptor.setStringOutputType("hex");

		String myEncryptedText = textEncryptor.encrypt("Te");
		
		String plainText = textEncryptor.decrypt(myEncryptedText);
		
		
		System.out.println(myEncryptedText);
		System.out.println(plainText);
	}

}
