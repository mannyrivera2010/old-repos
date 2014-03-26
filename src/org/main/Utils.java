/*
 * Project 1  COSC 600 Fall 2012
 * Due: Sept 20, 2012
 * Author: Emanuel Rivera
 */

package org.main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Utils {
	
	public static ArrayList<String> filetoArrayList(String StrFile){
		ArrayList<String> Output= new ArrayList<String>();
		try {// Main
			String StrFILENAME = StrFile;
			FileInputStream fstream = new FileInputStream(StrFILENAME);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				String CurrentLine=strLine.trim();
				if(CurrentLine.length()==0){
					//Empty Line
				}else{
					Output.add(CurrentLine);
				}
				
			}
			in.close(); // Close the input stream
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}// End Main
		return Output;
	}//end fileto1dFastTable()
	
	/**
	 * Method is used to write to file
	 * @param Results
	 * @param Path
	 */
	public static void writeStringToFile(String Results,String Path){
		writeStringToFile(Results,Path,false);
	}//end void

	/**
	 * Method is used to write to file. Supports another method
	 * @param Results
	 * @param Path
	 * @param append
	 */
	public static void writeStringToFile(String Results,String Path,boolean append){
	
		try {
			FileWriter fstream = new FileWriter(Path, append);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(Results);
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}//end void

}
