package org.output.html;
/**
 * @author Emanuel Rivera
 */

import javolution.text.TextBuilder; //StringBuilder Equivalent
import javolution.util.FastTable; //ArrayList Equivalent

import org.database.DatabaseFactory;
import org.enums.DatabaseEngineEnum;
import org.enums.EngineOutputEnum;
import org.shared.StringTable;

import org.shared.Util;
import org.shared.encryption.JackSum;

import org.shared.performance.Timing;

// TODO: Auto-generated Javadoc
/**
 * The Class HtmlGenerator.
 */
public class HtmlGenerator {
	
	/** The Data base choice. */
	private DatabaseEngineEnum DataBaseChoice= DatabaseEngineEnum.SQLITE4WRAPPER;
	
	/** The Util shared. */
	private Util UtilShared = new Util();
	
	/** The Jack sum engine. */
	private JackSum JackSumEngine=new JackSum();
	
	/**
	 * ***************************************
	 * printHTMLIndividualFilesHistory.
	 *
	 * @param Total the total
	 * @return the object
	 * ***************************************
	 */
	
	public Object printHTMLGrantList(EngineOutputEnum Total) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// *********************************************
		TextBuilder Out = new TextBuilder("");
		DatabaseFactory Database = new DatabaseFactory(DataBaseChoice);
		
		if (Total == EngineOutputEnum.FULLHTMLONLY) 
			Out.append("<html>\n\t<head>\n\t\t<title>Grant List</title>\n\t</head>\n");
		
		StringTable UniqueFiles=Database.data.getGrantList_DISTINCT_GrantID();
		//System.out.println(UniqueFiles);

		for (int i = 0; i < UniqueFiles.getArrLRowsSize(); i++) {// Loop
			String strOuter = UniqueFiles.getColumnValue("GrantSubFolder", i);
			String GrantID = UniqueFiles.getColumnValue("GrantID", i);
			// System.out.println(strOuter);

			Out.append("<h3><a href=\"#\"  width=\"100%\">" + strOuter + " (ID:"+GrantID+")</a></h3>\n<div>\n");
			// System.out.println("Directory: "+StrCurDir);
			Out.append("\t<table border=\"1\">\n\t\t<tr><th>Date</th><th>Grant</th></tr>\n");
			
			StringTable GrantSubFolderFullListWhere = Database.data
					.getGrantList_DISTINCT_Date_GrantSubFolderFullWhere(GrantID);
			
			for (int j = 0; j < GrantSubFolderFullListWhere.getArrLRowsSize(); j++) {

				String StrDate = GrantSubFolderFullListWhere
						.getColumnValue("Date", j);
				String StrGrantFolderFull = GrantSubFolderFullListWhere
						.getColumnValue("WorkingDirGrantFolderFull", j);
		
				Out.append("\t\t<tr>" + "<td>" + UtilShared.formatDate(StrDate) + "</td>" + "<td>"
						+ StrGrantFolderFull + "</td>" 
						+ "</tr>\n");
				
				
			}
			Out.append("\t</table>\n</div>\n");
		}// end Out

		if (Total == EngineOutputEnum.FULLHTMLONLY) 
			Out.append("</html>");

		// System.out.println(Out);
		// *********************************************
		System.out.println("printHTMLIndividualFilesHistory="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Out.toString();
	}// end sub
	
	
	/**
	 * ***************************************
	 * printHTMLIndividualFilesHistory.
	 *
	 * @param Total the total
	 * @return the object
	 * ***************************************
	 */
	
	public Object printHTMLIndividualFilesHistory(EngineOutputEnum Total) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// *********************************************
		TextBuilder Out = new TextBuilder("");
		DatabaseFactory Database = new DatabaseFactory(DataBaseChoice);
		
		if (Total == EngineOutputEnum.FULLHTMLONLY) 
			Out.append("<html>\n<head><title>Individual Files</title></head>\n");
		
