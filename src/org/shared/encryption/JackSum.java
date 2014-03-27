package org.shared.encryption;
/**
 * @author Emanuel Rivera
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import org.shared.Util;

import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;

public class JackSum {
	
	private AbstractChecksum checkSum;

	
	public String md5(String mD5){
	      try {
			checkSum = JacksumAPI.getChecksumInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      checkSum.reset();
	      checkSum.setEncoding(AbstractChecksum.BIN);
	      String key=mD5;
	      byte[] keyBytes = key.getBytes();
	      checkSum.update(keyBytes);
	      String result = checkSum.getFormattedValue();

		return result;
	}
	
	/**
	 * Creates SHA hash with 512 bits
	 * @param mD5
	 * @return
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
	 * Creates SHA hash with 512 bits
	 * @param mD5
	 * @return
	 */
	public String sha256_HEX(String mD5){
	      try {
			checkSum = JacksumAPI.getChecksumInstance("sha256");
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
	 * Creates a md5 hash in HEX from string
	 * @param mD5
	 * @return
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
	 * Creates a adler32 hash in HEX from string
	 * @param strInput
	 * @return
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
	 * Creates a adler32 hash in HEX from string
	 * @param strInput
	 * @return
	 */
	public String adler32Date_HEX(){
		
		Util utilObj=new Util();
		String strInput= utilObj.getCurrentFormattedDate();
		
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
	 * Creates a crc64 hash in HEX from string
	 * @param mD5
	 * @return
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
	 * Creates a crc16 hash in HEX from string
	 * @param mD5
	 * @return
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
		
}
