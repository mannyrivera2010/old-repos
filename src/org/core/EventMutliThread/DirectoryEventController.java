
package org.core.EventMutliThread;

import javolution.text.TextBuilder;
import javolution.util.FastTable;
import org.database.DatabaseFactory;
import org.enums.DatabaseEngineEnum;
import org.shared.StringTable;
import org.shared.performance.Timing;

// TODO: Auto-generated Javadoc
/**
 * The Class DirectoryEventController.
 */
public class DirectoryEventController {
	
	/** The Stop watch performance. */
	private boolean StopWatchPerformance=false;  //used to turn performance data on and off
	
	//Database 
	/** The Data base choice. */
	private DatabaseEngineEnum DataBaseChoice;
	
	/** The Database. */
	private DatabaseFactory Database = null;
	
	//Creates a FastTable (Arraylist) of DirecoryEventThread
	/** The Ft directory event. */
	private FastTable<DirectoryEventThread> FtDirectoryEvent= new FastTable<DirectoryEventThread>();  
	
	/** The Str global log. */
	private TextBuilder StrGlobalLog= new TextBuilder();  //used to pass log to MainApp (GUI)
	
	
	/**
	 * Instantiates a new directory event controller.
	 *
	 * @param dataBaseChoice the data base choice
	 */
	public DirectoryEventController(DatabaseEngineEnum dataBaseChoice) {
		DataBaseChoice = dataBaseChoice;
		Database=new DatabaseFactory(DataBaseChoice);
	}

	/**
	 * Main Method to start process for all directories
	 * (compare directories, send emails, log to database).
	 */
	public void runActions(){
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		StrGlobalLog.clear();
		
		//Get list of Directories to operate on (compare, send emails, log to database)
		StringTable Events_Directories = Database.data.getEvents_Directories();
		
		FtDirectoryEvent.clear();
		
		//The loops initiates process for each directory
		for(int i =0; i<Events_Directories.getArrLRowsSize();i++){
			String Directory=Events_Directories.getColumnValue("Directory",i);
			String Email_TO=Events_Directories.getColumnValue("Email_TO",i);
			String Email_CC=Events_Directories.getColumnValue("Email_CC",i);
			String Subject=Events_Directories.getColumnValue("Subject",i);
			String Body=Events_Directories.getColumnValue("Body",i);
			
			DirectoryEventThread CurrentDirectoryEvent= new DirectoryEventThread(Directory,Email_TO,Email_CC,Subject,Body,DataBaseChoice);
			if(DataBaseChoice==DatabaseEngineEnum.MYSQLMULTI){
				CurrentDirectoryEvent.start();	//If it is MySql doing Mutlithreading.  
			}else{
				CurrentDirectoryEvent.go();  //for Single Threading
			}
			
			StrGlobalLog.append(CurrentDirectoryEvent.getLog()); //append log for GUI
			FtDirectoryEvent.add(CurrentDirectoryEvent);
		}
		
		if(DataBaseChoice==DatabaseEngineEnum.MYSQLMULTI){
			for (int i = 0; i < FtDirectoryEvent.size(); i++){
				try {
					((Thread) FtDirectoryEvent.get(i)).join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		}

		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_RunActions()="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}
	
	/**
	 * for passing logs to GUI.
	 *
	 * @return the logs
	 */
	public String getLogs(){
		return StrGlobalLog.toString();
	}

}
