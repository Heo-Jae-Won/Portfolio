package model;
import java.sql.*;

public class Database {
	public static Connection CON;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			CON=DriverManager.getConnection("jdbc:mysql://localhost:3306/edudb","edu","pass");
			System.out.println("DB connected");
		}catch(Exception e) {
			System.out.println("DB not connected:" + e.toString());
		}
	}
}
