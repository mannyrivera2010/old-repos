package org.testing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.shared.StringTable;

// TODO: Auto-generated Javadoc
/**
 * The Class ImportTxtLog.
 */
public class ImportTxtLog {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws SQLException the sQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		/*
		//Events
		//StringTable Events=new StringTable("Directory_Location,Email_TO,Email_CC,Subject,Body",",","event.csv");
		//Events.Remove1stArrLRows();//Remove File Header
		
		//TableLog
		//StringTable TableLog=new StringTable("Date\tAction\tFile", "\t","log.txt");//load log
		//TableLog.ExcludeFilterStringTable("Action", "Removed:", true);//Remove "Removed:" Lines
		//TableLog.qsort("FileName");//Sort files
		
		//TableDetailedLogs
		//StringTable TableDetailedLog= TableDetailedLogRedirect("Date;Action;File;WorkingDir;" +
		//		"FileName;FileNameSig;FileNameFull;GrantSubFolder;GrantSubFolderFull;FileNameFullNoSig;WorkingDirGrantFolderFull",";",TableLog,Events);
		TableDetailedLog.qsort("Date");
		//System.out.println(TableDetailedLog.toString());
		
		
		  Class.forName("org.sqlite.JDBC");
		    Connection conn =
		      DriverManager.getConnection("jdbc:sqlite:data\\test.db");
		    Statement stat = conn.createStatement();
		    stat.executeUpdate("drop table if exists MainLog;");
		    stat.executeUpdate("create table MainLog (Date,Action,File,WorkingDir," +
				"FileName,FileNameSig,FileNameFull,GrantSubFolder,GrantSubFolderFull,FileNameFullNoSig,WorkingDirGrantFolderFull);");
		    PreparedStatement prep = conn.prepareStatement(
		      "insert into MainLog values (?,?,?,?,?,?,?,?,?,?,?);");

		    for(int i=0;i<TableDetailedLog.getArrLRowsSize();i++){//Get Details
		        prep.setString(1,TableDetailedLog.getColumnValue("Date", i));
			    prep.setString(2,TableDetailedLog.getColumnValue("Action", i));
			    prep.setString(3,TableDetailedLog.getColumnValue("File", i));
			    prep.setString(4,TableDetailedLog.getColumnValue("WorkingDir", i));
			    prep.setString(5,TableDetailedLog.getColumnValue("FileName", i));
			    prep.setString(6,TableDetailedLog.getColumnValue("FileNameSig", i));
			    prep.setString(7,TableDetailedLog.getColumnValue("FileNameFull", i));
			    prep.setString(8,TableDetailedLog.getColumnValue("GrantSubFolder", i));
			    prep.setString(9,TableDetailedLog.getColumnValue("GrantSubFolderFull", i));
			    prep.setString(10,TableDetailedLog.getColumnValue("FileNameFullNoSig", i));
			    prep.setString(11,TableDetailedLog.getColumnValue("WorkingDirGrantFolderFull", i));
			    prep.addBatch();
		    }//end loop
		   
		    conn.setAutoCommit(false);
		    prep.executeBatch();
		    conn.setAutoCommit(true);

		    
		  
		    conn.close();
		    */
		  }//end main
	

	// /////////////////////////////
		// TableDetailedLogRedirect/////
		// /////////////////////////////
			
		/**
	 * Table detailed log redirect.
	 *
	 * @param instrConRaw the instr con raw
	 * @param instrDelimiter the instr delimiter
	 * @param TableLog the table log
	 * @param Events the events
	 * @return the string table
	 */
	public static StringTable TableDetailedLogRedirect(String instrConRaw, String instrDelimiter,StringTable TableLog,StringTable Events){
				StringTable TableDetailedLog = new StringTable(instrConRaw,instrDelimiter);
				for(int i=0;i<TableLog.getArrLRowsSize();i++){//Get Details
					String Date=TableLog.getColumnValue("Date", i);
					String Action=TableLog.getColumnValue("Action", i);
					String File=TableLog.getColumnValue("File", i);
					
					String WorkingDir="-1";
					
					for (int j = 0; j < Events.getArrLRowsSize(); j++) {
						String StrCurDir = Events.getColumnValue("Directory_Location", j);
						
						String ShortenFile="-1";
						//System.out.println(StrCurDir.length()+">"+File.length());
						if(StrCurDir.length()>File.length()){
							//System.out.println("Debug: OVER\t"+StrCurDir+"\t"+File);
						}else{
							ShortenFile=File.substring(0, StrCurDir.length());
						}
						//System.out.println("Debug5da>"+StrCurDir+"\t"+ShortenFile+"\t"+
						//		StrCurDir.equalsIgnoreCase(ShortenFile));
						if(StrCurDir.equalsIgnoreCase(ShortenFile)){
							//System.out.println(ShortenFile.substring(ShortenFile.length()-1,ShortenFile.length()));
							if(ShortenFile.substring(ShortenFile.length()-1,ShortenFile.length()).equalsIgnoreCase("\\")){
								WorkingDir=ShortenFile;
							}else{
								WorkingDir=ShortenFile+"\\";
							}
							break;
						}
					}//end loop
					
					String RestOfPath=File.substring(WorkingDir.length());
					
					String GrantSubFolderFull="";
					if(RestOfPath.indexOf("\\")>=1){
						GrantSubFolderFull=RestOfPath.substring(0, RestOfPath.indexOf("\\"));	
					}else{
						GrantSubFolderFull="*";
					}
					
					String GrantSubFolder="";
					
					if(GrantSubFolderFull.indexOf("_")>=1||GrantSubFolderFull.indexOf("-")>=1){
						if(GrantSubFolderFull.indexOf("_")>=1){
							GrantSubFolder=RestOfPath.substring(0, RestOfPath.indexOf("_"));
						}else if(GrantSubFolderFull.indexOf("-")>=1){
							GrantSubFolder=RestOfPath.substring(0, RestOfPath.indexOf("-"));
						}	
					}else{
						GrantSubFolder=GrantSubFolderFull;
					}

					String FileNameFull="";
					
					if(RestOfPath.lastIndexOf("\\")>=1){
						FileNameFull=RestOfPath.substring(RestOfPath.lastIndexOf("\\")+1);
					}else{
						FileNameFull=RestOfPath;
					}
					//System.out.println("FileNameFull>>"+FileNameFull);
							

					String FileName="-1";
					String FileNameSig="-1";
					String FileNameFullNoSig="-1";
					
					
					if(FileNameFull.substring(FileNameFull.length()-1,FileNameFull.length()).equalsIgnoreCase(")")){//Contain
						FileName=FileNameFull.substring(0, FileNameFull.lastIndexOf("(")).trim();
						FileNameSig=FileNameFull.substring(FileNameFull.lastIndexOf("(")+1,FileNameFull.length()-1);
						FileNameFullNoSig=File.substring(0, File.lastIndexOf("(")).trim();
					}else{//Not Contain
						FileName=FileNameFull;
						FileNameSig="-1";
						FileNameFullNoSig=FileName;
					}

					
					String WorkingDirGrantFolderFull=WorkingDir+GrantSubFolderFull;
					//System.out.println("FileNameFull>>"+FileNameFull);
					TableDetailedLog.insertStringColumn(Date+";"+Action+";"+File+";"+WorkingDir+";"+FileName+
							";"+FileNameSig+";"+FileNameFull+";"+GrantSubFolder+
							";"+GrantSubFolderFull+";"+FileNameFullNoSig+";"+WorkingDirGrantFolderFull);
					
					//System.out.println(Date+";"+Action+";"+File+";"+WorkingDir+";"+FileName+
					//		";"+FileNameSig+";"+FileNameFull+";"+GrantSubFolder+
					//		";"+GrantSubFolderFull+"");
					
				}
				return TableDetailedLog;
			}
		
	
}//end im
