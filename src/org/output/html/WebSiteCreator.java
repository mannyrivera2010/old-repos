package org.output.html;
/**
 * @author Emanuel Rivera
 */

import java.util.StringTokenizer;
import javolution.util.FastTable;

import org.shared.IniEngine;
import org.shared.Util;
import org.enums.EngineOutputEnum;
import org.output.html.HtmlGenerator;

// TODO: Auto-generated Javadoc
/**
 * The Class WebSiteCreator.
 */
public class WebSiteCreator {
	
	/** The Util shared. */
	private Util UtilShared = new Util(); //misc methods
	
	/** The html output generator. */
	private HtmlGenerator htmlOutputGenerator=new HtmlGenerator();  //for Generating HTML code

	/**
	 * Creates the index.
	 */
	public void CreateIndex(){
		IniEngine IniEngine= new IniEngine(); //For settings
		System.out.println("--------Complete Web------\n");
		String CompleteWebPath=IniEngine.getIniStringValue("Settings.ini","History_LogOutput","CompleteWebPath");
		
		UtilShared.folderExistAndMake(CompleteWebPath+"/WebFiles/");
		UtilShared.writeStringToFile(
				UtilShared.filetoString(".//resources/HtmlTemplates/MainStyle.css"),
				CompleteWebPath+"/WebFiles/MainStyle.css");
		
		UtilShared.folderCopy(".//resources/HtmlTemplates/js", CompleteWebPath+"/WebFiles/js");
		UtilShared.folderCopy(".//resources/HtmlTemplates/ui", CompleteWebPath+"/WebFiles/ui");
		UtilShared.folderCopy(".//resources/HtmlTemplates/theme", CompleteWebPath+"/WebFiles/theme");
	
		/*
		 * Index.html
		 */
		CreateHtml_IndexFile(CompleteWebPath);
		
		/*
		 * Folder Files
		 */
		CreateHtml_FolderFiles(CompleteWebPath);
		
		/*
		 * Processed Grant List
		 */
		CreateHtml_ProcessedGrantList(CompleteWebPath);
		
		/*
		 * Individual Files
		 */
		CreateHtml_IndividualFiles(CompleteWebPath);
		
		/*
		 * Grant List
		 */
		CreateHtml_GrantList_old(CompleteWebPath);
		
	}//end void CreateIndex()
 
	/**
	 * Creates the Index file of website.
	 *
	 * @param CompleteWebPath the complete web path
	 */
	public void CreateHtml_IndexFile(String CompleteWebPath){
		String strReplace=UtilShared.filetoString(".//resources/HtmlTemplates/index.html");
		String strCur = UtilShared.insertStringTemplate(strReplace, "##UpdateTime##", UtilShared.getCurrentFormattedDate());
		UtilShared.writeStringToFile(strCur,CompleteWebPath+"/index.html");
	}// void CreateHtml_IndexFile(String CompleteWebPath)
	
	/**
	 * Creates the FolderFiles page for website.
	 *
	 * @param CompleteWebPath the complete web path
	 */
	public void CreateHtml_FolderFiles(String CompleteWebPath){
		String strReplace=(String) htmlOutputGenerator.printHTMLFolderFilesHistory(EngineOutputEnum.LITEHTMLONLY).toString();
		
		String strCur=UtilShared.insertTxtTemplate(".//resources/HtmlTemplates/template2.html",
				"<!-- Start Content-->","<!-- Start Content changed-->\n\n"+strReplace);
		
		strCur=UtilShared.insertStringTemplate(strCur, "##UpdateTime##", UtilShared.getCurrentFormattedDate());
		UtilShared.writeStringToFile(strCur,CompleteWebPath+"/WebFiles/FolderFiles.html");
	}//void CreateHtml_FolderFiles(String CompleteWebPath)
	
	/**
	 * Creates the ProcessedGrantList page for website.
	 *
	 * @param CompleteWebPath the complete web path
	 */
	public void CreateHtml_ProcessedGrantList(String CompleteWebPath){
		String strReplace=(String) htmlOutputGenerator.printHTMLProcessedGrantList(EngineOutputEnum.LITEHTMLONLY).toString();
		String strCur=UtilShared.insertTxtTemplate(".//resources/HtmlTemplates/template2.html",
				"<!-- Start Content-->","<!-- Start Content changed-->\n\n"+strReplace);
		strCur=UtilShared.insertStringTemplate(strCur, "##UpdateTime##", UtilShared.getCurrentFormattedDate());
		UtilShared.writeStringToFile(strCur,CompleteWebPath+"/WebFiles/Processed.html");
	}//void CreateHtml_ProcessedGrantList(String CompleteWebPath)
	
	/**
	 * Creates the IndividualFiles page for website.
	 *
	 * @param CompleteWebPath the complete web path
	 */
	public void CreateHtml_IndividualFiles(String CompleteWebPath){
		String strReplace=(String) htmlOutputGenerator.printHTMLIndividualFilesHistory(EngineOutputEnum.LITEHTMLONLY).toString();
		String strCur=UtilShared.insertTxtTemplate(".//resources/HtmlTemplates/template2.html",
				"<!-- Start Content-->","<!-- Start Content changed-->\n\n"+strReplace);
		strCur=UtilShared.insertStringTemplate(strCur, "##UpdateTime##", UtilShared.getCurrentFormattedDate());
		UtilShared.writeStringToFile(strCur,CompleteWebPath+"/WebFiles/IndividualFiles.html");
	}//void CreateHtml_IndividualFiles(String CompleteWebPath)
	