		StringTable UniqueFiles=Database.data.getIndividualFiles_DISTINCT_FileName();
		//System.out.println(UniqueFiles);

		for (int i = 0; i < UniqueFiles.getArrLRowsSize(); i++) {// Loop
			String strOuter = UniqueFiles.getColumnValue("FileName", i);
			// System.out.println(strOuter);

			Out.append("<h3><a href=\"#\"  width=\"100%\">" + strOuter + "</a></h3>\n<div>\n");
			// System.out.println("Directory: "+StrCurDir);
			Out.append("<table border=\"1\">\n<tr><th>Date</th><th>Action</th><th>File</th><th>Signature</th><th>ID</th></tr>\n");
			
			StringTable IndividualFilesFullListWhere = Database.data
					.getIndividualFiles_Date_Action_FileName_FileNameSig_Where(strOuter);
			
			for (int j = 0; j < IndividualFilesFullListWhere.getArrLRowsSize(); j++) {

				String StrDate = IndividualFilesFullListWhere
						.getColumnValue("Date", j);
				String StrAction = IndividualFilesFullListWhere
						.getColumnValue("Action", j);
				String StrFile = IndividualFilesFullListWhere
						.getColumnValue("File", j);
				String StrFileNameSig = IndividualFilesFullListWhere
						.getColumnValue("FileNameSig", j);
				String StrGrantID = IndividualFilesFullListWhere
						.getColumnValue("GrantID", j);
		
				Out.append("<tr>" + "<td>" + UtilShared.formatDate(StrDate) + "</td>" + "<td>"
						+ StrAction + "</td>" + "<td>" + StrFile
						+ "</td>" + 
						"<td>" + JackSumEngine.crc16_HEX(StrFileNameSig) + "</td>" + 
						"<td>" + StrGrantID + "</td>"+ "</tr>\n");
			}
			Out.append("</table>\n</div>\n");
		}// end Out

		if (Total == EngineOutputEnum.FULLHTMLONLY) 
			Out.append("</html>");

		// System.out.println(Out);
		// *********************************************
		System.out.println("printHTMLIndividualFilesHistory="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Out.toString();
	}// end sub

	/**
	 * ***************************************
	 * printHTMLFolderFilesHistory
	 * ***************************************.
	 *
	 * @param Total the total
	 * @return the object
	 */
	
	public Object printHTMLFolderFilesHistory(EngineOutputEnum Total) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// *********************************************
		TextBuilder Out = new TextBuilder("");
		DatabaseFactory Database = new DatabaseFactory(DataBaseChoice);
		
		StringTable UniqueWorkingDir=Database.data.getFolderFiles_DISTINCT_WorkingDir();
		
		if (Total == EngineOutputEnum.FULLHTMLONLY) 
			Out.append("<html>\n<head><title>Individual Files</title></head>\n");

		for (int i = 0; i < UniqueWorkingDir.getArrLRowsSize(); i++) {// Loop
			String strOuter = UniqueWorkingDir.getColumnValue("WorkingDir", i);
			// System.out.println(strOuter);

			Out.append("<h3><a href=\"#\">" + strOuter + "</a></h3>\n<div>\n");
			// System.out.println("Directory: "+StrCurDir);
			Out.append("<table border=\"1\" width=\"100%\">\n<tr><th>Date</th><th>Action</th><th>File</th><th>Signature</th><th>ID</th></tr>\n");

			StringTable FolderFilesFullListWhere = Database.data.
					getFolderFiles_Date_Action_File_FileNameSig_Where(strOuter);

			for (int j = 0; j < FolderFilesFullListWhere.getArrLRowsSize(); j++) {
				String StrDate = FolderFilesFullListWhere
						.getColumnValue("Date", j);
				String StrAction = FolderFilesFullListWhere
						.getColumnValue("Action", j);
				String StrFile = FolderFilesFullListWhere
						.getColumnValue("File", j);
				String StrFileNameSig = FolderFilesFullListWhere
						.getColumnValue("FileNameSig", j);
				String StrGrantID = FolderFilesFullListWhere
						.getColumnValue("GrantID", j);
				
				Out.append("\t<tr>" + "<td>" + UtilShared.formatDate(StrDate).trim() + "</td>" + "<td>"
						+ StrAction + "</td>" + "<td>" + StrFile + "</td>" + 
						"<td>" + JackSumEngine.crc16_HEX(StrFileNameSig) + "</td>" + 
						"<td>" + StrGrantID + "</td>"+ "</tr>\n");
			}
			Out.append("</table>\n</div>\n");
		}// end Out

