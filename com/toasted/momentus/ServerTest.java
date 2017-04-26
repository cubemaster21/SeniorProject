package com.toasted.momentus;
import java.sql.*;
import java.util.Calendar;
public class ServerTest {
	
	public static void main(String[] args)
	{
		try {
		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://rds-momentus.caav2j9g5xby.us-east-1.rds.amazonaws.com:3306/Momentus";

			Class.forName(myDriver);
		
		
			Connection conn = DriverManager.getConnection(myUrl, "EndlessClavicle", "babycham");
			System.out.println("Connected");
			Calendar calendar = Calendar.getInstance();
			java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
			
			String query = "insert into HighScores (Score_Val, levelID) values (?,?);";
			PreparedStatement preparedstmt = conn.prepareStatement(query);
			preparedstmt.setString(1,"233939393");
			preparedstmt.setString(2, "Level/convulution");
			preparedstmt.execute();
			conn.close();
		} 
		catch (Exception e)
		{
			System.err.println("Got and exception!");
			System.err.println(e.getMessage());
		}
		
	}
}
