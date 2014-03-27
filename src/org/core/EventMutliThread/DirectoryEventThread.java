package org.core.EventMutliThread;

import java.io.File;

import org.database.DatabaseFactory;
import org.enums.DatabaseEngineEnum;
import org.logging.LogFileLimit;

import org.shared.DirChanges;
import org.shared.IniEngine;
import org.shared.StringTable;
import org.shared.Util;

import org.shared.encryption.JackSum;
import org.shared.mailing.Mail;
import org.shared.performance.Timing;

// TODO: Auto-generated Javadoc
/**
 * The Class DirectoryEventThread.
 */
public class DirectoryEventThread extends Thread {
	//Logging
	/** The logg. */
	private LogFileLimit logg = new LogFileLimit();
	
	//Database Variables
	/** The Data base choice. */
	private DatabaseEngineEnum DataBaseChoice;
	
	/** The Database. */
	private DatabaseFactory Database = null;
		
	//Performance Variables
	/** The Stop watch performance. */
	private boolean StopWatchPerformance = false;  //used to turn performance data on and off
	
	/** The IO performance. */
	private boolean IOPerformance = false;  //used to turn performance data on and off
			
	//For Log and passing to GUI
	/** The Str global log. */
	private String StrGlobalLog = "";
	
	// Location
	/** The Directory_ location. */
	private String Directory_Location = "";
	
	//Used to store tables with Directory Structures
	/** The Historial files table. */
	StringTable HistorialFilesTable = null;
	
	//Email Variables
	/** The Email_ to. */
	private String Email_TO = "";
	
	/** The Email_ cc. */
	private String Email_CC = "";
	
	/** The Subject. */
	private String Subject = "";
	
