package org.shared.encryption;
/**
 * @author Emanuel Rivera
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;

// TODO: Auto-generated Javadoc
/**
 * The Class JackSum.
 */
public class JackSum {
	
	/** The check sum. */
	private AbstractChecksum checkSum;

	/**
	 * Creates SHA hash with 512 bits.
	 *
	 * @param mD5 the m d5
	 * @return the string
	 */
	public String sha512_HEX(String mD5){
	      try {
			checkSum = JacksumAPI.getChecksumInstance("sha512");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      checkSum.reset();
	      checkSum.setEncoding(AbstractChecksum.HEX);
	      
	      String key=mD5;
	      byte[] keyBytes = key.getBytes();
	      checkSum.update(keyBytes);
	      String result = checkSum.getFormattedValue();

		return result;
	}
	
	/**
	 * Creates a md5 hash in HEX from string.
	 *
	 * @param mD5 the m d5
	 * @return the string
	 */
	public String md5_HEX(String mD5){
	      try {
			checkSum = JacksumAPI.getChecksumInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      checkSum.reset();
	      checkSum.setEncoding(AbstractChecksum.HEX);
	      
	      String key=mD5;
	      byte[] keyBytes = key.getBytes();
	      checkSum.update(keyBytes);
	      String result = checkSum.getFormattedValue();

		return result;
	}
	
	/**
	 * Creates a md5 hash in HEX from file.
	 *
	 * @param StrFileName the str file name
	 * @return the string
	 */
	public String mD5File_HEX(String StrFileName){
	      try {
			checkSum = JacksumAPI.getChecksumInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      checkSum.reset();
	      checkSum.setEncoding(AbstractChecksum.HEX);
	      
	      
	      byte[] keyBytes = null;
		try {
			keyBytes = getBytesFromFile(StrFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      checkSum.update(keyBytes);
	      String result = checkSum.getFormattedValue();
	    
		return result;
	}
	

	/**
	 * Creates a adler32 hash in HEX from string.
	 *
	 * @param strInput the str input
	 * @return the string
	 */
	public String adler32_HEX(String strInput){
	      try {
			checkSum = JacksumAPI.getChecksumInstance("adler32");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      checkSum.reset();
	      checkSum.setEncoding(AbstractChecksum.HEX);
	      String key=strInput;
	      byte[] keyBytes = key.getBytes();
	      checkSum.update(keyBytes);
	      String result = checkSum.getFormattedValue();

		return result;
	}
	
	
	/**
	 * Creates a crc64 hash in HEX from string.
	 *
	 * @param mD5 the m d5
	 * @return the string
	 */
	public String crc64_HEX(String mD5){
	      try {
			checkSum = JacksumAPI.getChecksumInstance("crc64");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      checkSum.reset();
	      checkSum.setEncoding(AbstractChecksum.HEX);
	      String key=mD5;
	      byte[] keyBytes = key.getBytes();
	      checkSum.update(keyBytes);
	      String result = checkSum.getFormattedValue();

		return result;
	}
	
	/**
	 * Creates a crc16 hash in HEX from string.
	 *
	 * @param mD5 the m d5
	 * @return the string
	 */
	public String crc16_HEX(String mD5){
	      try {
			checkSum = JacksumAPI.getChecksumInstance("crc16");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      checkSum.reset();
	      checkSum.setEncoding(AbstractChecksum.HEX);
	      String key=mD5;
	      byte[] keyBytes = key.getBytes();
	      checkSum.update(keyBytes);
	      String result = checkSum.getFormattedValue();

		return result;
	}
	
	
	/**
	 * Get byte array from File.
	 *
	 * @param strFileName the str file name
	 * @return the bytes from file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public byte[] getBytesFromFile(String strFileName) throws IOException {
		File file = new File(strFileName);
	    InputStream is = new FileInputStream(file);

	    // Get the size of the file
	    long length = file.length();

	    // You cannot create an array using a long type.
	    // It needs to be an int type.
	    // Before converting to an int type, check
	    // to ensure that file is not larger than Integer.MAX_VALUE.
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }

	    // Create the byte array to hold the data
	    byte[] bytes = new byte[(int)length];

	    // Read in the bytes
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    // Ensure all the bytes have been read in
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    // Close the input stream and return bytes
	    is.close();
	    return bytes;
	}
	
}
