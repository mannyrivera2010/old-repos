package org.database.controllers;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.text.TextBuilder;

import org.database.DataView;
import org.shared.StringTable;
import org.shared.Util;
import org.shared.performance.Timing;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

// TODO: Auto-generated Javadoc
/**
 * The Class Sqlite4GenericController.
 */
public class Sqlite4GenericController extends DataView {	

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		StringTable Test=new StringTable("Col1;;;Col2",";;;");
		Test.insertStringColumn("1data1-;;;1data2-");
		Test.insertStringColumn("2data1-;;;2data2-");
		Test.insertStringColumn("3data1-;;;3data2-");
		
		DataView data=new Sqlite4GenericController();
		
		System.out.println(data.getHistoryTableWhere("%"));
	}
	
	
	/**
	 * Instantiates a new sqlite4 generic controller.
	 */
	public Sqlite4GenericController() {
		Util UtilShared = new Util();
		Timing StopWatch1=new Timing();
		StopWatch1.start();

		UtilShared.folderExistAndMake("data");
		try {
			Logger.getLogger("com.almworks.sqlite4java").setLevel(LogLevel);
			SQLiteConnection db = new SQLiteConnection(new File(DatabaseFilePath));
			db.open(true);	
			db.exec(super.Sql_Events_Table);
			db.exec(super.Sql_FinishedGrants_Table);
			db.exec(super.Sql_History_Table);
			db.exec(super.Sql_MainLog_Table);
			
			db.dispose();
			
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tSqlite4Controller()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));	
	}
	
	/*
	 * Generic Execute
	 */	
	/* (non-Javadoc)
	 * @see org.database.DataView#genericExecute(java.lang.String)
	 */
	@Override
	public void genericExecute(String StrSQLStatement){
		//Timing StopWatch1=new Timing();
		//StopWatch1.start();
		try {
			Logger.getLogger("com.almworks.sqlite4java").setLevel(LogLevel);
			SQLiteConnection db = new SQLiteConnection(new File(DatabaseFilePath));
			db.open(true);

			db.exec(StrSQLStatement);
			
			db.dispose();
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
		//System.out.println(this.getClass().getName()+"_getHistoryTableWhere()="+StopWatch1.Min_Sec_ms(StopWatch1.stop_Double()));
	}

	/*
	 * Generic Execute
	 */	
	/* (non-Javadoc)
	 * @see org.database.DataView#genericSelect(java.lang.String)
	 */
	@Override
	public StringTable genericSelect(String StrSQLStatment){
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		StringTable stTemp= new StringTable();
		try {
			Logger.getLogger("com.almworks.sqlite4java").setLevel(LogLevel);
			SQLiteConnection db = new SQLiteConnection(new File(DatabaseFilePath));
			db.open(true);

			SQLiteStatement st = db
					.prepare(StrSQLStatment);
			


			int tbcolumnCount=st.columnCount();
			String[] Header = new String[tbcolumnCount];
			
			for (int i = 0; i < st.columnCount(); i++) {
				Header[i]=st.getColumnName(i);
			}

			stTemp.setArrColumnHeader(Header, ";;;");
			while (st.step()) {
				String[] Entry = new String[tbcolumnCount];
		
				for (int i = 0; i < st.columnCount(); i++) {
					
					if(st.columnNull(i)){
						Entry[i]="";
						//System.out.println("********");
					}else{
						//System.out.println(st.columnString(i));
						Entry[i]=st.columnString(i);
					}
					
				}
				stTemp.insertStringArray(Entry);
			}
			db.dispose();
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
		return stTemp;
	}

	/*
	 * Generic Execute
	 */	
	/* (non-Javadoc)
	 * @see org.database.DataView#genericInsert(java.lang.String, org.shared.StringTable)
	 */
	@Override
	public void genericInsert(String strTableName,StringTable StTable){
		try {
			Logger.getLogger("com.almworks.sqlite4java").setLevel(LogLevel);
			SQLiteConnection db = new SQLiteConnection(new File(DatabaseFilePath));
			db.open(true);		
			
		    db.exec("BEGIN IMMEDIATE TRANSACTION");
			
		    TextBuilder ColQues= new TextBuilder("INSERT INTO ");
			ColQues.append(strTableName);
			ColQues.append(" VALUES (");
			for(int jaa = 0 ; jaa < StTable.getColumnNumber();jaa++){
				if(jaa < StTable.getColumnNumber()-1){
					ColQues.append("?,");
					
				}else{
					ColQues.append("?");
				}
			}
			ColQues.append(");");			
			String sql = ColQues.toString();
			
			for (int i = 0; i <StTable.getArrLRowsSize(); i++) {
				SQLiteStatement st = db.prepare(sql);
				
				for(int jb = 0 ; jb < StTable.getColumnNumber();jb++){
					st.bind(jb+1,StTable.getColumnValueArray(jb, i));
				}

				st.step();
			
			}
			db.exec("COMMIT TRANSACTION");
			db.dispose();
			//org.shared.performance.JvmMemory.SeeMemUsage();
			
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
	}
	
}//end class
