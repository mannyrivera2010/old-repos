package org.database.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.database.DataView;
import org.shared.StringTable;
import org.shared.Util;
import org.shared.encryption.JackSum;
import org.shared.performance.Timing;

// TODO: Auto-generated Javadoc
//BEFORE Abstract Class

/**
 * The Class SqliteController.
 */
public class SqliteController extends DataView {
	
	/** The Util shared. */
	private Util UtilShared = new Util();
	
	/** The Jack sum. */
	private JackSum JackSum=new JackSum();
		
	/**
	 * Instantiates a new sqlite controller.
	 */
	public SqliteController() {
		super();
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		
		UtilShared.folderExistAndMake("data");
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);

			Statement stat = conn.createStatement();
			
			stat.executeUpdate(super.Sql_Events_Table);
			stat.executeUpdate(super.Sql_FinishedGrants_Table);
			stat.executeUpdate(super.Sql_History_Table);
			stat.executeUpdate(super.Sql_MainLog_Table);
			
			
			conn.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_SqliteController()="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}
	
	/* (non-Javadoc)
	 * @see org.database.DataView#getHistoryTableWhere(java.lang.String)
	 */
	public StringTable getHistoryTableWhere(String inWorkingDir) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		StringTable SdtTemp=new StringTable("WorkingDir;;;File;;;CheckSum",";;;");
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);

			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("SELECT WorkingDir,FileName,CheckSum FROM History " +
					"WHERE WorkingDir like '"+inWorkingDir+"';");
			if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_getHistoryTableWhere()="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
			while (rs.next()) {
				//System.out.println("name = " + rs.getString("Grants"));
				SdtTemp.insertStringColumn(rs.getString("WorkingDir")+
						";;;"+rs.getString("FileName")+
						";;;"+rs.getString("CheckSum"));
			}
			rs.close();
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return SdtTemp;
	}
	
	/* (non-Javadoc)
	 * @see org.database.DataView#addToLogTable(org.shared.StringTable)
	 */
	public void addToLogTable(StringTable inDetailedTable) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);
			
			 PreparedStatement prep = conn.prepareStatement(
				      "insert into MainLog values (?,?,?,?,?,?,?,?,?);");

		
				    for(int i=0;i<inDetailedTable.getArrLRowsSize();i++){//Get Details
				        prep.setString(1,inDetailedTable.getColumnValue("Date", i));
					    prep.setString(2,inDetailedTable.getColumnValue("Action", i));
					    prep.setString(3,inDetailedTable.getColumnValue("File", i));
					    prep.setString(4,inDetailedTable.getColumnValue("WorkingDir", i));
					    prep.setString(5,inDetailedTable.getColumnValue("FileName", i));
					    prep.setString(6,inDetailedTable.getColumnValue("FileNameSig", i));
					    prep.setString(7,inDetailedTable.getColumnValue("GrantSubFolder", i));
					    prep.setString(8,inDetailedTable.getColumnValue("GrantSubFolderFull", i));
					    prep.setString(9,inDetailedTable.getColumnValue("WorkingDirGrantFolderFull", i));
					    prep.addBatch();
				    }//end loop
				   
				    conn.setAutoCommit(false);
				    prep.executeBatch();
				    conn.setAutoCommit(true);
		
			//
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_setHistoryTable()="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}
	
	/* (non-Javadoc)
	 * @see org.database.DataView#setHistoryTable(org.shared.StringTable)
	 */
	public void setHistoryTable(StringTable inHistoryTable) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);

			//Statement stat = conn.createStatement();
			//stat.executeUpdate("DELETE FROM History"); 
			
			 PreparedStatement prep = conn.prepareStatement(
				      "insert into History values (?,?,?);");

				    for(int i=0;i<inHistoryTable.getArrLRowsSize();i++){//Get Details
				        prep.setString(1,inHistoryTable.getColumnValue("WorkingDir", i));
					    prep.setString(2,inHistoryTable.getColumnValue("FileName", i));
					    prep.setString(3,inHistoryTable.getColumnValue("CheckSum", i));
					    prep.addBatch();
				    }//end loop
				   
				    conn.setAutoCommit(false);
				    prep.executeBatch();
				    conn.setAutoCommit(true);
		
			//
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_setHistoryTable()="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}
	
	/* (non-Javadoc)
	 * @see org.database.DataView#deleteHistoryTableRecordWhere(java.lang.String)
	 */
	public void deleteHistoryTableRecordWhere(String inHistoryTable) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);

			Statement stat = conn.createStatement();
			stat.executeUpdate("DELETE FROM History WHERE WorkingDir LIKE '"+inHistoryTable+"'"); 
			
	
			//
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_deleteHistoryTableRecordWhere()="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}
	
	/* (non-Javadoc)
	 * @see org.database.DataView#getFinishedGrants()
	 */
	public StringTable getFinishedGrants() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		StringTable SdtTemp=new StringTable("Grants","");
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);

			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("select Grants from FinishedGrants;");
			while (rs.next()) {
				//System.out.println("name = " + rs.getString("Grants"));
				SdtTemp.insertStringColumn(rs.getString("Grants"));
			}
			rs.close();
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_getFinishedGrants()="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return SdtTemp;
	}

	/* (non-Javadoc)
	 * @see org.database.DataView#getGrantListOLD_DISTINCT_GrantSubFolder()
	 */
	public StringTable getGrantListOLD_DISTINCT_GrantSubFolder() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		StringTable SdtTemp=new StringTable("GrantSubFolder",";;;");
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);

			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("SELECT DISTINCT GrantSubFolder " +
					"FROM MainLog " +
					"WHERE GrantSubFolder NOT LIKE '*' " +
					"AND Action NOT LIKE 'Removed' " +
					"AND GrantSubFolder NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
					"ORDER BY GrantSubFolderFull;");
			while (rs.next()) {
				//System.out.println("name = " + rs.getString("GrantSubFolderFull"));
				SdtTemp.insertStringColumn(rs.getString("GrantSubFolder"));
			}
			rs.close();
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_getGrantList_DISTINCT_GrantSubFolder()="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return SdtTemp;
	}
	
	/* (non-Javadoc)
	 * @see org.database.DataView#getGrantListOLD_DISTINCT_Date_GrantSubFolderFullWhere(java.lang.String)
	 */
	public StringTable getGrantListOLD_DISTINCT_Date_GrantSubFolderFullWhere(String inGrantSubFolderFull) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		
		StringTable SdtTemp=new StringTable("Date;;;GrantSubFolderFull;;;WorkingDirGrantFolderFull",";;;");
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);

			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("SELECT DISTINCT Date,GrantSubFolderFull,WorkingDirGrantFolderFull " +
					"FROM MainLog " +
					"WHERE GrantSubFolder NOT LIKE '*' " +
					"AND Action NOT LIKE 'Removed' " +
					"AND GrantSubFolder NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
					"AND GrantSubFolder LIKE '"+inGrantSubFolderFull+"' ORDER BY Date;");
			while (rs.next()) {
				//System.out.println("name = " + rs.getString("Grants"));
				SdtTemp.insertStringColumn(rs.getString("Date")+
						";;;"+rs.getString("GrantSubFolderFull")+
						";;;"+rs.getString("WorkingDirGrantFolderFull"));
			}
			rs.close();
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_getGrantList_DISTINCT_Date_GrantSubFolderFullWhere="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return SdtTemp;
	}
	
	/* (non-Javadoc)
	 * @see org.database.DataView#getGrantList_Date_Action_File_FileNameSig_Where(java.lang.String, java.lang.String)
	 */
	public StringTable getGrantList_Date_Action_File_FileNameSig_Where(String Date, String inGrantSubFolderFull){
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		
		StringTable SdtTemp=new StringTable("Date;;;Action;;;File;;;FileNameSig",";;;");
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);

			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("SELECT Date,Action,File,FileNameSig " +
					"FROM MainLog " +
					"WHERE GrantSubFolderFull LIKE '"+inGrantSubFolderFull+"' " +
					"AND GrantSubFolder NOT LIKE '*' " +
					"AND Date LIKE '"+Date+"' " +
					"AND Action NOT LIKE 'Removed' " +
					"AND GrantSubFolder NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
					"ORDER BY Date");
			while (rs.next()) {
				//System.out.println("name = " + rs.getString("Grants"));
				SdtTemp.insertStringColumn(UtilShared.formatDate(rs.getString("Date"))+
						";;;"+rs.getString("Action")+
						";;;"+rs.getString("File")+
						";;;"+JackSum.crc16_HEX(rs.getString("FileNameSig")));
			}
			rs.close();
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_getGrantList_Date_Action_File_FileNameSig_Where="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return SdtTemp;
	}
	

	/* (non-Javadoc)
	 * @see org.database.DataView#getIndividualFiles_DISTINCT_FileName()
	 */
	public StringTable getIndividualFiles_DISTINCT_FileName() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		
		StringTable SdtTemp=new StringTable("FileName","");
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);


			Statement stat = conn.createStatement();	
						
			ResultSet rs = stat.executeQuery("SELECT DISTINCT FileName " +
					"FROM MainLog " +
					"WHERE GrantSubFolder NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
					"AND Action NOT LIKE 'Removed' " +
					"ORDER BY FileName");
			
			while (rs.next()) {
				//System.out.println("name = " + rs.getString("FileName"));
				SdtTemp.insertStringColumn(rs.getString("FileName"));
			}
			rs.close();
			
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_getIndividualFiles_DISTINCT_FileName()="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return SdtTemp;
	}
	
	/* (non-Javadoc)
	 * @see org.database.DataView#getIndividualFiles_Date_Action_FileName_FileNameSig_Where(java.lang.String)
	 */
	public StringTable getIndividualFiles_Date_Action_FileName_FileNameSig_Where(String inFileName) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		
		StringTable SdtTemp=new StringTable("Date;;;Action;;;File;;;FileNameSig",";;;");
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);

			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("SELECT Date,Action,File,FileNameSig " +
					"FROM MainLog " +
					"WHERE GrantSubFolder NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
					"AND Action NOT LIKE 'Removed' " +
					"AND FileName LIKE '"+inFileName+"' ORDER BY FileName");
			while (rs.next()) {
				//System.out.println("name = " + rs.getString("Grants"));
				SdtTemp.insertStringColumn(UtilShared.formatDate(rs.getString("Date"))+
						";;;"+rs.getString("Action")+
						";;;"+rs.getString("File")+
						";;;"+JackSum.crc16_HEX(rs.getString("FileNameSig")));
			}
			rs.close();
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_getIndividualFiles_Date_Action_FileName_FileNameSig_Where="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return SdtTemp;
	}
	
	
	/* (non-Javadoc)
	 * @see org.database.DataView#getFolderFiles_DISTINCT_WorkingDir()
	 */
	public StringTable getFolderFiles_DISTINCT_WorkingDir() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		
		StringTable SdtTemp=new StringTable("WorkingDir","");
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);

			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("SELECT DISTINCT WorkingDir " +
					"FROM MainLog " +
					"WHERE GrantSubFolder NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
					"AND Action NOT LIKE 'Removed' " +
					"ORDER BY WorkingDir");
			while (rs.next()) {
				//System.out.println("name = " + rs.getString("Grants"));
				SdtTemp.insertStringColumn(rs.getString("WorkingDir"));
			}
			rs.close();
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_getFolderFiles_DISTINCT_WorkingDir()="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return SdtTemp;
	}
	
	/* (non-Javadoc)
	 * @see org.database.DataView#getFolderFiles_Date_Action_File_FileNameSig_Where(java.lang.String)
	 */
	public StringTable getFolderFiles_Date_Action_File_FileNameSig_Where(String inWorkingDir) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		
		StringTable SdtTemp=new StringTable("Date;;;Action;;;File;;;FileNameSig",";;;");
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);

			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("SELECT Date,Action,File,FileNameSig,WorkingDir " +
					"FROM MainLog " +
					"WHERE GrantSubFolder NOT IN (SELECT DISTINCT Grants FROM FinishedGrants) " +
					"AND Action NOT LIKE 'Removed' " +
					"AND WorkingDir LIKE '"+inWorkingDir+"' ORDER BY WorkingDir,FileName");
			while (rs.next()) {
				//System.out.println("name = " + rs.getString("Grants"));
				SdtTemp.insertStringColumn(UtilShared.formatDate(rs.getString("Date"))+
						";;;"+rs.getString("Action")+
						";;;"+rs.getString("File")+
						";;;"+(JackSum.crc16_HEX(rs.getString("FileNameSig"))));
			}
			rs.close();
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_getFolderFiles_Date_Action_File_FileNameSig_Where="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return SdtTemp;
	}
	
	/* (non-Javadoc)
	 * @see org.database.DataView#getEvents_Directories()
	 */
	public StringTable getEvents_Directories() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		
		StringTable SdtTemp=new StringTable("Directory;;;Email_TO;;;Email_CC;;;Subject;;;Body",";;;");
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);

			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("SELECT Directory,Email_TO,Email_CC,Subject,Body " +
					"FROM Events;");
			while (rs.next()) {
				//System.out.println("name = " + rs.getString("Grants"));
				SdtTemp.insertStringColumn(rs.getString("Directory")+
						";;;"+rs.getString("Email_TO")+
						";;;"+rs.getString("Email_CC")+
						";;;"+rs.getString("Subject")+
						";;;"+rs.getString("Body"));
			}
			rs.close();
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_getEvents_Directories()="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return SdtTemp;
	}	
	
	
	
	/*
	 * Generic
	 */	


	/* (non-Javadoc)
	 * @see org.database.DataView#genericInsert(java.lang.String, org.shared.StringTable)
	 */
	@Override
	protected void genericInsert(String TableName, StringTable inDetailedTable) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.database.DataView#genericSelect(java.lang.String)
	 */
	@Override
	protected StringTable genericSelect(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.database.DataView#genericExecute(java.lang.String)
	 */
	@Override
	protected void genericExecute(String string) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
