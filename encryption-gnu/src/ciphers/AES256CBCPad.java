package ciphers;

import gnu.crypto.mode.IMode;
import gnu.crypto.mode.ModeFactory;
import gnu.crypto.pad.IPad;
import gnu.crypto.pad.PadFactory;
import gnu.crypto.pad.WrongPaddingException;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

import coders.Base64Coder;
import coders.HexCoder;

// TODO: Auto-generated Javadoc
/**
 * The Class AES256CBCPad.
 */
public class AES256CBCPad {
	
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

		AES256CBCPad AesEn=new AES256CBCPad();
		
		String cipher = AesEn.AES_encrypt(plaintext, key);
		System.out.println(cipher);
		String cplaintext = AesEn.AES_decrypt(cipher, key);
		System.out.println(cplaintext);		
	}


	/**
	 * AE s_encrypt.
	 *
	 * @param plaintext1 the plaintext1
	 * @param key the key
	 * @return the string
	 * @throws InvalidKeyException the invalid key exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public String AES_encrypt(String plaintext1, String key)
			throws InvalidKeyException, UnsupportedEncodingException {

		byte[] cookieValue=plaintext1.getBytes("UTF8");
		// create a key
		byte[] key_bytes = coders.Keys.Create32byteKey_SHA256(key);
		byte[] iv_bytes = coders.Keys.Create32byteKey_SHA256("iv");

		IMode mode = ModeFactory.getInstance("CBC", "AES", 32);
		Map<String, Object> attributes = new HashMap<String, Object>();
		// These attributes are defined in gnu.crypto.cipher.IBlockCipher.

		attributes.put(IMode.KEY_MATERIAL, key_bytes);
		attributes.put(IMode.CIPHER_BLOCK_SIZE, new Integer(32));
		// These attributes are defined in IMode.
		attributes.put(IMode.STATE, new Integer(IMode.ENCRYPTION));
		attributes.put(IMode.IV, iv_bytes);

		mode.init(attributes);
		int bs = mode.currentBlockSize();

		// /////////
		IPad padding = PadFactory.getInstance("PKCS7");
		padding.init(bs);
		byte[] pad = padding.pad(cookieValue, 0, cookieValue.length);
		byte[] pt = new byte[cookieValue.length + pad.length];

		byte[] ct = new byte[pt.length];

		System.arraycopy(cookieValue, 0, pt, 0, cookieValue.length);
		System.arraycopy(pad, 0, pt, cookieValue.length, pad.length);
		// ///
		// encrypt text in 8-byte chunks
		for (int i = 0; i < pt.length; i += bs) {
			mode.update(pt, i, ct, i);
		}
		// ////////////////
		String encryptedString = Base64Coder.encodeLines(ct);
		// String encryptedString = HexCoder.bytesToHex(ct);
		// String encryptedString = new String(ct);
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
		byte[] key_bytes = coders.Keys.Create32byteKey_SHA256(key);
		byte[] iv_bytes = coders.Keys.Create32byteKey_SHA256("iv");

		IMode mode = ModeFactory.getInstance("CBC", "AES", 32);
		Map<String, Object> attributes = new HashMap<String, Object>();
		// These attributes are defined in gnu.crypto.cipher.IBlockCipher.

		attributes.put(IMode.KEY_MATERIAL, key_bytes);
		attributes.put(IMode.CIPHER_BLOCK_SIZE, new Integer(32));
		// These attributes are defined in IMode.
		attributes.put(IMode.STATE, new Integer(IMode.DECRYPTION));
		attributes.put(IMode.IV, iv_bytes);
		mode.init(attributes);
		int bs = mode.currentBlockSize();

		// initialize byte arrays that will hold encrypted/decrypted
		// text
		// ct = cookieValue.getBytes();
		// ct = HexCoder.hexToBytes(cookieValue);
		ct = Base64Coder.decodeLines(cookieValue);
		pt = new byte[cookieValue.length()];

		IPad padding1 = PadFactory.getInstance("PKCS7");
		padding1.init(bs);

		// Iterate over the byte arrays by 16-byte blocks and decrypt.
		for (int i = 0; i < ct.length; i += bs) {
			mode.update(ct, i, pt, i);
		}

		int unpad = 0;
		try {
			unpad = padding1.unpad(pt, 0, pt.length);
		} catch (WrongPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] output = new byte[pt.length - unpad];
		System.arraycopy(pt, 0, output, 0, output.length);

		String decryptedString = new String(output, "UTF8");
		return decryptedString.trim();
	}
	
}