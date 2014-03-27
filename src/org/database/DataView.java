package org.database;

import java.util.logging.Level;
import org.shared.StringTable;
import org.shared.performance.Timing;

// TODO: Auto-generated Javadoc
/**
 * The Class DataView.
 */
public abstract class DataView {
	//Sqlite Database File
	/** The Database file path. */
	protected String DatabaseFilePath="data\\data.db";
	
	//MySql Database Settings
	/** The url. */
	protected String url = "jdbc:mysql://192.168.2.16:3307/grantprocess_test";
	
	/** The user. */
	protected String user = "admin";
	
	/** The password. */
	protected String password = "pass";
	
	//For Performance and Logging
	/** The Stop watch performance. */
	protected boolean StopWatchPerformance=false;
	
	/** The Log level. */
	protected Level LogLevel=Level.WARNING;
	
	//SQL Statements used to create database tables
	/** The Sql_ history_ table. */
	protected String Sql_History_Table=("CREATE TABLE IF NOT EXISTS History (WorkingDir varchar(100),FileName varchar(255),CheckSum varchar(32));");
	
	/** The Sql_ main log_ table. */
	protected String Sql_MainLog_Table=("CREATE TABLE IF NOT EXISTS MainLog (Date varchar(32),Action varchar(15),File varchar(255),WorkingDir varchar(100),FileName varchar(255)," +
			"FileNameSig varchar(32),GrantSubFolder varchar(100),GrantSubFolderFull varchar(100)," +
			"WorkingDirGrantFolderFull varchar(100),GrantID Int);");
	
	/** The Sql_ events_ table. */
	protected String Sql_Events_Table=("CREATE TABLE IF NOT EXISTS Events (Directory varchar(100),Email_TO varchar(100),Email_CC varchar(100),Subject varchar(100),Body varchar(1000));");
	
	/** The Sql_ finished grants_ table. */
	protected String Sql_FinishedGrants_Table=("CREATE TABLE IF NOT EXISTS FinishedGrants (Grants varchar(30));");
		
	
	//Methods for subclasses
	/**
	 * Generic insert.
	 *
	 * @param TableName the table name
	 * @param inDetailedTable the in detailed table
	 */
	protected abstract void genericInsert(String TableName, StringTable inDetailedTable);
	
	/**
	 * Generic select.
	 *
	 * @param string the string
	 * @return the string table
	 */
	protected abstract StringTable genericSelect(String string);
	
	/**
	 * Generic execute.
	 *
	 * @param string the string
	 */
	protected abstract void genericExecute(String string);
		
