package org.shared.encryption;

import org.shared.encryption.Encoder;
// TODO: Auto-generated Javadoc

/**
 * The Class EncoderTest.
 */
public class EncoderTest {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		String strOrginalString="pass";
		System.out.println("Orginal String="+strOrginalString);
		String Encoded= Encoder.Encode(strOrginalString);
		System.out.println("Encoded String="+Encoded);
		
		String decodeText="Decoded String="+Encoder.Decode(Encoded);
		System.out.println(decodeText);		
	}
}
