package org.database.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.text.TextBuilder;

import org.database.DataView;
import org.shared.StringTable;
import org.shared.performance.Timing;

public class MySqlGenericController extends DataView {
	private Connection con = null;
	private Statement st = null;
	private ResultSet rs = null;
	
	public static void main(String[] args) {
		DataView test = new MySqlGenericController();

		//System.out.println(test.getEvents_Directories());
		StringTable History=new StringTable("WorkingDir;;;FileName;;;CheckSum",";;;");
		History.insertStringColumn("XOne;;;add2;;;add3");
		History.insertStringColumn("XOne;;;add2;;;add3");
		History.insertStringColumn("XOne;;;add2;;;add3");
		History.insertStringColumn("XOne;;;add2;;;add3");

		//test.setHistoryTable(History);

		//System.out.println(test.getFinishedGrants());
		//System.out.println(test.getGrantList_DISTINCT_GrantSubFolder());
		//test.mysqltest();
		test.deleteHistoryTableRecordWhere("O:\\Grant%");
	}



	public MySqlGenericController() {
		Timing StopWatch1=new Timing();
		StopWatch1.start();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {		
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();
			st.executeUpdate(super.Sql_History_Table);
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(MySqlGenericController.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(MySqlGenericController.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
		//
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"\tMySqlController()\t"+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));	
	}

	@Override
	protected void genericInsert(String strTableName, StringTable StTable) {
		// TODO Auto-generated method stub
		try {		
			con = DriverManager.getConnection(url, user, password);

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
				PreparedStatement prep = con.prepareStatement(sql);

				for(int jb = 0 ; jb < StTable.getColumnNumber();jb++){
					prep.setString(jb+1,StToData(StTable.getColumnValueArray(jb, i)));
				}
				prep.executeUpdate();
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(MySqlGenericController.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(MySqlGenericController.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}//end finally
	}

	@Override
	protected StringTable genericSelect(String SqlStatement) {
		StringTable StTemp=new StringTable();
		try {

			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();

			ResultSet rs = st.executeQuery(StToData(SqlStatement));

			ResultSetMetaData md = rs.getMetaData();

			int colCount = md.getColumnCount();
			String[] Header = new String[colCount];

			//System.out.println("Number of Column : "+ colCount);		  

			for (int i = 0; i < colCount; i++){
				Header[i] = md.getColumnName(i+1);
			}

			StTemp.setArrColumnHeader(Header, ";;;");

			while (rs.next()) {

				String[] Entry = new String[colCount];

				for (int i = 0; i < colCount; i++) {

					if(rs.getString(i+1)==null){
						Entry[i]="";
						//System.out.println("********");
					}else{
						Entry[i]=DataToSt(rs.getString(i+1));
					}
				}
				StTemp.insertStringArray(Entry);
			}
			rs.close();
			con.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return StTemp;
	}

	@Override
	protected void genericExecute(String strSql) {
		Timing StopWatch1=new Timing();
		StopWatch1.start();
		
		try {
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();

			st.execute(StToData(strSql));
	
			con.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}	
	
	
	public static String StToData(String strSource){
		String TemplateString=strSource.replace("\\", "/");
		return TemplateString.trim();
	}
	
	public static String DataToSt(String strSource){
		String TemplateString=strSource.replace("/", "\\");
		return TemplateString.trim();
	}
	
	
	

}
