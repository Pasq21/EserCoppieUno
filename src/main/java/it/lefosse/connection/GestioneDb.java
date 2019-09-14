package it.lefosse.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
		List<String> listaContinent = new ArrayList<String>();
		String query = "SELECT distinct Continent from country";
		PreparedStatement ps = getConnection().prepareStatement(query);
		ResultSet result = ps.executeQuery();
		while (result.next()) {
			String name = result.getString(1);
			listaContinent.add(name);
		}
		return listaContinent;
	}

	public static List<String> getStati(String continente) throws SQLException, ClassNotFoundException {
		List<String> stati = new ArrayList<String>();
		String query = "select Name from world.country where Continent=?";
		PreparedStatement ps = getConnection().prepareStatement(query);
		ps.setString(1, continente);
		ResultSet result = ps.executeQuery();
		while (result.next()) {
			String stato = result.getString(1);
			stati.add(stato);
		}
		return stati;
	}

	public static List<String> getCitta(String stato) throws ClassNotFoundException, SQLException {
		List<String> listaCitta = new ArrayList<String>();
		String query = "select Name from world.city where city.CountryCode=(SELECT Code from world.country where Name=?);";
		PreparedStatement ps = getConnection().prepareStatement(query);
		ps.setString(1, stato);
		ResultSet result = ps.executeQuery();
		while (result.next()) {
			String citta = result.getString(1);
			listaCitta.add(citta);
		}
		return listaCitta;

	}

	public static int randomPop() throws ClassNotFoundException, SQLException {
		int max = 0;
		int min = 0;
		String query1 = "select Population from world.city order by Population desc limit 1;";
		PreparedStatement ps = getConnection().prepareStatement(query1);
		ResultSet result = ps.executeQuery();
		if (result.next()) {
			max = result.getInt(1);
		}

		String query2 = "select Population from world.city order by Population asc limit 1;";
		PreparedStatement ps2 = getConnection().prepareStatement(query2);
		ResultSet result2 = ps2.executeQuery();
		if (result2.next()) {
			min = result.getInt(1);
		} 
		int random = ThreadLocalRandom.current().nextInt(min, max + 1);
		return random;
	}

	public static String getCode(String stato) throws ClassNotFoundException, SQLException {
		String code = null;
		String query1 = "select Code from world.country where Name=?;";
		PreparedStatement ps = getConnection().prepareStatement(query1);
		ps.setString(1, stato);
		ResultSet result = ps.executeQuery();
		while (result.next()) {
			code = result.getString(1);
		}
		return code;
	}

	public static void insertCitta(String cittaInserita, String countryCode, String distretto, int popolazione)
			throws ClassNotFoundException, SQLException {
		String query = "INSERT INTO city (Name,CountryCode,District,Population) VALUES (?,?,?,?);";
		PreparedStatement ps = getConnection().prepareStatement(query);
		ps.setString(1, cittaInserita);
		ps.setString(2, countryCode);
		ps.setString(3, distretto);
		ps.setInt(4, popolazione);
		ps.executeUpdate();
	}
}