	/** The Body. */
	private String Body = "";

	
	/**
	 * This Method is the Constructor. It Sets the Directory,Email Variables, and the Database Choice
	 *
	 * @param directory_Location the directory_ location
	 * @param email_TO the email_ to
	 * @param email_CC the email_ cc
	 * @param subject the subject
	 * @param body the body
	 * @param dataBaseChoice the data base choice
	 */
	public DirectoryEventThread(String directory_Location,
			String email_TO, String email_CC, String subject, String body,
			DatabaseEngineEnum dataBaseChoice) {
		super();
		Timing StopWatch1 = new Timing();  //Used for Performance
		StopWatch1.start();
		
		if(directory_Location.substring(directory_Location.length()-1).equals("\\")){
			Directory_Location = directory_Location.substring(0, directory_Location.length()-1);
		}else
			Directory_Location = directory_Location;
		
		//System.out.println(Directory_Location);
		
		Email_TO = email_TO;
		Email_CC = email_CC;
		Subject = subject;
		Body = body;

		DataBaseChoice = dataBaseChoice;
		Database = new DatabaseFactory(DataBaseChoice);

		if (StopWatchPerformance)
			System.out.println(this.getClass().getName() + "_DirectoryEvent("
					+ directory_Location + ")="
					+ StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}//end DirectoryEventMutliThread()

	/**
	 * This Method is called for Single Threaded instants.
	 */
	public void go() {
		Util UtilShared = new Util();
		Timing StopWatch1 = new Timing();  //Used for Performance
		StopWatch1.start();

		if (UtilShared.folderExist(this.Directory_Location)) {
			RunEventActions();
			// Util.PrintSepLine();
		} else {
			System.err.println("DirectoryEvent(" + this.Directory_Location
					+ ") Could not find = " + this.Directory_Location);
			Database.data
					.deleteHistoryTableRecordWhere(this.Directory_Location);
		}
		if (StopWatchPerformance)
			System.out.println(this.getClass().getName() + "_Go("
					+ this.Directory_Location + ")="
					+ StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}//end void Go()
	
	/**
	 * For Mutli-Threading(non-Javadoc).
	 *
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		this.go();
	}
		
		
	/**
	 * This method starts whole process of comparing directories, email, and logging.
	 */
	private void RunEventActions() {
		Timing StopWatch1 = new Timing();  //Used for Performance
		StopWatch1.start();
		DirChanges DirectoryChanges = new DirChanges();  //Class for Directory Comparison

		//Create new StringTable of Previous structure
		StringTable StPrevious = Database.data
				.getHistoryTableWhere(this.Directory_Location);
		HistorialFilesTable = StPrevious;

		reset(); //Clear and re-create database Persistence 

		//Creates new 
		StringTable StCurrent = Database.data
				.getHistoryTableWhere(this.Directory_Location);
		HistorialFilesTable = StCurrent;

		// System.out.println("stCurrent=\n"+HistorialFilesTable);

		DirectoryChanges.setPreviousFileNames(StPrevious); //Set Table with previous directory structure
		DirectoryChanges.setCurrentFileNames(StCurrent); //Set Table with current directory structure
		// System.out.println("Compare()\n="+DirectoryChanges.compareAndReportStringTable());

		insertLogAndEmailString(DirectoryChanges.compareAndReportStringTable()); //Pass Comparison results for logging it and email

		if (StopWatchPerformance)
			System.out.println(this.getClass().getName() + "_RunEventActions("
					+ this.Directory_Location + ")="
					+ StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}

	/**
	 * Method used to Clear and re-create database Persistence of Directory Structures for comparison.
	 */
	public void reset() {
		Timing StopWatch1 = new Timing();  //Used for Performance
		Timing StopWatch2 = new Timing();  //Used for Performance
		StopWatch1.start();
		
		Database.data.deleteHistoryTableRecordWhere(this.Directory_Location);  
		//Clear database Persistence of Directory Structures

		StopWatch2.start();
		
		StringTable TempTable = makeFileList(this.Directory_Location,
				Database.data.getHistoryTableWhere(Directory_Location));
		//Creates a new StringTable with new directory structure 
		
		
		if (StopWatchPerformance)
			System.out.println(this.getClass().getName()
					+ "_Reset>>>>>>>>>>>>>MakeFileList(" + Directory_Location
					+ ")<<<<<<<<<<<<<<<<<<<<<<<<<<<<="
					+ StopWatch2.sec_ms(StopWatch2.stop_SecDouble()));
		
		Database.data.setHistoryTable(TempTable);//Add the new StringTable to database for comparison 
		
		
		if (StopWatchPerformance)
			System.out.println(this.getClass().getName() + "_Reset()="
					+ StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}

	/**
	 * Method is used for Inserting a StringTable into the database and log events into database.
	 *
	 * @param StPlain the st plain
	 */
	public void insertLogAndEmailString(StringTable StPlain) {
		Util UtilShared = new Util();
		Timing StopWatch1 = new Timing();  //Used for Performance
		StopWatch1.start();
		
		StringTable detailedTable = this.TableDetailedLogRedirect(StPlain,
				this.Directory_Location);
		
		Database.data.addToLogTable(detailedTable);//Add detailedTable to Database

		StringTable LogEmailOut = new StringTable("Date\tAction\tFile", "\t");//New table with 3 columns
		for (int i = 0; i < detailedTable.getArrLRowsSize(); i++) {
			//Get values
			String strDate = UtilShared.formatDate(detailedTable.getColumnValue(
					"Date", i));
			String strAction = detailedTable.getColumnValue("Action", i);
			String strFile = detailedTable.getColumnValue("File", i);

			
			if (strAction.toLowerCase().contains("Added".toLowerCase())) {
				//This method only allows for entries with added to be emailed
				LogEmailOut.insertStringArray(new String[] { strDate,
						strAction, strFile });
			}
		}

		StrGlobalLog = LogEmailOut.toStringBodyDel();

		if (LogEmailOut.getArrLRowsSize() >= 1) {
			System.out.println(LogEmailOut);
			email(LogEmailOut.toString());
		}

		if (StopWatchPerformance)
			System.out.println(this.getClass().getName()
					+ "_InsertLogAndEmailString()="
					+ StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}//end InsertLogAndEmailString()

	/**
	 * Method used to pass Log Info to MainApp class (GUI).
	 *
	 * @return the log
	 */
	public String getLog() {
		return StrGlobalLog;
	}//end GetLog()

	/**
	 * Method is Used to email the String Results
	 * It uses the Private Email Variables to get the TO,CC,Subject value.
	 *
	 * @param Results the results
	 */
	public void email(String Results) {
		Timing StopWatch1 = new Timing();  //Used for Performance
		StopWatch1.start();

		Mail MailAgent = new Mail(); //Create a new MailAgent for Sending Emails

		IniEngine IniS=new IniEngine();  //Created an object for reading ini files
		String strSettingFileName="Settings.ini"; //Set the Config filename
		
		//Getting all the configurations
		String user_auth = IniS.getIniStringValue(strSettingFileName, "Email", "user_auth"); //Username
		String user_password = IniS.getIniStringValue(strSettingFileName, "Email", "user_password"); //Password
		String host = IniS.getIniStringValue(strSettingFileName, "Email", "host"); //Host ex)google.com
		String port = IniS.getIniStringValue(strSettingFileName, "Email", "port"); //465
		String send_mail_As = IniS.getIniStringValue(strSettingFileName, "Email", "send_mail_As"); //Full email
		String TLS_not_SSL = IniS.getIniStringValue(strSettingFileName, "Email", "TLS_not_SSL"); 
		// If yes, use SSL authentication. if no, use TLS authentication   
		String SendEmails = IniS.getIniStringValue(strSettingFileName, "Email", "SendEmails"); // Option Disable Emails in config
		
		//Set the username, password, host, mail as, and TLS or SSL authentication
		MailAgent.setUserAuth(user_auth,
				org.shared.encryption.Encoder.Decode(user_password), host,
				port, send_mail_As, TLS_not_SSL);

		//System.out.println(Email_TO + "\t" + Email_CC + "\t" + Subject + "\t"
		//		+ Body); //for testing

		if(SendEmails.trim().contains("off")){ //Check for Option 
			//Do not Send Email
		}else{
			//Send the email to the right people, and sets subject, body with results
			MailAgent.SendMail(Email_TO, Email_CC, Subject, Body
					+ "\n\n\n\n----------------\nAction:\n" + Results);
		}
		
		if (StopWatchPerformance)
			System.out.println(this.getClass().getName() + "_Email()="
					+ StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}//end void Email()


	/**
	 * This method is used to break down the strings and expand data into more columns for easy SQL statements
	 * 
	 * It will return a new table with the expand data
	 * Expands 4 column into 10 column.
	 *
	 * @param TableLog the table log
	 * @param WorkingDirPre the working dir pre
	 * @return the string table
	 */
	private StringTable TableDetailedLogRedirect(StringTable TableLog,
			String WorkingDirPre) {
		Timing StopWatch1 = new Timing();  //Used for Performance
		StopWatch1.start();

		//Created a New StringTable with 10 columns
		StringTable TableDetailedLog = new StringTable("Date" + ";;;"
				+ "Action" + ";;;" + "File" + ";;;" + "WorkingDir" + ";;;"
				+ "FileName" + ";;;" + "FileNameSig" + ";;;" + "GrantSubFolder"
				+ ";;;" + "GrantSubFolderFull" + ";;;"
				+ "WorkingDirGrantFolderFull" + ";;;"
				+ "GrantID", ";;;");
		
		//Iterate table TableLog 
		for (int i = 0; i < TableLog.getArrLRowsSize(); i++) {
			String Date = TableLog.getColumnValue("Date", i); //Get the Date Value from row i
			String Action = TableLog.getColumnValue("Action", i); //Get the Action value from row i 
			String File = TableLog.getColumnValue("FileName", i); //Get the FileName value
			String CheckSum = TableLog.getColumnValue("CheckSum", i); //Get the CheckSum value
			String strGrantID=TableLog.getColumnValue("GrantID",i); //Get the GrantID
			
			// System.out.println(Date+"\t"+Action+"\t"+File+"\t"+WorkingDirPre);

			String WorkingDir = "-1";
			if (WorkingDirPre.substring(WorkingDirPre.length() - 1,
					WorkingDirPre.length()).equalsIgnoreCase("\\")) {
				WorkingDir = WorkingDirPre;
			} else {
				WorkingDir = WorkingDirPre + "\\";
			}

			String RestOfPath = File.substring(WorkingDir.length());
			//System.out.println("\tRestOfPath>>>\t" + RestOfPath);

			String GrantSubFolderFull = "";
			if (RestOfPath.indexOf("\\") >= 1) {
				GrantSubFolderFull = RestOfPath.substring(0,
						RestOfPath.indexOf("\\"));
			} else {
				GrantSubFolderFull = "*";
			}


			String GrantSubFolder = "";

			int HypenIndex = GrantSubFolderFull.indexOf("-");
			int UnderScoreIndex = GrantSubFolderFull.indexOf("_");

			if(HypenIndex>=0&&UnderScoreIndex==-1){
				//Has Only Hypen
				GrantSubFolder = GrantSubFolderFull.substring(0,HypenIndex);
			}else if(UnderScoreIndex>=0&&HypenIndex==-1){
				//Has Only Underscore
				GrantSubFolder = GrantSubFolderFull.substring(0,UnderScoreIndex);
			}else if(HypenIndex>=0&&HypenIndex<UnderScoreIndex){
				//Has Both Hypen and Underscore Look for First HypenIndex
				GrantSubFolder = GrantSubFolderFull.substring(0,HypenIndex);
			}else if(UnderScoreIndex>=0&&UnderScoreIndex<HypenIndex){
				//Has Both Hypen and Underscore Look for First UnderScoreIndex
				GrantSubFolder = GrantSubFolderFull.substring(0,UnderScoreIndex);
			}else{
				GrantSubFolder = GrantSubFolderFull;
			}
		

			String FileNameFull = "";

			if (RestOfPath.lastIndexOf("\\") >= 1) {
				FileNameFull = RestOfPath.substring(RestOfPath
						.lastIndexOf("\\") + 1);
			} else {
				FileNameFull = RestOfPath;
			}

			String FileName = FileNameFull;
			String FileNameSig = CheckSum;

			String WorkingDirGrantFolderFull = WorkingDir + GrantSubFolderFull;
			
			/*
			 *  After processing string, make new row with new columns
			 */
			
			TableDetailedLog.insertStringColumn(Date + ";;;" + Action + ";;;"
					+ File + ";;;" + WorkingDir + ";;;" + FileName + ";;;"
					+ FileNameSig + ";;;" + GrantSubFolder + ";;;"
					+ GrantSubFolderFull + ";;;" + WorkingDirGrantFolderFull + ";;;" 
					+ strGrantID);

			// System.out.println("{"+Date+">>"+Action+">>"+File+">>"+WorkingDir+">>"+FileName+
			// ">>"+FileNameSig+">>"+FileNameFull+">>"+GrantSubFolder+
			// ">>"+GrantSubFolderFull+"}");  //for Testing
		}

		if (StopWatchPerformance)
			System.out.println(this.getClass().getName()
					+ "_TableDetailedLogRedirect()="
					+ StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return TableDetailedLog;
	}//end StringTable TableDetailedLog()

	/**
	 * Method Goes through the Directories
	 * It make the FolderProperties.ini
	 * 
	 * It returns a StringTable of Directory Structure
	 *
	 * @param path the path
	 * @param StTemp the st temp
	 * @return the string table
	 */
	public StringTable makeFileList(String path, StringTable StTemp) {
		Util UtilShared = new Util(); //Used for misc methods
		JackSum JackSumEngine = new JackSum();  //Used for CheckSum
		Timing StopWatch1 = new Timing();  //Used for Performance
		StopWatch1.start();
		
		IniEngine IniS=new IniEngine();
		
		try {
			File root = new File(path);
			File[] list = root.listFiles();

			for (File f : list) {
				if (f.isDirectory()) {
					//System.out.println("Dir:" + f.getAbsoluteFile());
					
					String strFolderPropertiesFileName=f.getAbsolutePath()+"\\.FolderProperties.ini";
					String FolderName=f.getAbsolutePath().substring(Directory_Location.length()+1);  //used to find out if is root folder
					boolean blnFileEx=UtilShared.fileExist(strFolderPropertiesFileName);  //Checks if File exists
					
					
					if(!blnFileEx && FolderName.indexOf("\\")==-1){  //if File does not exist and it is the root folder make hidden file
						IniS.setIniStringValue( strFolderPropertiesFileName, "Properties", "Creation_Date", UtilShared.getCurrentFormattedDate());
						IniS.setIniStringValue( strFolderPropertiesFileName, "Properties", "GrantID", UtilShared.incrementIniGrantCounter()+"");
						Runtime.getRuntime().exec("attrib +H "+strFolderPropertiesFileName); //Hide File
					}
					
					makeFileList(f.getAbsolutePath(), StTemp);  
				} else {
					String AbsoluteFileName = f.getAbsoluteFile().toString().trim() + "";
					//System.out.println( "File:" + AbsoluteFileName );
					if (AbsoluteFileName.toLowerCase().contains("thumbs.db")) {
						System.err.println("Ignore Thumbs.db files");
					} else if (AbsoluteFileName.toLowerCase().contains(
							"desktop.ini")) {
						System.err.println("Ignore desktop.ini");
					} else if (AbsoluteFileName.toLowerCase().contains("~")) {
						System.err.println("Ignore ~ Temp Files");
					} else if (AbsoluteFileName.contains(".FolderProperties.ini")) {
						//System.err.println("FolderProperties.ini");
					} else {
						//Sets the String to find location of ini file
						String FolderNameFull=f.getAbsolutePath().substring(Directory_Location.length()+1);
						String FolderNameSub= FolderNameFull.substring(0, FolderNameFull.indexOf("\\"));
						String FolderPathSub=Directory_Location+"\\"+FolderNameSub;
						String strFolderPropertiesFileNamePath=FolderPathSub+"\\.FolderProperties.ini";
						
						String GrantIDValue =IniS.getIniStringValue(strFolderPropertiesFileNamePath, "Properties", "GrantID");
						
						//If .FolderProperties.ini throw an Exception
						if(GrantIDValue.contains("-1")){
							throw new Exception();
						}
						
						StTemp.insertStringArray(new String[] {
								this.Directory_Location,
								AbsoluteFileName,
								JackSumEngine.mD5File_HEX(AbsoluteFileName),
								GrantIDValue });
						// System.out.println( "\tFile:" + AbsoluteFileName );
					}
				}
			}// end loop

		} catch (Exception e) {
			logg.severe("core","MakeFileList() Could not find = " + path);
			// e.printStackTrace();
		}
		
		if (StopWatchPerformance && IOPerformance)
			System.out.println(this.getClass().getName() + "_MakeFileList("
					+ path + ")="
					+ StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
		return StTemp;
	}// end StringTable MakeFileList()

}//end Class
