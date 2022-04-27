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

import compression.CompressEngineDeflat;

import coders.Base64Coder;
import coders.HexCoder;

// TODO: Auto-generated Javadoc
/**
 * The Class AES128CBCPad_Compress.
 */
public class AES128CBCPad_Compress {
	
	/**
	 * The main method.
	 *
	 * @param argv the arguments
	 * @throws Exception the exception
	 */
	public static void main(String argv[]) throws Exception {
		TEST1();
	}

	/**
	 * TES t1.
	 *
	 * @throws InvalidKeyException the invalid key exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws WrongPaddingException the wrong padding exception
	 */
	public static void TEST1() throws InvalidKeyException,
			UnsupportedEncodingException, WrongPaddingException {
		String key = "password";

		String plaintext = 
				"Manny-1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111112222313" +
				"\n1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
				"\n1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
				"\n1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
				"\n1111111111222222225555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555" +
				"\n2222222222222222222222222222222222222222222222222222223333333333333333333333555555555555555556666666666666666666666" +
				"\n55555555555555555555555555551231231234564655456111111111111111111111111111111111111111111111111111111111111" +
				"\n                                                                                                                   ";
	    //System.out.println(Integer.MAX_VALUE);
		AES128CBCPad_Compress AesEn = new AES128CBCPad_Compress();

		String cipher = AesEn.AES_encrypt(plaintext, key);
		System.out.println(cipher);
		
		String cplaintext = AesEn.AES_decrypt(cipher, key);
		System.out.println(cplaintext);
		
		//cipher = AesEn.AES_encrypt(cplaintext.getBytes("UTF8"), key);
		//System.out.println("*"+cipher+"*");
		//cplaintext = AesEn.AES_decrypt(cipher, key);
		//System.out.println("*"+cplaintext+"*");
		
		//AesEn.AES_FileEncrypt("Stephanie Meyer - Crepusculo 1.doc","password");
		//AesEn.AES_FileDecrypt("Stephanie Meyer - Crepusculo 1.doc.enc","libro.doc","password");
	}

