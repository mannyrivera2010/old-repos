package org.database;

import org.database.controllers.*;
import org.enums.DatabaseEngineEnum;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Database objects.
 */
public class DatabaseFactory{
	
	/** The data. */
	public DataView data = null;
	
	/** The Database engine choice. */
	public DatabaseEngineEnum DatabaseEngineChoice;
	
	/**
	 * Instantiates a new database factory.
	 *
	 * @param Choice the choice
	 */
	public DatabaseFactory(DatabaseEngineEnum Choice) {
		
		if(Choice==DatabaseEngineEnum.MYSQLSINGLE){
			DatabaseEngineChoice=DatabaseEngineEnum.MYSQLSINGLE;
			data=new MySqlGenericController();
		}else if(Choice==DatabaseEngineEnum.MYSQLMULTI){
			DatabaseEngineChoice=DatabaseEngineEnum.MYSQLMULTI;
			data=new MySqlGenericController();
		}else if(Choice==DatabaseEngineEnum.SQLITE4WRAPPER){
			DatabaseEngineChoice=DatabaseEngineEnum.SQLITE4WRAPPER;
			data=new Sqlite4GenericController();
		}else if(Choice==DatabaseEngineEnum.SQLITEGENERICJAR){
			DatabaseEngineChoice=DatabaseEngineEnum.SQLITEGENERICJAR;
			data=new SqliteGenericController();
		}
		
	}	
	
}//end Class
