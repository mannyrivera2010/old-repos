package org.database;

import java.util.logging.Level;
import org.shared.StringTable;
import org.shared.performance.Timing;

public abstract class DataView {
	//
	protected String DatabaseFilePath="data\\data.db";
	
	protected String url = "jdbc:mysql://192.168.2.16:3307/grantprocess_test";

	protected String user = "admin";
	protected String password = "pass";
	
	//
	protected boolean StopWatchPerformance=false;
	protected Level LogLevel=Level.WARNING;
	
	
	protected String Sql_History_Table=("CREATE TABLE IF NOT EXISTS Packets (Date ,FileName varchar(255),CheckSum varchar(32));");
	
	protected abstract void genericInsert(String TableName, StringTable inDetailedTable);
	protected abstract StringTable genericSelect(String string);
	protected abstract void genericExecute(String string);
	
	
	/*
	 * History Table
	 */
	public void addToLogTable(StringTable inDetailedTable) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		genericInsert("MainLog", inDetailedTable);
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\taddToLogTable()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}

	public void setHistoryTable(StringTable inHistoryTable) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		this.genericInsert("History", inHistoryTable);
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tsetHistoryTable()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}
	
	public void deleteHistoryTableRecordWhere(String inHistoryTable) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		genericExecute("DELETE FROM History WHERE WorkingDir LIKE '"+inHistoryTable+"'");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tdeleteHistoryTableRecordWhere()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}
	
	
	public StringTable getHistoryTableWhere(String inWorkingDir) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT WorkingDir,FileName,CheckSum FROM History " +
				"WHERE WorkingDir like '"+inWorkingDir+"'  ORDER BY WorkingDir,FileName;");
		//if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetHistoryTableWhere()\t"+StopWatch1.Min_Sec_ms(StopWatch1.stop_Double()));
		return Temp;
	}
		
	/*
	 * GrantList
	 */
	public StringTable getGrantList_DISTINCT_GrantSubFolder() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT DISTINCT GrantSubFolder " +
				"FROM MainLog " +
				"WHERE GrantSubFolder NOT LIKE '*' " +
				"AND Action NOT LIKE 'Removed' " +
				"AND GrantSubFolder NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"ORDER BY GrantSubFolderFull;");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetGrantList_DISTINCT_GrantSubFolder(()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	
	public StringTable getGrantList_DISTINCT_Date_GrantSubFolderFullWhere(
			String inGrantSubFolderFull) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT DISTINCT Date,GrantSubFolderFull,WorkingDirGrantFolderFull " +
				"FROM MainLog " +
				"WHERE GrantSubFolder NOT LIKE '*' " +
				"AND Action NOT LIKE 'Removed' " +
				"AND GrantSubFolder NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"AND GrantSubFolder LIKE '"+inGrantSubFolderFull+"' ORDER BY Date;");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetGrantList_DISTINCT_Date_GrantSubFolderFullWhere()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	
		
	public StringTable getGrantList_Date_Action_File_FileNameSig_Where(String Date,
			String inGrantSubFolderFull) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT Date,Action,File,FileNameSig " +
				"FROM MainLog " +
				"WHERE GrantSubFolderFull LIKE '"+inGrantSubFolderFull+"' " +
				"AND GrantSubFolder NOT LIKE '*' " +
				"AND Date LIKE '"+Date+"' " +
				"AND Action NOT LIKE 'Removed' " +
				"AND GrantSubFolder NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"ORDER BY Date");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetGrantList_Date_Action_File_FileNameSig_Where()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	
	public StringTable getFinishedGrants() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT Grants FROM FinishedGrants;");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetFinishedGrants()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	

	/*
	 * IndividualFiles
	 */
	public StringTable getIndividualFiles_DISTINCT_FileName() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub.
		StringTable Temp= genericSelect("SELECT DISTINCT FileName " +
				"FROM MainLog " +
				"WHERE GrantSubFolder NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"AND Action NOT LIKE 'Removed' " +
				"ORDER BY FileName");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetIndividualFiles_DISTINCT_FileName()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	
	public StringTable getIndividualFiles_Date_Action_FileName_FileNameSig_Where(
			String inFileName) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT Date,Action,File,FileNameSig " +
				"FROM MainLog " +
				"WHERE GrantSubFolder NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"AND Action NOT LIKE 'Removed' " +
				"AND FileName LIKE '"+inFileName+"' ORDER BY FileName");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetIndividualFiles_Date_Action_FileName_FileNameSig_Where()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	
	/*
	 * FolderView
	 */
	public StringTable getFolderFiles_DISTINCT_WorkingDir() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT DISTINCT WorkingDir " +
				"FROM MainLog " +
				"WHERE GrantSubFolder NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"AND Action NOT LIKE 'Removed' " +
				"ORDER BY WorkingDir");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetFolderFiles_DISTINCT_WorkingDir\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	
	public StringTable getFolderFiles_Date_Action_File_FileNameSig_Where(
			String inWorkingDir) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		// TODO Auto-generated method stub
		StringTable Temp= genericSelect("SELECT Date,Action,File,FileNameSig,WorkingDir " +
				"FROM MainLog " +
				"WHERE GrantSubFolder NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
				"AND Action NOT LIKE 'Removed' " +
				"AND WorkingDir LIKE '"+inWorkingDir+"' ORDER BY WorkingDir,FileName");
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tgetFolderFiles_Date_Action_File_FileNameSig_Where()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return Temp;
	}
	
	/*
	 * Events
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

	



}