	/**
	 * AE s_ byte encrypt.
	 *
	 * @param cookieValue the cookie value
	 * @param key the key
	 * @return the byte[]
	 * @throws InvalidKeyException the invalid key exception
	 * @throws IllegalStateException the illegal state exception
	 */
	public byte[] AES_ByteEncrypt(byte[] cookieValue,String key) throws InvalidKeyException, IllegalStateException{
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
		return ct;
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
		CompressEngineDeflat ZipCom=new CompressEngineDeflat();
		byte[] cookieValue=ZipCom.CompressDeflater(plaintext1.getBytes("UTF8"));
		//byte[] cookieValue=plaintext1.getBytes("UTF8");
		// create a key
		
		String encryptedString = Base64Coder.encodeLines(AES_ByteEncrypt(cookieValue,key));
		// String encryptedString = HexCoder.bytesToHex(ct);
		// String encryptedString = new String(ct);
		return encryptedString;
	}
	
	
	/**
	 * AE s_ file encrypt.
	 *
	 * @param FileName the file name
	 * @param key the key
	 * @throws InvalidKeyException the invalid key exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public void AES_FileEncrypt(String FileName, String key)
			throws InvalidKeyException, UnsupportedEncodingException {
		
		file.Engine FileEn = new file.Engine();
		
		CompressEngineDeflat ZipCom=new CompressEngineDeflat();
		byte[] cookieValue=ZipCom.CompressDeflater(FileEn.ReturnFileBytes(FileName));
		//byte[] cookieValue=plaintext1.getBytes("UTF8");
		// create a key
		
		byte[] encryptedbytes = AES_ByteEncrypt(cookieValue,key);
		// String encryptedString = HexCoder.bytesToHex(ct);
		// String encryptedString = new String(ct);
		FileEn.WriteBytesToFile(FileName+".enc", encryptedbytes);
	}
	
	/**
	 * AE s_ file encrypt.
	 *
	 * @param FileName the file name
	 * @param DestFileName the dest file name
	 * @param key the key
	 * @throws InvalidKeyException the invalid key exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public void AES_FileEncrypt(String FileName,String DestFileName, String key)
			throws InvalidKeyException, UnsupportedEncodingException {
		
		file.Engine FileEn = new file.Engine();
		
		CompressEngineDeflat ZipCom=new CompressEngineDeflat();
		byte[] cookieValue=ZipCom.CompressDeflater(FileEn.ReturnFileBytes(FileName));
		//byte[] cookieValue=plaintext1.getBytes("UTF8");
		// create a key
		
		byte[] encryptedbytes = AES_ByteEncrypt(cookieValue,key);
		// String encryptedString = HexCoder.bytesToHex(ct);
		// String encryptedString = new String(ct);
		FileEn.WriteBytesToFile(DestFileName, encryptedbytes);
	}

	
	/**
	 * AE s_ file decrypt.
	 *
	 * @param FileName the file name
	 * @param DestFileName the dest file name
	 * @param key the key
	 */
	public void AES_FileDecrypt(String FileName,String DestFileName, String key){
		file.Engine FileEn = new file.Engine();
	
		
		CompressEngineDeflat ZipCom=new CompressEngineDeflat();
		byte[] cookieValue = null;
		try {
			cookieValue = AES_ByteDecrypt(FileEn.ReturnFileBytes(FileName),key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
			System.err.println("************************************");
			return;
		}
		
		System.err.println("Pass1");
		//byte[] cookieValue=plaintext1.getBytes("UTF8");
		// create a key
		
		byte[] decryptedbytes = ZipCom.DecompressDeflater(cookieValue);
		// String encryptedString = HexCoder.bytesToHex(ct);
		// String encryptedString = new String(ct);
		FileEn.WriteBytesToFile(DestFileName, decryptedbytes);
	}
	
	
	/**
	 * AE s_decrypt.
	 *
	 * @param cookieValue the cookie value
	 * @param key the key
	 * @return the string
	 * @throws InvalidKeyException the invalid key exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws WrongPaddingException the wrong padding exception
	 */
	public String AES_decrypt(String cookieValue, String key)
			throws InvalidKeyException, UnsupportedEncodingException, WrongPaddingException {
		byte[] ct=Base64Coder.decodeLines(cookieValue);
		
		CompressEngineDeflat ZipCom=new CompressEngineDeflat();		
		String decryptedString= new String(ZipCom.DecompressDeflater(AES_ByteDecrypt(ct,key)), "UTF8");
		return decryptedString;
	}
	
	
	/**
	 * AE s_ byte decrypt.
	 *
	 * @param cookieValue the cookie value
	 * @param key the key
	 * @return the byte[]
	 * @throws InvalidKeyException the invalid key exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws WrongPaddingException the wrong padding exception
	 */
	public byte[] AES_ByteDecrypt(byte[] cookieValue, String key)
			throws InvalidKeyException, UnsupportedEncodingException, WrongPaddingException {
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

		// initialize byte arrays that will hold encrypted/decrypted
		// text
		// ct = cookieValue.getBytes();
		// ct = HexCoder.hexToBytes(cookieValue);
		ct = cookieValue;
		pt = new byte[cookieValue.length];

		IPad padding1 = PadFactory.getInstance("PKCS7");
		padding1.init(bs);

		// Iterate over the byte arrays by 16-byte blocks and decrypt.
		for (int i = 0; i < ct.length; i += bs) {
			mode.update(ct, i, pt, i);
		}

		int unpad = 0;
		
			unpad = padding1.unpad(pt, 0, pt.length);
	

		byte[] output = new byte[pt.length - unpad];
		System.arraycopy(pt, 0, output, 0, output.length);
		//Decompress
		return output;
	}

}