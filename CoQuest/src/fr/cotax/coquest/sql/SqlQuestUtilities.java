package fr.cotax.coquest.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

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
	
	public int get_points(Player player)
	{
		try {
			PreparedStatement q = connection.prepareStatement("SELECT quest_points FROM players WHERE uuid = ?");
			q.setString(1,  player.getUniqueId().toString());
			int points = 0;
			ResultSet rs = q.executeQuery();
			while (rs.next()) {
				points = rs.getInt("quest_points");
			}
			q.close();
			return (points);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (0);
	}
	
	public boolean add_points(Player player, int amount)
	{
		int points = get_points(player);
		int new_points = points + amount;
		
		try {
			PreparedStatement q = connection.prepareStatement("UPDATE players SET quest_points = ? WHERE uuid = ?");
			q.setInt(1, new_points);
			q.setString(2,  player.getUniqueId().toString());
			q.executeUpdate();
			q.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean remove_points(Player player, int amount)
	{
		int points = get_points(player);
		int new_points = points - amount;
		
		if (new_points < 0)
			return false;
		try {
			PreparedStatement q = connection.prepareStatement("UPDATE players SET quest_points = ? WHERE uuid = ?");
			q.setInt(1, new_points);
			q.setString(1,  player.getUniqueId().toString());
			q.executeUpdate();
			q.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int get_quest_id(Player player, int q_nbr)
	{
		String id_string = "quest_" + q_nbr + "_id";
		
		if (player == null)
			return (0);
		
		try {
			PreparedStatement q = connection.prepareStatement("SELECT " + id_string + " FROM players WHERE uuid = ?");
			q.setString(1, player.getUniqueId().toString());
			int id = 0;
			ResultSet rs = q.executeQuery();
			while (rs.next()) {
				id = rs.getInt(id_string);
			}
			q.close();
			return (id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (0);
	}
	
	public boolean change_quest(Player player, int id, boolean set_to_zero)
	{		
		int max_id_tab[] = {7, 5, 1, 1, 1};
		Random rand = new Random();
		int new_id = rand.nextInt(max_id_tab[id - 1]) + 1;

		String quest_string = "quest_" + id + "_id";
		if (set_to_zero == true)
			new_id = 0;
		try {
			PreparedStatement q = connection.prepareStatement("UPDATE players SET " + quest_string + " = ? WHERE uuid = ?");
			q.setInt(1, new_id);
			q.setString(2,  player.getUniqueId().toString());
			q.executeUpdate();
			q.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public int get_quest_progress(Player player, int q_id)
	{
		String quest_str = "q" + q_id + "_progress";
		
		try {
			PreparedStatement q = connection.prepareStatement("SELECT " + quest_str + " FROM players WHERE uuid = ?");
			q.setString(1, player.getUniqueId().toString());
 			int progress = 0;
			ResultSet rs = q.executeQuery();
			while (rs.next()) {
				progress = rs.getInt(quest_str);
			}
			q.close();
			return progress;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (0);
	}
	
	public boolean change_progress(Player player, int q_id, int prog_added)
	{
		int old_prog = get_quest_progress(player, q_id);
		int new_prog = old_prog + prog_added;
		String quest_str = "q" + q_id + "_progress";

		try {
			PreparedStatement q = connection.prepareStatement("UPDATE players SET " + quest_str + " = ? WHERE uuid = ?");
			q.setInt(1, new_prog);
			q.setString(2,  player.getUniqueId().toString());
			q.executeUpdate();
			q.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;		
	}
}