package file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import compression.CompressEngineDeflat;

// TODO: Auto-generated Javadoc
/**
 * The Class Engine.
 */
public class Engine {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Engine Newas=new Engine();
		//Newas.DeCompressFile("nea.txt","read1.txt");
	}
	
	/**
	 * Compress file.
	 *
	 * @param FiletoCompress the fileto compress
	 * @param Dest the dest
	 */
	public void CompressFile(String FiletoCompress,String Dest){
		
		CompressEngineDeflat ZipCom=new CompressEngineDeflat();

		byte[]FileContent=ReturnFileBytes(FiletoCompress);
		
		//System.out.println(FileContent.length);
		WriteBytesToFile(Dest,ZipCom.CompressDeflater(FileContent));
		
	}
	
	/**
	 * De compress file.
	 *
	 * @param FiletoCompress the fileto compress
	 * @param Dest the dest
	 */
	public void DeCompressFile(String FiletoCompress,String Dest){
		
		CompressEngineDeflat ZipCom=new CompressEngineDeflat();

		byte[]FileContent=ReturnFileBytes(FiletoCompress);
		
		//System.out.println(FileContent.length);
		WriteBytesToFile(Dest,ZipCom.DecompressDeflater(FileContent));
		
	}
	
	
	/**
	 * Write bytes to file.
	 *
	 * @param FileName the file name
	 * @param Content the content
	 */
	public void WriteBytesToFile(String FileName,byte[] Content){
		String strFileName = FileName;
		BufferedOutputStream bos = null;

		try {
			// create an object of FileOutputStream
			FileOutputStream fos = new FileOutputStream(new File(strFileName));

			// create an object of BufferedOutputStream
			bos = new BufferedOutputStream(fos);

			/*
			 * To write byte array to file use, public void write(byte[] b)
			 * method of BufferedOutputStream class.
			 */
	
			bos.write(Content);

			System.out.println("File written");
		} catch (FileNotFoundException fnfe) {
			System.out.println("Specified file not found" + fnfe);
		} catch (IOException ioe) {
			System.out.println("Error while writing file" + ioe);
		} finally {
			if (bos != null) {
				try {

					// flush the BufferedOutputStream
					bos.flush();

					// close the BufferedOutputStream
					bos.close();

				} catch (Exception e) {
				}
			}
		}
	
	}
	
	
	/**
	 * Return file bytes.
	 *
	 * @param FileName the file name
	 * @return the byte[]
	 */
	public byte[] ReturnFileBytes(String FileName){
		byte[] fileBytes=null;
		
		try {
			fileBytes=ReturnFileBytesX(FileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileBytes;
	}
	
	/**
	 * Return file bytes x.
	 *
	 * @param FileName the file name
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private byte[] ReturnFileBytesX(String FileName) throws IOException{
		File file = new File(FileName);

	
			// create FileInputStream object
			FileInputStream fin = new FileInputStream(file);
			/*
			 * Create byte array large enough to hold the content of the file.
			 * Use File.length to determine size of the file in bytes.
			 */

			byte fileContent[] = new byte[(int) file.length()];

			/*
			 * To read content of the file in byte array, use int read(byte[]
			 * byteArray) method of java FileInputStream class.
			 */
			fin.read(fileContent);

			// create string from byte array
		return fileContent;
	}

}