	/* ****************************************
	 * History Table
	 * ****************************************/
	/**
	 * Adds the to log table.
	 *
	 * @param inDetailedTable the in detailed table
	 */
	public void addToLogTable(StringTable inDetailedTable) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		genericInsert("MainLog", inDetailedTable);
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\taddToLogTable()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}

	/**
	 * Sets the history table.
	 *
	 * @param inHistoryTable the new history table
	 */
	public void setHistoryTable(StringTable inHistoryTable) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		this.genericInsert("History", inHistoryTable);
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tsetHistoryTable()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}
	
	/**
	 * Delete history table record where.
	 *
	 * @param inHistoryTable the in history table
	 */
	public void deleteHistoryTableRecordWhere(String inHistoryTable) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		genericExecute("DELETE FROM History WHERE WorkingDir LIKE '"+inHistoryTable+"'");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tdeleteHistoryTableRecordWhere()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}
	
	/**
	 * Gets the history table where.
	 *
	 * @param inWorkingDir the in working dir
	 * @return the history table where
	 */
	public StringTable getHistoryTableWhere(String inWorkingDir) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT WorkingDir,FileName,CheckSum,GrantID FROM History " +
				"WHERE WorkingDir like '"+inWorkingDir+"'  ORDER BY WorkingDir,FileName;");
		//if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetHistoryTableWhere()\t"+StopWatch1.Min_Sec_ms(StopWatch1.stop_Double()));
		return Temp;
	}
		
	/* ****************************************
	 * GrantList_new_Based on GrantID
	 * ****************************************/
	/**
	 * Gets the grant list_ distinc t_ grant id.
	 *
	 * @return the grant list_ distinc t_ grant id
	 */
	public StringTable getGrantList_DISTINCT_GrantID() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT DISTINCT Date,GrantSubFolder,GrantID " +
				"FROM MainLog " +
				"WHERE GrantSubFolder NOT LIKE '*' " +
				"AND Action NOT LIKE 'Removed' " +
				"AND GrantID NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"Group by GrantID " +
				"ORDER by GrantSubFolder asc , GrantID asc;");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetGrantList_DISTINCT_GrantSubFolder(()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	
	/**
	 * Gets the grant list_ distinc t_ date_ grant sub folder full where.
	 *
	 * @param inGrantID the in grant id
	 * @return the grant list_ distinc t_ date_ grant sub folder full where
	 */
	public StringTable getGrantList_DISTINCT_Date_GrantSubFolderFullWhere(
			String inGrantID) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT DISTINCT Date,GrantSubFolder,WorkingDirGrantFolderFull,GrantID  " +
				"FROM MainLog " +
				"WHERE GrantSubFolder NOT LIKE '*' " +
				"AND Action NOT LIKE 'Removed' " +
				"AND GrantID NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"AND GrantID LIKE '"+inGrantID+"' ORDER BY Date;");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetGrantList_DISTINCT_Date_GrantSubFolderFullWhere()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	

	/* ****************************************
	 * GrantList_Old_Based on GrantSubFolder
	 * ****************************************/
	/**
	 * Gets the grant list ol d_ distinc t_ grant sub folder.
	 *
	 * @return the grant list ol d_ distinc t_ grant sub folder
	 */
	public StringTable getGrantListOLD_DISTINCT_GrantSubFolder() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT DISTINCT GrantSubFolder " +
				"FROM MainLog " +
				"WHERE GrantSubFolder NOT LIKE '*' " +
				"AND Action NOT LIKE 'Removed' " +
				"AND GrantID NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"ORDER BY GrantSubFolderFull;");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetGrantList_DISTINCT_GrantSubFolder(()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	
	/**
	 * Gets the grant list ol d_ distinc t_ date_ grant sub folder full where.
	 *
	 * @param inGrantSubFolderFull the in grant sub folder full
	 * @return the grant list ol d_ distinc t_ date_ grant sub folder full where
	 */
	public StringTable getGrantListOLD_DISTINCT_Date_GrantSubFolderFullWhere(
			String inGrantSubFolderFull) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT DISTINCT Date,GrantSubFolderFull,WorkingDirGrantFolderFull " +
				"FROM MainLog " +
				"WHERE GrantSubFolder NOT LIKE '*' " +
				"AND Action NOT LIKE 'Removed' " +
				"AND GrantID NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"AND GrantSubFolder LIKE '"+inGrantSubFolderFull+"' ORDER BY Date;");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetGrantList_DISTINCT_Date_GrantSubFolderFullWhere()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
		
	/**
	 * Gets the grant list_ date_ action_ file_ file name sig_ where.
	 *
	 * @param Date the date
	 * @param inGrantID the in grant id
	 * @return the grant list_ date_ action_ file_ file name sig_ where
	 */
	public StringTable getGrantList_Date_Action_File_FileNameSig_Where(String Date,
			String inGrantID) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT Date,Action,File,FileNameSig " +
				"FROM MainLog " +
				"WHERE GrantID LIKE '"+inGrantID+"' " +
				"AND Date LIKE '"+Date+"' " +
				"AND GrantSubFolder NOT LIKE '*' " +
				"AND Action NOT LIKE 'Removed' " +
				"AND GrantID NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"ORDER BY Date");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetGrantList_Date_Action_File_FileNameSig_Where()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	
	/* ****************************************
	 * Finish Grants 
	 * ****************************************/
	/**
	 * Gets the finished grants.
	 *
	 * @return the finished grants
	 */
	public StringTable getFinishedGrants() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT Grants FROM FinishedGrants;");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetFinishedGrants()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}

	/* ****************************************
	 * IndividualFiles
	 */
	/**
	 * Gets the individual files_ distinc t_ file name.
	 *
	 * @return the individual files_ distinc t_ file name
	 */
	public StringTable getIndividualFiles_DISTINCT_FileName() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub.
		StringTable Temp= genericSelect("SELECT DISTINCT FileName " +
				"FROM MainLog " +
				"WHERE GrantID NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"AND Action NOT LIKE 'Removed' " +
				"ORDER BY FileName");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetIndividualFiles_DISTINCT_FileName()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	
	/**
	 * Gets the individual files_ date_ action_ file name_ file name sig_ where.
	 *
	 * @param inFileName the in file name
	 * @return the individual files_ date_ action_ file name_ file name sig_ where
	 */
	public StringTable getIndividualFiles_Date_Action_FileName_FileNameSig_Where(
			String inFileName) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT Date,Action,File,FileNameSig,GrantID " +
				"FROM MainLog " +
				"WHERE GrantID NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"AND Action NOT LIKE 'Removed' " +
				"AND FileName LIKE '"+inFileName+"' ORDER BY FileName,Date");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetIndividualFiles_Date_Action_FileName_FileNameSig_Where()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	
	/* ****************************************
	 * FolderView
	 * ****************************************/
	/**
	 * Gets the folder files_ distinc t_ working dir.
	 *
	 * @return the folder files_ distinc t_ working dir
	 */
	public StringTable getFolderFiles_DISTINCT_WorkingDir() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT DISTINCT WorkingDir " +
				"FROM MainLog " +
				"WHERE GrantID NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"AND Action NOT LIKE 'Removed' " +
				"ORDER BY WorkingDir");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetFolderFiles_DISTINCT_WorkingDir\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	
	/**
	 * Gets the folder files_ date_ action_ file_ file name sig_ where.
	 *
	 * @param inWorkingDir the in working dir
	 * @return the folder files_ date_ action_ file_ file name sig_ where
	 */
	public StringTable getFolderFiles_Date_Action_File_FileNameSig_Where(
			String inWorkingDir) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT Date,Action,File,FileNameSig,WorkingDir,GrantID " +
				"FROM MainLog " +
				"WHERE GrantID NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"AND Action NOT LIKE 'Removed' " +
				"AND WorkingDir LIKE '"+inWorkingDir+"' ORDER BY WorkingDir,FileName,Date");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetFolderFiles_Date_Action_File_FileNameSig_Where()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	
	/* ****************************************
	 * Gets the list of Directories with who
	 * to email
	 * ****************************************/
	/**
	 * Gets the events_ directories.
	 *
	 * @return the events_ directories
	 */
	public StringTable getEvents_Directories() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT Directory,Email_TO,Email_CC,Subject,Body " +
				"FROM Events;");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetEvents_Directories()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;		
	}

}//end class
