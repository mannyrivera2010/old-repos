package org.database.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javolution.text.TextBuilder;

import org.database.DataView;
import org.shared.StringTable;
import org.shared.Util;
import org.shared.performance.Timing;

// TODO: Auto-generated Javadoc
/**
 * The Class SqliteGenericController.
 */
public class SqliteGenericController extends DataView {

	/**
	 * Instantiates a new sqlite generic controller.
	 */
	public SqliteGenericController() {
		Util UtilShared = new Util();
		Timing StopWatch1=new Timing();
		StopWatch1.start();

		UtilShared.folderExistAndMake("data");
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);

			Statement stat = conn.createStatement();

			int a = stat.executeUpdate(super.Sql_Events_Table);
			stat.executeUpdate(super.Sql_FinishedGrants_Table);
			stat.executeUpdate(super.Sql_History_Table);
			stat.executeUpdate(super.Sql_MainLog_Table);
		
			conn.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StopWatchPerformance)System.out.println(this.getClass().getName()+"_SqliteController()="+StopWatch1.sec_ms(StopWatch1.stop_SecDouble()));
	}

	/*
	 * Generic
	 */	
	/* (non-Javadoc)
	 * @see org.database.DataView#genericInsert(java.lang.String, org.shared.StringTable)
	 */
	@Override
	protected void genericInsert(String strTableName, StringTable StTable) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"+DatabaseFilePath);

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
			//System.out.println(sql);

			PreparedStatement prep = conn.prepareStatement(sql);

			for (int i = 0; i <StTable.getArrLRowsSize(); i++) {
				for(int jb = 0 ; jb < StTable.getColumnNumber();jb++){
					prep.setString(jb+1,StTable.getColumnValueArray(jb, i));
				}
				prep.executeUpdate();
			}
			
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}


	}

	/* (non-Javadoc)
	 * @see org.database.DataView#genericSelect(java.lang.String)
	 */
	@Override
	protected StringTable genericSelect(String SqlStatement) {		
		StringTable StTemp=new StringTable();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"+DatabaseFilePath);
			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery(SqlStatement);

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
						Entry[i]=rs.getString(i+1);
						//System.out.println(rs.getString(i+1));
					}

				}
				StTemp.insertStringArray(Entry);


			}
			rs.close();
			conn.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return StTemp;
	}

	/* (non-Javadoc)
	 * @see org.database.DataView#genericExecute(java.lang.String)
	 */
	@Override
	protected void genericExecute(String strSql) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+DatabaseFilePath);

			Statement stat = conn.createStatement();

			stat.execute(strSql);

			conn.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