		if (Total == EngineOutputEnum.FULLHTMLONLY) 
			Out.append("</html>");
		
		// *********************************************
		System.out.println("printHTMLFolderFilesHistory="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Out;
	}// end printHTMLFolderFilesHistory

	/**
	 * ***************************************
	 * printHTMLGrantListHistory
	 * ***************************************.
	 *
	 * @param Total the total
	 * @return the object
	 */

	public Object printHTMLGrantListHistoryOLD(EngineOutputEnum Total) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// *********************************************
		TextBuilder Out = new TextBuilder("");
		DatabaseFactory Database = new DatabaseFactory(DataBaseChoice);
		
		FastTable<Object> FastTableObject = new FastTable<Object>();
		FastTable<String> SecondLevelArray= new FastTable<String>();

		StringTable UniqueGrants= Database.data.getGrantListOLD_DISTINCT_GrantSubFolder();

		if (Total == EngineOutputEnum.FULLHTMLONLY) 
			Out.append("<html>\n<head><meta http-equiv=\"refresh\" content=\"20\"><title>GrantList</title></head>\n");
		
		String strFolderProsName="";

		// This FastTable is 'Events Directory'
		for (int i = 0; i < UniqueGrants.getArrLRowsSize(); i++) { 
			String strUniqueGrantSubFolderFull = UniqueGrants.getColumnValue("GrantSubFolder", i);
			
			Out.append("<h3><a href=\"#\">" + strUniqueGrantSubFolderFull + "</a></h3>\n<div>\n");
			//System.out.println("Directory: "+strUniqueGrantSubFolderFullFastTable);
			Out.append("<table border=\"1\"  width=\"100%\"><tr><th>Date</th><th>Grant Location</th></tr>\n");

			StringTable UniqueGrantsDateFile= Database.data.getGrantListOLD_DISTINCT_Date_GrantSubFolderFullWhere(strUniqueGrantSubFolderFull);
			//System.out.println(UniqueGrantsDateFile.toString());
			
			for (int j = 0; j < UniqueGrantsDateFile.getArrLRowsSize(); j++) { // This FastTable is
				String StrDate=UniqueGrantsDateFile.getColumnValue("Date",j);
				String StrGrantSubFolderFull=UniqueGrantsDateFile.getColumnValue("GrantSubFolderFull",j);
				String StrWorkingDirGrantFolderFull=UniqueGrantsDateFile.getColumnValue("WorkingDirGrantFolderFull",j);
				
				if (Total == EngineOutputEnum.OBJECTFASTTABLE){
					strFolderProsName="GrantList";
				}

				String HashedFileName=JackSumEngine.md5_HEX(StrDate+"-"+StrGrantSubFolderFull);

				//Out.append(("<tr>"+"<td>"+StrDate+"</td>"+"<td>" + StrDirFile.substring(0, StrDirFile.length()-StrDira.length()))+ "</td></tr>\n";
				Out.append(("<tr>"+"<td>"+UtilShared.formatDate(StrDate)+"</td>"+"<td><a href=\""+
						strFolderProsName+"_Files/"+HashedFileName+".html\">"+StrWorkingDirGrantFolderFull+"</a>")+ "</td></tr>\n");

				TextBuilder OutSub= new TextBuilder("");

				if (Total == EngineOutputEnum.FULLHTMLONLY) {
					OutSub.append("<html>\n<head><title>Folder Grant History</title></head>\n");
				}
				OutSub.append("<h3>" + StrGrantSubFolderFull + "</h3>\n");
				OutSub.append("<table border=\"1\"  width=\"95%\"><tr><th>Date</th><th>Action</th><th>File</th><th>Signature</th></tr>\n");

				StringTable UniqueGrantsFileDetails= Database.data.getGrantList_Date_Action_File_FileNameSig_Where(StrDate,StrGrantSubFolderFull);

				for (int ja = 0; ja < UniqueGrantsFileDetails.getArrLRowsSize(); ja++) { // This FastTable is
					String StrDateInner = UniqueGrantsFileDetails.getColumnValue("Date", ja);
					String StrActionInner=UniqueGrantsFileDetails.getColumnValue("Action", ja);
					String StrFileInner=UniqueGrantsFileDetails.getColumnValue("File", ja);
					String StrFileNameSig=UniqueGrantsFileDetails.getColumnValue("FileNameSig", ja);

					OutSub.append("<tr><td>"+UtilShared.formatDate(StrDateInner)+"</td>" +
							"<td>"+StrActionInner+"</td>" +
							"<td>"+StrFileInner+"</td>" +
							"<td>"+JackSumEngine.crc16_HEX(StrFileNameSig)+"</td>"+
							"</tr>\n");	
				}//end loop

				OutSub.append("</table>\n");

				if (Total == EngineOutputEnum.FULLHTMLONLY) 
					OutSub.append("\n</html>");
				
				
				if (Total == EngineOutputEnum.OBJECTFASTTABLE)
					SecondLevelArray.add(HashedFileName+"|*|"+OutSub.toString());
				
				//System.err.println("File>>"+strFolderProsName+"_Files\\"+HashedFileName+".html");		
			}// end This FastTable is Current Live Log
			Out.append("</table>\n</div>\n");
		}// end This FastTable is 'Events Directory'

