package ciphers;

import gnu.crypto.cipher.Blowfish;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.InvalidKeyException;

import coders.Base64Coder;

// TODO: Auto-generated Javadoc
/**
 * The Class BlowFish.
 */
public class BlowFish {
	
	/**
	 * The main method.
	 *
	 * @param argv the arguments
	 * @throws Exception the exception
	 */
	public static void main(String argv[]) throws Exception {
		String key = "1234567";
		String plaintext = "In order to graduate, each graduate student must complete either COSC880 graduate " +
				"project or COSC897 graduate thesis. A graduate project is a 3-credit course that must be taken" +
				" by a student with a graduate faculty. The following policies and procedures must be followed by " +
				"a graduate student and must be insured by the graduate faculty.\nA graduate project (COSC880) serves " +
				"the purpose of providing applied skills to the student. That means, graduate project should be " +
				"focused on implementation and learning skills for the student. The graduate project can also be" +
				"based on survey of current topics in a given field of study and write a paper for publication. " +
				"Graduate faculty plays a role of training the student to write a proposal, analyze a problem," +
				" collect requirements for a problem, design, implement, test, and demonstrate a chosen problem.";

		String cipher = encrypt(plaintext, key);
		System.out.println(cipher);
		String cplaintext = decrypt(cipher, key);
		System.out.println("*"+cplaintext+"*");
	}

	/**
	 * Encrypt.
	 *
	 * @param cookieValue the cookie value
	 * @param key the key
	 * @return the string
	 * @throws InvalidKeyException the invalid key exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public static String encrypt(String cookieValue, String key)
			throws InvalidKeyException, UnsupportedEncodingException {
		byte[] plainText;
		byte[] encryptedText;
		Blowfish twofish = new Blowfish();
		// create a key
		byte[] keyBytes = key.getBytes();
		Object keyObject = twofish.makeKey(keyBytes, 8);
		// make the length of the text a multiple of the block size
		if ((cookieValue.length() % 8) != 0) {
			while ((cookieValue.length() % 8) != 0) {
				cookieValue += " ";
			}
		}
		// initialize byte arrays for plain/encrypted text
		plainText = cookieValue.getBytes("UTF8");
		encryptedText = new byte[cookieValue.length()];
		// encrypt text in 8-byte chunks
		for (int i = 0; i < Array.getLength(plainText); i += 8) {
			twofish.encrypt(plainText, i, encryptedText, i, keyObject, 8);
		}
		String encryptedString = Base64Coder.encodeLines(encryptedText);
		return encryptedString;
	}

	/**
	 * Decrypt.
	 *
	 * @param cookieValue the cookie value
	 * @param key the key
	 * @return the string
	 * @throws InvalidKeyException the invalid key exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public static String decrypt(String cookieValue, String key)
			throws InvalidKeyException, UnsupportedEncodingException {
		byte[] encryptedText;
		byte[] decryptedText;
		Blowfish twofish = new Blowfish();

		// create the key
		byte[] keyBytes = key.getBytes();
		Object keyObject = twofish.makeKey(keyBytes, 8);
		// make the length of the string a multiple of
		// the block size
		if ((cookieValue.length() % 8) != 0) {
			while ((cookieValue.length() % 8) != 0) {
				cookieValue += " ";
			}
		}
		// initialize byte arrays that will hold encrypted/decrypted
		// text
		encryptedText = Base64Coder.decodeLines(cookieValue);
		decryptedText = new byte[cookieValue.length()];
		// Iterate over the byte arrays by 16-byte blocks and decrypt.
		for (int i = 0; i < Array.getLength(encryptedText); i += 8) {
			twofish.decrypt(encryptedText, i, decryptedText, i, keyObject, 8);
		}
		String decryptedString = new String(decryptedText, "UTF8");
		return decryptedString;
	}
}