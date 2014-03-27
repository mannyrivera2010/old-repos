package org.shared.encryption;

// TODO: Auto-generated Javadoc
/**
 * The Class JackSumTest.
 */
public class JackSumTest {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		JackSum JackSumEngine= new JackSum();
		System.out.println("md5="+JackSumEngine.md5_HEX("test"));
		System.out.println("crc16="+JackSumEngine.crc16_HEX("test"));
		System.out.println("Sha512="+JackSumEngine.sha512_HEX("test"));


		
	
	}
}
