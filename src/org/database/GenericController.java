package org.database;

import org.database.controllers.*;

public class GenericController{
	public DataView data = null;
	public DatabaseEngineEnum DatabaseEngineChoice;
	
	public GenericController(DatabaseEngineEnum Choice) {
		
		if(Choice==DatabaseEngineEnum.MYSQLSINGLE){
			DatabaseEngineChoice=DatabaseEngineEnum.MYSQLSINGLE;
			data=new MySqlGenericController();
		}else if(Choice==DatabaseEngineEnum.MYSQLMULTI){
			DatabaseEngineChoice=DatabaseEngineEnum.MYSQLMULTI;
			data=new MySqlGenericController();
		}else if(Choice==DatabaseEngineEnum.SQLITE4WRAPPER){
			DatabaseEngineChoice=DatabaseEngineEnum.SQLITE4WRAPPER;
			data=new Sqlite4GenericController();
		}
	}
	

	
//Change After Extends
	
	
}
