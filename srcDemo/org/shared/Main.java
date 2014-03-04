package org.shared;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.list.DirListI;
import org.list.DirListXML;
import org.list.DirListQueue;


public class Main {

	static Scanner scanIn = new Scanner(System.in);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		mainProgram();

	}

	private static void mainProgram() {
		System.out.println("Welcome to the Directory Comparision and Snapshot Maker");
		System.out.println("=======================================================");
		boolean blnContinue=true;
		while(blnContinue){
			System.out.println("Choices:");
			System.out.println("1)Make snapshot of Directory (using DirListXML)");
			System.out.println("2)Make snapshot of Directory (using DirListXMLQueue)");
			System.out.println("3)Make snapshot of Directory (using DirListTXTQueue)");
			System.out.println("10)Compare Two Directory Snapshot (using DirListXML)");
			System.out.println("11)Compare Two Directory Snapshot (using DirListTXT)");
			System.out.println("e)Exit");
			
			
			String Choice= scanIn.nextLine().trim();
	
			if (Choice.equals("1")){
				makeSnapshot("DirListXML");
			}else if(Choice.equals("2")){
				makeSnapshot("DirListXMLQueue");
			}else if(Choice.equals("3")){
				makeSnapshot("DirListTXTQueue");
			}else if(Choice.equals("10")){
				compareSnapshots("XML");
			}else if(Choice.equals("11")){
				compareSnapshots("TXT");
			}else if(Choice.equals("e")){
				blnContinue=false;
			}		
		}
	
		System.out.println("DONE");
		scanIn.close();
	}

	
	
	
	public static void makeSnapshot(String Choice){
		System.out.println("Input Directory:");
		String inputDirectory= scanIn.nextLine().trim();
		
		if(new File(inputDirectory).isDirectory()==true){
			//move along
		}else{
			System.out.println("Not Valid Directory, Try Again");
			return;
		}
		
		System.out.println("output file (*.xml or *.txt) type whole name with ext:");
		String outputXml= scanIn.nextLine().trim();
		
		DirListI DirListObj1 = null;
		
		if(Choice.equals("DirListXML")){
			System.out.println("Using DirListXML");
			DirListObj1= new DirListXML();
		}else if(Choice.equals("DirListXMLQueue")){
			System.out.println("Using DirListXMLQueue");
			DirListObj1= new DirListQueue();
		}else if(Choice.equals("DirListTXTQueue")){
			System.out.println("Using DirListTXTQueue");
			DirListObj1= new DirListQueue();
		}
		
		System.out.println("Starting to Get list of Files");
		
		try {
			System.out.println("Working");
			DirListObj1.loadDir(inputDirectory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Not Valid Directory, Try Again (DirListObj1 Error)");
			return;
		}
		System.out.println("Done Getting List of Files");
		System.out.println("Saving List to File");
		if(Choice.contains("XML")){
			DirListObj1.saveXML(outputXml);
		}else if(Choice.contains("TXT")){
			DirListObj1.saveTXT(outputXml);
		}
		
		System.out.println("Done Saving List to File");
	}

	
	public static void compareSnapshots(String Choice){
		
		System.out.println("Input Snapshot File 1:");
		String inputXML1= scanIn.nextLine().trim();
		
		System.out.println("Input XML Snapshot Directory 2:");
		String inputXML2= scanIn.nextLine().trim();
		
		if(new File(inputXML1).isFile()==true&&new File(inputXML2).isFile()==true){
			//move along
		}else{
			System.out.println("Not Valid File, Try Again");
			return;
		}
		
		
		System.out.println("Loading Directory Snapshot");
		
		DirListI DirListObj1 = null;
		DirListI DirListObj2 = null;
		
		
		if(Choice.contains("XML")){
			DirListObj1= new DirListXML();
			DirListObj2= new DirListXML();
			
			DirListObj1.loadXML(inputXML1);
			DirListObj2.loadXML(inputXML2);
		}else if(Choice.contains("TXT")){
			DirListObj1= new DirListQueue();
			DirListObj2= new DirListQueue();
			
			DirListObj1.loadTXT(inputXML1);
			DirListObj2.loadTXT(inputXML2);
		}
		
		DirChanges DirCompareObj=new DirChanges(DirListObj1,DirListObj2);
		
		System.out.println("Enter Output text report file");
		String outputReportFile= scanIn.nextLine().trim();
		
		System.out.println("Saving and Generating report...");
		File file = new File(outputReportFile);
		try {
			FileUtils.writeStringToFile(file, DirCompareObj.CompareAndReport());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Done Writing Report to File");
		System.out.println("==================================");
		
	}
	
	
	
	public static void demo(){
		DirListXML DirListObj1= new DirListXML();
		DirListXML DirListObj2= new DirListXML();
		
		DirListObj1.loadXML("Test 1-19-2013.xml");
		DirListObj2.loadXML("Test 1-19-2013 After.xml");
		
		
		DirChanges DirCompareObj=new DirChanges(DirListObj1,DirListObj2);
		System.out.println(DirCompareObj.CompareAndReport());
		
		
	}
}
