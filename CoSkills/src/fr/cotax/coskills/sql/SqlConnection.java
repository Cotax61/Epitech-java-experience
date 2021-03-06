package fr.cotax.coskills.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {

	private Connection connection;
	private String url,host,database,user,pass;

	public SqlConnection(String url, String host, String database, String user, String pass) {
		this.url = url;
		this.host = host;
		this.database = database;
		this.user = user;
		this.pass = pass;
	}
	
	public void connection() {
		if (!isConnected()) {
			try {
				connection = DriverManager.getConnection(url + host + "/" + database, user, pass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
				System.out.println("Disconnected from SQL database");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Connection GetConnection() {
		return (this.connection);
	}
	
	public boolean isConnected() {
		return connection != null;
	}
}
