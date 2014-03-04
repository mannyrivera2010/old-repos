package table.compression;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import table.coders.Base64Coder;
import table.coders.HexCoder;


public class ZipInflater implements Compression{


	public static void main(String[] args) {
		
		String plaintext = "In order to graduate, each graduate student must complete either COSC880 graduate " +
				"project or COSC897 graduate thesis. A graduate project is a 3-credit course that must be taken" +
				" by a student with a graduate faculty. The following policies and procedures must be followed by " +
				"a graduate student and must be insured by the graduate faculty.\nA graduate project (COSC880) serves " +
				"the purpose of providing applied skills to the student. That means, graduate project should be " +
				"focused on implementation and learning skills for the student. The graduate project can also be" +
				"based on survey of current topics in a given field of study and write a paper for publication. " +
				"Graduate faculty plays a role of training the student to write a proposal, analyze a problem," +
				" collect requirements for a problem, design, implement, test, and demonstrate a chosen problem.";
		
		
		//plaintext = "ThisThisThisThisThisThisThisThisThisThisThisThisThisThisThisThisThisThisThisThis";
		//
		Compression ZipCom=new ZipInflater();
		byte[] input = plaintext.getBytes();

		//System.out.println(input.length);
		//System.out.println(ZipCom.CompressDeflater(input).length);
		//System.out.println(ZipCom.DecompressDeflater(ZipCom.CompressDeflater(input)).length);
		
        //System.out.println(HexCoder.bytesToHex(ZipCom.Compress(input)).length());
		
		String encryptedString = Base64Coder.encodeLines(ZipCom.Compress(input));
		
		System.out.println(new String(input));
		System.out.println(encryptedString);

	}
	
	@Override
	public byte[] Decompress(byte[] compressedData){
		// Create the decompressor and give it the data to compress
		Inflater decompressor = new Inflater();
		decompressor.setInput(compressedData);

		// Create an expandable byte array to hold the decompressed data
		ByteArrayOutputStream bos = new ByteArrayOutputStream(compressedData.length);

		// Decompress the data
		byte[] buf = new byte[1024];
		while (!decompressor.finished()) {
		    try {
		        int count = decompressor.inflate(buf);
		        bos.write(buf, 0, count);
		    } catch (DataFormatException e) {
		    }
		}
		try {
		    bos.close();
		} catch (IOException e) {
		}

		// Get the decompressed data
		byte[] decompressedData = bos.toByteArray();
		return decompressedData;
	}
	
	
	@Override
	public byte[] Compress(byte[] decompressedData){
		// Create the compressor with highest level of compression
		Deflater compressor = new Deflater();
		compressor.setLevel(Deflater.BEST_COMPRESSION);

		// Give the compressor the data to compress
		compressor.setInput(decompressedData);
		compressor.finish();

		// Create an expandable byte array to hold the compressed data.
		// You cannot use an array that's the same size as the orginal because
		// there is no guarantee that the compressed data will be smaller than
		// the uncompressed data.
		ByteArrayOutputStream bos = new ByteArrayOutputStream(decompressedData.length);

		// Compress the data
		byte[] buf = new byte[1024];
		while (!compressor.finished()) {
		    int count = compressor.deflate(buf);
		    bos.write(buf, 0, count);
		}
		try {
		    bos.close();
		} catch (IOException e) {
		}

		// Get the compressed data
		byte[] compressedData = bos.toByteArray();

		return compressedData;
	}
	
	

}
