package it.lefosse.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestioneDb {

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/world?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "dstech";

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		return conn;
	}

	public static List<String> getContinent() throws ClassNotFoundException, SQLException {
		List<String> listaContinent=new ArrayList<String>();
		String query="SELECT distinct Continent from country";
		PreparedStatement ps=getConnection().prepareStatement(query);
		ResultSet result= ps.executeQuery();
		while (result.next()) {
			String name=result.getString(1);
			listaContinent.add(name);
		}
		return listaContinent;
	}
	
	public static List<String> getStati(String continente) throws SQLException, ClassNotFoundException {
		List<String> stati=new ArrayList<String>();
		String query="select Name from world.country where Continent=?";
		PreparedStatement ps=getConnection().prepareStatement(query);
		ps.setString(1, continente);
		ResultSet result= ps.executeQuery();
		while(result.next()) {
			String stato=result.getString(1);
			stati.add(stato);
		}
		return stati;
		
	}
	
}