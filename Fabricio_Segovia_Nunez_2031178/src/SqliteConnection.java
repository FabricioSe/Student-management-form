import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class SqliteConnection {

	public static Connection dbConnector() {
		
		try {
			
			// First we need to introduce JDBC
			Class.forName("org.sqlite.JDBC");
			// Passing the address of the database
			Connection conn = DriverManager.getConnection("jdbc:sqlite:Studentdb.db");
			
			JOptionPane.showMessageDialog(null, "Connection Succesfully Established");
			
			return conn;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			
			JOptionPane.showMessageDialog(null, e);
			return null; // Return null in case there is an exception 
			
		}
		
	}
	
}
