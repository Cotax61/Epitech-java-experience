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
			q.setString(1,  player.getUniqueId().toString());
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
			PreparedStatement q = connection.prepareStatement("UPDATE  SET quest_points = ? WHERE uuid = ?");
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
		String id_string = "quest_" + q_nbr + "id";
		
		try {
			PreparedStatement q = connection.prepareStatement("SELECT " + id_string + " FROM players WHERE uuid = ?");
			q.setString(1,  player.getUniqueId().toString());
			int points = 0;
			ResultSet rs = q.executeQuery();
			while (rs.next()) {
				points = rs.getInt("");
			}
			q.close();
			return (points);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (0);
	}
	
	public boolean change_quest(Player player, int id)
	{
		int old_id = get_quest_id(player, id);
		int max_id_tab[] = {1, 1, 1, 1, 1};
		Random rand = new Random();
		int new_id = rand.nextInt(max_id_tab[id] - 1) + 1;

		while (old_id == new_id)
			new_id = rand.nextInt(max_id_tab[id] - 1) + 1;
		try {
			PreparedStatement q = connection.prepareStatement("UPDATE SET quest_points = ? WHERE uuid = ?");
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
		String quest_str = "q" + q_id + "progress";
		
		try {
			PreparedStatement q = connection.prepareStatement("SELECT " + quest_str + " FROM players WHERE uuid = ?");
			q.setString(1,  player.getUniqueId().toString());
			q.executeUpdate();
			int progress = 0;
			ResultSet rs = q.executeQuery();
			while (rs.next()) {
				progress = rs.getInt("");
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
		String quest_str = "q" + q_id + "progress";

		try {
			PreparedStatement q = connection.prepareStatement("UPDATE SET " + quest_str + " = ? WHERE uuid = ?");
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