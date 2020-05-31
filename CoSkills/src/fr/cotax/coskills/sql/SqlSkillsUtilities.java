package fr.cotax.coskills.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

public class SqlSkillsUtilities {

	Connection connection;
	
	public SqlSkillsUtilities(Connection connect)
	{
		this.connection = connect;
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
	
	public boolean hasAccount(Player player)
	{
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
	
	public int get_skill_level(Player player, String skill)
	{
		try {
			PreparedStatement q = connection.prepareStatement("SELECT " + skill + " FROM levels WHERE uuid = ?");
			q.setString(1,  player.getUniqueId().toString());
			int level = 0;
			ResultSet rs = q.executeQuery();
			while (rs.next()) {
				level = rs.getInt(skill);
			}
			q.close();
			return (level);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (0);
	}

	public int get_skill_xp(Player player, String skill)
	{
		try {
			PreparedStatement q = connection.prepareStatement("SELECT " + skill + " FROM xp WHERE uuid = ?");
			q.setString(1,  player.getUniqueId().toString());
			int xp = 0;
			ResultSet rs = q.executeQuery();
			while (rs.next()) {
				xp = rs.getInt(skill);
			}
			q.close();
			return (xp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (0);
	}	
}
