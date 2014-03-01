package hash;

import hash.AbstractChecksum;
import hash.JacksumAPI;

import java.security.NoSuchAlgorithmException;



// TODO: Auto-generated Javadoc
/**
 * The Class engine.
 */
public class engine {

	/** The check sum. */
	private static AbstractChecksum checkSum;
	
	/**
	 * Md5.
	 *
	 * @param mD5 the m d5
	 * @return the string
	 */
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
	 * SH a256.
	 *
	 * @param inputa the inputa
	 * @return the string
	 */
	public String SHA256(String inputa){
	      try {
			checkSum = JacksumAPI.getChecksumInstance("sha256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      checkSum.reset();
	      checkSum.setEncoding(AbstractChecksum.BIN);
	      String key=inputa;
	      byte[] keyBytes = key.getBytes();
	      checkSum.update(keyBytes);
	      String result = checkSum.getFormattedValue();

		return result;
	}
	
	


}
