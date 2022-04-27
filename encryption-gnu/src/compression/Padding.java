package compression;

import gnu.crypto.pad.IPad;
import gnu.crypto.pad.PadFactory;
import gnu.crypto.pad.WrongPaddingException;

// TODO: Auto-generated Javadoc
/**
 * The Class Padding.
 */
public class Padding {


	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws WrongPaddingException the wrong padding exception
	 */
	public static void main(String[] args) throws WrongPaddingException {
		
		String plaintext = "In order to graduate, each graduate student must complete either COSC880 graduate " +
				"project or COSC897 graduate thesis. A graduate project is a 3-credit course that must be taken" +
				" by a student with a graduate faculty. The following policies and procedures must be followed by " +
				"a graduate student and must be insured by the graduate faculty.\nA graduate project (COSC880) serves " +
				"the purpose of providing applied skills to the student. That means, graduate project should be " +
				"focused on implementation and learning skills for the student. The graduate project can also be" +
				"based on survey of current topics in a given field of study and write a paper for publication. " +
				"Graduate faculty plays a role of training the student to write a proposal, analyze a problem," +
				" collect requirements for a problem, design, implement, test, and demonstrate a chosen problem.";

		plaintext+="\nAn updated version (Summer 2011) is nearing completion and is nearly ready for release."+
		"It is still under active development, and does not yet contain instructions for use. "+
		"It does have a number of new features, including the ability to use shapefiles natively for data import and export." +
		"I expect to release the finished product later this summer, but if you don't want to wait, you can dowload it as well." +
		"I am not yet providing a link for the source code because it is still changing on a daily basis. " +
		"If you would like it, just let me know and I will send. ";
		
		CompressEngineDeflat ZipCom=new CompressEngineDeflat();
		byte[] inputa = plaintext.getBytes();
		
		byte[] compressed=ZipCom.CompressDeflater(inputa);
	
		int blockSize=32;
		
		
		//======================
		IPad padding = PadFactory.getInstance("PKCS7");
		padding.init(blockSize);
		byte[] pad = padding.pad(compressed, 0, compressed.length);
		byte[] pt = new byte[compressed.length + pad.length];
		
		System.arraycopy(compressed, 0, pt, 0, compressed.length);
		System.arraycopy(pad, 0, pt, compressed.length, pad.length);

		
		System.out.println(compressed.length%32);
		System.out.println(pt.length%32);
		
		
		IPad padding1 = PadFactory.getInstance("PKCS7");
		padding1.init(blockSize);
		
		int unpad = padding1.unpad(pt, 0, pt.length);
		byte[] output = new byte[pt.length - unpad];
		System.arraycopy(pt, 0, output, 0, output.length);
		
		System.out.println(output.length%32);
		
		
	}

}
