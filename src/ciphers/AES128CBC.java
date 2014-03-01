package ciphers;

import gnu.crypto.mode.IMode;
import gnu.crypto.mode.ModeFactory;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

import coders.Base64Coder;
import coders.HexCoder;

// TODO: Auto-generated Javadoc
/**
 * The Class AES128CBC.
 */
public class AES128CBC {
	
	/**
	 * The main method.
	 *
	 * @param argv the arguments
	 * @throws Exception the exception
	 */
	public static void main(String argv[]) throws Exception{
		TEST1();
	}
	
	/**
	 * TES t1.
	 *
	 * @throws InvalidKeyException the invalid key exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public static void TEST1() throws InvalidKeyException, UnsupportedEncodingException{
		String key = "password";

		String plaintext = 
				"Manny-1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111112222313" +
				"\n1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
				"\n1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
				"\n1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
				"\n1111111111222222225555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555" +
				"\n2222222222222222222222222222222222222222222222222222223333333333333333333333555555555555555556666666666666666666666" +
				"\n5555555555555555555555555555123123123456465545611111111111111111111111111111111111111111111111111111111111111111111" +
				"\n                                                                                                                   ";

		AES128CBC AesEn=new AES128CBC();
		
		String cipher = AesEn.AES_encrypt(plaintext, key);
		System.out.println(cipher);
		String cplaintext = AesEn.AES_decrypt(cipher, key);
		System.out.println("*"+cplaintext+"*");
		
		
	}


	  
	/**
	 * AE s_encrypt.
	 *
	 * @param cookieValue the cookie value
	 * @param key the key
	 * @return the string
	 * @throws InvalidKeyException the invalid key exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public String AES_encrypt(String cookieValue, String key)
			throws InvalidKeyException, UnsupportedEncodingException {
		byte[] pt;
		byte[] ct;
		
		// create a key
		byte[] key_bytes = coders.Keys.Create16byteKey_MD5(key);
		byte[] iv_bytes = coders.Keys.Create16byteKey_MD5("iv");
		
		IMode mode = ModeFactory.getInstance("CBC", "AES", 16);
		Map<String, Object> attributes = new HashMap<String, Object>();
		// These attributes are defined in gnu.crypto.cipher.IBlockCipher.
		
		attributes.put(IMode.KEY_MATERIAL, key_bytes);
		attributes.put(IMode.CIPHER_BLOCK_SIZE, new Integer(16));
		// These attributes are defined in IMode.
		attributes.put(IMode.STATE, new Integer(IMode.ENCRYPTION));
		attributes.put(IMode.IV, iv_bytes);
		
		mode.init(attributes);
		int bs = mode.currentBlockSize();
		
		// make the length of the text a multiple of the block size
		if ((cookieValue.length() % bs) != 0) {
			while ((cookieValue.length() % bs) != 0) {
				cookieValue += " ";
			}
		}
		// initialize byte arrays for plain/encrypted text
		pt = cookieValue.getBytes("UTF8");
		ct = new byte[cookieValue.length()];
		// encrypt text in 8-byte chunks
	
		for (int i = 0; i < pt.length; i += bs)
		{
		mode.update(pt, i, ct, i);
		
		}
		//////////////////
		String encryptedString = Base64Coder.encodeLines(ct);
		//String encryptedString = HexCoder.bytesToHex(ct);
		//String encryptedString = new String(ct);
		return encryptedString;
	}
	
	
	/**
	 * AE s_decrypt.
	 *
	 * @param cookieValue the cookie value
	 * @param key the key
	 * @return the string
	 * @throws InvalidKeyException the invalid key exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public String AES_decrypt(String cookieValue, String key)
			throws InvalidKeyException, UnsupportedEncodingException {
		byte[] pt;
		byte[] ct;
		// create a key
		byte[] key_bytes = coders.Keys.Create16byteKey_MD5(key);
		byte[] iv_bytes = coders.Keys.Create16byteKey_MD5("iv");
	
		IMode mode = ModeFactory.getInstance("CBC", "AES", 16);
		Map<String, Object> attributes = new HashMap<String, Object>();
		// These attributes are defined in gnu.crypto.cipher.IBlockCipher.
		
		attributes.put(IMode.KEY_MATERIAL, key_bytes);
		attributes.put(IMode.CIPHER_BLOCK_SIZE, new Integer(16));
		// These attributes are defined in IMode.
		attributes.put(IMode.STATE, new Integer(IMode.DECRYPTION));
		attributes.put(IMode.IV, iv_bytes);
		mode.init(attributes);
		int bs = mode.currentBlockSize();
		
		// make the length of the text a multiple of the block size
		if ((cookieValue.length() % bs) != 0) {
			while ((cookieValue.length() % bs) != 0) {
				cookieValue += " ";
			}
		}
		
		// initialize byte arrays that will hold encrypted/decrypted
		// text
		//ct = cookieValue.getBytes();
		//ct = HexCoder.hexToBytes(cookieValue);
		ct = Base64Coder.decodeLines(cookieValue);
		pt = new byte[cookieValue.length()];
		// Iterate over the byte arrays by 16-byte blocks and decrypt.
		for (int i = 0; i < ct.length; i += bs)
		{
		mode.update(ct, i, pt, i);
		}
		String decryptedString = new String(pt, "UTF8");
		return decryptedString.trim();
	}
	
}