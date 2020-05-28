package fr.cotax.coquest.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

public class SqlQuestUtilities {

	private Connection connection;
	
	public SqlQuestUtilities(Connection connection) {
		this.connection = connection;
	}
	
	public void CreateUser(Player player) {
		try {
			PreparedStatement q = connection.prepareStatement("INSERT INTO players(uuid) VALUES (?)");
			q.setString(1,  player.getUniqueId().toString());
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean has_account(Player player) {
		try {
			PreparedStatement q = connection.prepareStatement("SELECT uuid FROM players WHERE uuid = ?");
			q.setString(1,  player.getUniqueId().toString());
			ResultSet resp = q.executeQuery();
			boolean has_acc = resp.next();
			q.close();
			return (has_acc);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}