		if (Total == EngineOutputEnum.FULLHTMLONLY) 
			Out.append("\n</html>");
		

		// *********************************************
		
		System.out.println("printHTMLGrantListHistory="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		if (Total == EngineOutputEnum.OBJECTFASTTABLE){
			FastTableObject.add(Out);
			FastTableObject.add(SecondLevelArray);
			//System.out.println("\n\n\n\n***********************\n\n\n\n******\n\n");
			return FastTableObject;
		}else{
			return Out;
		}//Return Out (LiteHtml,FullHtml) or ObjectFastTable 		
	}// end printHTMLGrantListHistory

	/**
	 * ***************************************
	 * printHTMLProcessedGrantList
	 * ***************************************.
	 *
	 * @param Total the total
	 * @return the object
	 */

	public Object printHTMLProcessedGrantList(EngineOutputEnum Total) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// *********************************************
		TextBuilder Out = new TextBuilder("");
		DatabaseFactory Database = new DatabaseFactory(DataBaseChoice);
		StringTable ProcessedGrants = Database.data.getFinishedGrants();
		//System.out.println(ProcessedGrants);

		if (Total == EngineOutputEnum.FULLHTMLONLY) 
			Out.append("<html>\n<head><title>GrantList</title></head>\n");

		Out.append("<table border=\"1\"><tr><th>Finished Grants</th></tr>\n");

		for(int i=0;i<ProcessedGrants.getArrLRowsSize();i++){
			Out.append("<tr><td>"+ProcessedGrants.getColumnValue("Grants",i)+"</td></tr>\n");
		}

		Out.append("</table>\n");

		if (Total == EngineOutputEnum.FULLHTMLONLY) 
			Out.append("</html>");
		// *********************************************
		System.out.println("printHTMLProcessedGrantList="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Out;	
	}//end sub
	
}//end Class
