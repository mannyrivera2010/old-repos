package org.shared.performance;

import org.core.EventMutliThread.DirectoryEventController;
import org.database.DataView;
import org.enums.DatabaseEngineEnum;
import org.database.DatabaseFactory;
import org.database.controllers.MySqlGenericController;
import org.database.controllers.Sqlite4GenericController;
import org.database.controllers.SqliteGenericController;
import org.shared.StringTable;

// TODO: Auto-generated Javadoc
/**
 * The Class DataTest.
 */
public class DataTest {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		RunMutliVsSingle();
		//RunMain();
		//InsertHistoryTest_Mysql_Sqlite_Sqlite4();
	}//end main
	
	
	/**
	 * Run mutli vs single.
	 */
	public static void RunMutliVsSingle(){
		Timing StopWatch=new Timing();

		double dblCounterSingle=0.0;
		double dblCounterMutli=0.0;
		double dblCounterSqlite=0.0;
		double dblCounterSqliteJar=0.0;

		int cur=1;
		int cur2=cur;
		System.out.println("RunMutliVsSingleEvent");
		
		System.out.println("Times\tMySqlSingle\tMySqlMutli\tSqlite4Single\tSqliteJar");
		
		for(int k=1;k<=10;k++){//Number of total test
			for(int a=0;a<3;a++){//Number per test
				dblCounterSingle=0.0;
				dblCounterMutli=0.0;
				dblCounterSqlite=0.0;
				dblCounterSqliteJar=0.0;
	
				for(int i =0;i<cur;i++){//Increase Times
					StopWatch.start();
					DirectoryEventController Single=new DirectoryEventController(DatabaseEngineEnum.MYSQLSINGLE);
					//Single.RunActions();
					dblCounterSingle+=StopWatch.stop_SecDouble();
					//end Single
					StopWatch.start();
					DirectoryEventController Mutli=new DirectoryEventController(DatabaseEngineEnum.MYSQLMULTI);					
					Mutli.runActions();
					dblCounterMutli+=StopWatch.stop_SecDouble();
					//end Mutli
					StopWatch.start();
					//DirectoryEventMultiController Sqlite=new DirectoryEventMultiController(DatabaseEngine.Sqlite4);					
					//Sqlite.RunActions();
					dblCounterSqlite+=StopWatch.stop_SecDouble();
					//end Mutli
					StopWatch.start();
					//DirectoryEventMultiController SqliteJar=new DirectoryEventMultiController(DatabaseEngine.SqliteGenericJar);					
					//SqliteJar.RunActions();
					dblCounterSqliteJar+=StopWatch.stop_SecDouble();
					//end Mutli
					
					
					
					
				}
				System.out.println(cur+"\t"+dblCounterSingle+"\t"+dblCounterMutli+"\t"+dblCounterSqlite+"\t"+dblCounterSqliteJar);
			}
			cur+=cur2;
		}
	}
	
	
	/*//////////////////////////////////////////**
	 *  InsertHistoryTest_Mysql_Sqlite_Sqlite4
	 */
	
	/**
	 * Insert history test_ mysql_ sqlite_ sqlite4.
	 */
	public static void InsertHistoryTest_Mysql_Sqlite_Sqlite4(){
		Timing StopWatch=new Timing();

		double dblCounterSqliteJar=0.0;
		double dblCounterSqlite4=0.0;
		double dblCounterMySql=0.0;
		
		int cur=50;
		int cur2=cur;
		System.out.println("InsertHistoryTest_Mysql_Sqlite_Sqlite4");
		
		System.out.println("Times\tSqliteJar\tSqlite4\tMySql");
		
		for(int k=1;k<=10;k++){//Number of total test
			for(int a=0;a<5;a++){//Number per test
				dblCounterSqliteJar=0.0;
				dblCounterSqlite4=0.0;
				dblCounterMySql=0.0;
						
				for(int i =0;i<cur;i++){//Increase Times
					//SqliteJar
					StopWatch.start();
					InsertHistoryTest_Sqlite();
					dblCounterSqliteJar+=StopWatch.stop_SecDouble();
					//Sqlite4
					StopWatch.start();
					InsertHistoryTest_Sqlite4();
					dblCounterSqlite4+=StopWatch.stop_SecDouble();
					//MySql
					StopWatch.start();
					InsertHistoryTest_MySql();
					dblCounterMySql+=StopWatch.stop_SecDouble();
				}
				System.out.println(cur+"\t"+dblCounterSqliteJar+"\t"+dblCounterSqlite4+"\t"+dblCounterMySql);
			}
			cur+=cur2;
		}
	}
	
	//Mysql
	/**
	 * Insert history test_ my sql.
	 */
	public static void InsertHistoryTest_MySql(){
		//System.out.println("Start InsertHistoryTest_MySql");
		DatabaseFactory Test=new DatabaseFactory(DatabaseEngineEnum.MYSQLSINGLE);
		StringTable History=new StringTable("WorkingDir;;;FileName;;;CheckSum",";;;");
		History.insertStringColumn("mysql;;;21;;;31");
		History.insertStringColumn("mysql;;;22;;;32");
		History.insertStringColumn("mysql;;;23;;;33");
		History.insertStringColumn("mysql;;;24;;;34");
		History.insertStringColumn("mysql;;;25;;;35");
		Test.data.setHistoryTable(History);
	}
	
	//SqliteJar
	/**
	 * Insert history test_ sqlite.
	 */
	public static void InsertHistoryTest_Sqlite(){
		//System.out.println("Start InsertHistoryTest_Sqlite");
		DatabaseFactory Test=new DatabaseFactory(DatabaseEngineEnum.SQLITEGENERICJAR);
		StringTable History=new StringTable("WorkingDir;;;FileName;;;CheckSum",";;;");
		History.insertStringColumn("Sqlite;;;21;;;31");
		History.insertStringColumn("Sqlite;;;22;;;32");
		History.insertStringColumn("Sqlite;;;23;;;33");
		History.insertStringColumn("Sqlite;;;24;;;34");
		History.insertStringColumn("Sqlite;;;25;;;35");
		Test.data.setHistoryTable(History);
	}
	
	//SqliteJar
	/**
	 * Insert history test_ sqlite4.
	 */
	public static void InsertHistoryTest_Sqlite4(){
		//System.out.println("Start InsertHistoryTest_Sqlite4");
		DatabaseFactory Test=new DatabaseFactory(DatabaseEngineEnum.SQLITE4WRAPPER);
		StringTable History=new StringTable("WorkingDir;;;FileName;;;CheckSum",";;;");
		History.insertStringColumn("Sqlite4;;;21;;;31");
		History.insertStringColumn("Sqlite4;;;22;;;32");
		History.insertStringColumn("Sqlite4;;;23;;;33");
		History.insertStringColumn("Sqlite4;;;24;;;34");
		History.insertStringColumn("Sqlite4;;;25;;;35");
		Test.data.setHistoryTable(History);
	}
	
	/*
	 *  End InsertHistoryTest_Mysql_Sqlite_Sqlite4
	 //////////////////////////////////////////*/
	
	/*//////////////////////////////////////////**
	 *  GetHistoryTest_Mysql_Sqlite_Sqlite4
	 */
	
	/**
	 * Gets the history test_ mysql_ sqlite_ sqlite4.
	 */
	public static void GetHistoryTest_Mysql_Sqlite_Sqlite4(){
		Timing StopWatch=new Timing();

		double dblCounterSqliteJar=0.0;
		double dblCounterSqlite4=0.0;
		double dblCounterMySql=0.0;
		
		int cur=10;
		int cur2=cur;
		
		System.out.println("GetHistoryTest_Mysql_Sqlite_Sqlite4");
		System.out.println("Times\tSqliteJar\tSqlite4\tMySql");
		
		for(int k=1;k<=10;k++){//Number of total test
			for(int a=0;a<1;a++){//Number per test
				dblCounterSqliteJar=0.0;
				dblCounterSqlite4=0.0;
				dblCounterMySql=0.0;
				
				for(int i =0;i<cur;i++){//Increase Times
					//SqliteJar
					StopWatch.start();
					GetHistoryTest_Sqlite();
					dblCounterSqliteJar+=StopWatch.stop_SecDouble();
					//Sqlite4
					StopWatch.start();
					GetHistoryTest_Sqlite4();
					dblCounterSqlite4+=StopWatch.stop_SecDouble();
					//MySql
					StopWatch.start();
					GetHistoryTest_MySql();
					dblCounterMySql+=StopWatch.stop_SecDouble();
				}
				System.out.println(cur+"\t"+dblCounterSqliteJar+"\t"+dblCounterSqlite4+"\t"+dblCounterMySql);
			}
			cur+=cur2;
		}
	}
	
	//Mysql
	/**
	 * Gets the history test_ my sql.
	 */
	public static void GetHistoryTest_MySql(){
		//System.out.println("Start InsertHistoryTest_MySql");
		DataView Test=new MySqlGenericController();
		Test.getHistoryTableWhere("%").getArrLRowsSize();
	}
	
	//SqliteJar
	/**
	 * Gets the history test_ sqlite.
	 */
	public static void GetHistoryTest_Sqlite(){
		//System.out.println("Start InsertHistoryTest_Sqlite");
		DataView Test=new SqliteGenericController();
		Test.getHistoryTableWhere("%").getArrLRowsSize();
	}
	
	//SqliteJar
	/**
	 * Gets the history test_ sqlite4.
	 */
	public static void GetHistoryTest_Sqlite4(){
		//System.out.println("Start InsertHistoryTest_Sqlite4");
		DataView Test=new Sqlite4GenericController();
		Test.getHistoryTableWhere("%").getArrLRowsSize();
	}
	
	/*
	 *  End GetHistoryTest_Mysql_Sqlite_Sqlite4
	 //////////////////////////////////////////*/
	
	
	/*//////////////////////////////////////////**
	 *  InsertHistoryTest_Mysql_Sqlite_Sqlite4
	 */
	
	/**
	 * Grant_ mysql_ sqlite_ sqlite4.
	 */
	public static void Grant_Mysql_Sqlite_Sqlite4(){
		Timing StopWatch=new Timing();

		double dblCounterSqliteJar=0.0;
		double dblCounterSqlite4=0.0;
		double dblCounterMySql=0.0;
		
		int cur=50;
		int cur2=cur;
		System.out.println("InsertHistoryTest_Mysql_Sqlite_Sqlite4");
		
		System.out.println("Times\tSqliteJar\tSqlite4\tMySql");
		
		for(int k=1;k<=10;k++){//Number of total test
			for(int a=0;a<5;a++){//Number per test
				dblCounterSqliteJar=0.0;
				dblCounterSqlite4=0.0;
				dblCounterMySql=0.0;
						
				for(int i =0;i<cur;i++){//Increase Times
					//SqliteJar
					StopWatch.start();
					InsertHistoryTest_Sqlite();
					dblCounterSqliteJar+=StopWatch.stop_SecDouble();
					//Sqlite4
					StopWatch.start();
					InsertHistoryTest_Sqlite4();
					dblCounterSqlite4+=StopWatch.stop_SecDouble();
					//MySql
					StopWatch.start();
					InsertHistoryTest_MySql();
					dblCounterMySql+=StopWatch.stop_SecDouble();
				}
				System.out.println(cur+"\t"+dblCounterSqliteJar+"\t"+dblCounterSqlite4+"\t"+dblCounterMySql);
			}
			cur+=cur2;
		}
	}
	
	
	

	
	
	
	/*
	 *  End InsertHistoryTest_Mysql_Sqlite_Sqlite4
	 //////////////////////////////////////////*/
	
	

	/**
	 * Prints the html grant list.
	 */
	void PrintHtmlGrantList(){
		//OutputHandler.printHTMLGrantListHistory(OutputHandler.EngineOutput.FullHtmlOnly);
	}
	
	
	
	/**
	 * Test2.
	 */
	void test2(){
		//Test for SqliteDriver
		/*
		DataView SqliteController=new SqliteController();
		double dblSqliteController=0.0;
		for(int i =0;i<5000;i++){
			StopWatch.start();
			SqliteController.getGrantList_DISTINCT_GrantSubFolder();
			SqliteController.getFolderFiles_DISTINCT_WorkingDir();
			dblSqliteController+=StopWatch.stop_Double();
		}
		System.out.println(dblSqliteController);
		*/
	}

}