	/**
	 * Creates the GrantList (old) page for website.
	 *
	 * @param CompleteWebPath the complete web path
	 */
	public void CreateHtml_GrantList_old(String CompleteWebPath){
		UtilShared.folderDelete(CompleteWebPath+"/WebFiles/GrantList_Files");
		@SuppressWarnings("unchecked")
		FastTable<Object> arrayTemp= (FastTable<Object>) htmlOutputGenerator.printHTMLGrantListHistoryOLD(EngineOutputEnum.OBJECTFASTTABLE);
		
		String strReplace=(String) arrayTemp.get(0).toString();
		String strCur=UtilShared.insertTxtTemplate(".//resources/HtmlTemplates/template2.html",
				"<!-- Start Content-->","<!-- Start Content changed-->\n\n"+strReplace);
		strCur=UtilShared.insertStringTemplate(strCur, "##UpdateTime##", UtilShared.getCurrentFormattedDate());
		UtilShared.writeStringToFile(strCur,CompleteWebPath+"/WebFiles/GrantList.html");
		UtilShared.folderExistAndMake(CompleteWebPath+"/WebFiles/GrantList"+"_Files");
		
		@SuppressWarnings("unchecked")
		FastTable<Object> SecondLevelArray = (FastTable<Object>) arrayTemp.get(1);
		
		for(int i=0;i<SecondLevelArray.size();i++){
			StringTokenizer sTokFin = new StringTokenizer((String) SecondLevelArray.get(i), "|*|");
			String strHashedName = sTokFin.nextToken()+".html";
			String strContent = sTokFin.nextToken();
			
			strCur=UtilShared.insertTxtTemplate(".//resources/HtmlTemplates/template3.html",
					"<!-- Start Content-->","<!-- Start Content changed-->\n\n"+strContent);
			strCur=UtilShared.insertStringTemplate(strCur, "##UpdateTime##", UtilShared.getCurrentFormattedDate());

			UtilShared.writeStringToFile(strCur,CompleteWebPath+"/WebFiles/GrantList_Files/"+strHashedName);		
			//System.out.println(strHashedName);
		}
	}//CreateHtml_GrantList_old(String CompleteWebPath)
	
	// /////////////////////////////
	// ***************************//
	// /////////////////////////////
	
	/**
	 * Make html folder files history.
	 *
	 * @return the string
	 */
	public String makeHTMLFolderFilesHistory() {
		IniEngine IniEngine= new IniEngine(); //For settings
		String Output=(String) htmlOutputGenerator.printHTMLFolderFilesHistory(EngineOutputEnum.FULLHTMLONLY);
		String FolderFilesPath = IniEngine.getIniStringValue("Settings.ini",
				"History_LogOutput", "FolderFilesPath");
		
		// FilePath*****
		String strFileName = "FolderFiles.html";
		if (FolderFilesPath.trim().length() >= 1) {// if Something then
			// Replace
			strFileName = FolderFilesPath;
		}
		// End FilePath*****
		
		System.out.println("Write:" + strFileName);
		UtilShared.writeStringToFile(Output, strFileName);
		return Output;
	}// end  String makeHTMLFolderFilesHistory() 

	// /////////////////////////////
	// ***************************//
	// /////////////////////////////
	
	/**
	 * Make html processed grant list.
	 */
	public void makeHTMLProcessedGrantList() {
		IniEngine IniEngine= new IniEngine(); //For settings
		String Output=(String) htmlOutputGenerator.printHTMLProcessedGrantList(EngineOutputEnum.FULLHTMLONLY);
		String IndividualFilesPath=IniEngine.getIniStringValue("Settings.ini","History_LogOutput","ProcessedGrantList");

		// FilePath*****
		String strFileName = "ProcessedGrantList.html";
		if (IndividualFilesPath.trim().length() >= 1) {// if Something then
			// Replace
			strFileName = IndividualFilesPath;
		}
		// End FilePath*****
		System.out.println("Write:"+strFileName);
		UtilShared.writeStringToFile(Output, strFileName);
	}//end void makeHTMLProcessedGrantList()

	// /////////////////////////////
	// ***************************//
	// /////////////////////////////
	
	/**
	 * Make html individual files history.
	 */
	public void makeHTMLIndividualFilesHistory() {
		IniEngine IniEngine= new IniEngine(); //For settings
		String Output= (String) htmlOutputGenerator.printHTMLIndividualFilesHistory(EngineOutputEnum.FULLHTMLONLY);
		String IndividualFilesPath=IniEngine.getIniStringValue("Settings.ini","History_LogOutput","IndividualFilesPath");

		// FilePath*****
		String strFileName = "IndividualFiles.html";
		if (IndividualFilesPath.trim().length() >= 1) {// if Something then
			// Replace
			strFileName = IndividualFilesPath;
		}
		// End FilePath*****
		
		System.out.println("Write:"+strFileName);
		UtilShared.writeStringToFile(Output, strFileName);
	}// void makeHTMLIndividualFilesHistory()

	// /////////////////////////////
	// ***************************//
	// /////////////////////////////
	

}
