package org.testing;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

// TODO: Auto-generated Javadoc
/**
 * The Class Sqlite4.
 */
public class Sqlite4 {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Logger.getLogger("com.almworks.sqlite4java").setLevel(Level.OFF);

		try {
			SQLiteConnection db = new SQLiteConnection(new File(
					".\\data\\test.db"));

			db.open(true);

			System.out.println(db.getAutoCommit());
			
			db.exec("CREATE TABLE IF NOT EXISTS test (test1,test2)");
			db.exec("BEGIN IMMEDIATE TRANSACTION");
			
			String sql = "INSERT INTO test VALUES (?,?)";
			
			
			for (int i = 0; i <10; i++) {
				SQLiteStatement st = db.prepare(sql);
				st.bind(1, "H:\\wget"+i);
				st.bind(2, "H:\\wget"+i);

				st.step();

			}
		
			db.exec("COMMIT TRANSACTION");

			db.dispose();
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	/**
	 * Select.
	 */
	public void Select(){
		Logger.getLogger("com.almworks.sqlite4java").setLevel(Level.OFF);

		try {
			SQLiteConnection db = new SQLiteConnection(new File(
					".\\data\\test.db"));

			db.open(true);

			db.exec("CREATE TABLE IF NOT EXISTS History (WorkingDir,FileName,CheckSum)");
			SQLiteStatement st = db
					.prepare("SELECT * FROM History Where Workingdir = ?");

			st.bind(1, "H:\\wget");

			for (int i = 0; i < st.columnCount(); i++) {
				String columnName = st.getColumnName(i);
				System.out.print(columnName + "\t");
			}

			System.out.println();
			while (st.step()) {
				String StrLine="";
				for (int i = 0; i < st.columnCount(); i++) {
					StrLine+=(st.columnString(i))+"\t";
				}
				
			}

			db.dispose();
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
