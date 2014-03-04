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

public class JackSumEngine {
	private AbstractChecksum checkSum;
		
	public static void main(String[] args) {
		
		//System.out.println("md5="+JackSum.md5_HEX("hel11l8"+"Round1"+"1"));
		//System.out.println("crc64="+JackSum.adler32_HEX("hel21l1"+"Round1"+"1"));
		//System.out.println("md5file="+JackSum.md5File_HEX("log.txt"));
		
	
	}
		
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
