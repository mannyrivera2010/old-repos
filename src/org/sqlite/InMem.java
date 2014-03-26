package org.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InMem {

	public static void main(String[] args) {
		 Connection connection = null;  
	     ResultSet resultSet = null;  
	     Statement statement = null;  

	     try {  
	         Class.forName("org.sqlite.JDBC");  
	         connection = DriverManager.getConnection("jdbc:sqlite::memory:");  
	         statement = connection.createStatement();  
	         
	         
	         statement.execute("CREATE TABLE Persons(P_Id int,LastName varchar(255),FirstName varchar(255),Address varchar(255),City varchar(255));");
	     
	        
	        		 statement.execute("INSERT INTO Persons VALUES (4,'Nilsen', 'Johan', 'Bakken 2', 'Stavanger');");
		   	         statement.execute("INSERT INTO Persons VALUES (5,'Nilsen', 'Johan', 'Bakken 2', 'Stavanger');");
		   	         statement.execute("INSERT INTO Persons VALUES (6,'Nilsen', 'Johan', 'Bakken 2', 'Stavanger');");
		   	         statement.execute("INSERT INTO Persons VALUES (7,'Nilsen', 'Johan', 'Bakken 2', 'Stavanger');");
		   	         statement.execute("INSERT INTO Persons VALUES (8,'Nilsen', 'Johan', 'Bakken 2', 'Stavanger');");
	        	 
	        	
	         
	   	      resultSet = statement.executeQuery("SELECT LastName FROM Persons");  
		         while (resultSet.next()) {  
		             System.out.println("Number of Records:"  
		                     + resultSet.getString("LastName"));  
		         }
	         
	     
	      
	          
	     } catch (Exception e) {  
	         e.printStackTrace();  
	     } finally {  
	         try {  
	             resultSet.close();  
	             statement.close();  
	             connection.close();  
	         } catch (Exception e) {  
	             e.printStackTrace();  
	         }  
	     }  
	     

	}

}
