package fr.cotax.coquest;

import java.sql.Connection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fr.cotax.coquest.sql.SqlConnection;
import fr.cotax.coquest.sql.SqlQuestUtilities;

public class main extends JavaPlugin implements Listener {

	public SqlConnection sql = null;
	public SqlQuestUtilities sql_util = null;
	public Connection connect = null;

	@Override
	public void onEnable() {
		sql = new SqlConnection("jdbc:mysql://","localhost","quest_saves","root","");		
		sql.connection();
		connect = sql.GetConnection();
		sql_util = new SqlQuestUtilities(this.connect);
		getServer().getPluginManager().registerEvents(new QuestListeners(this, sql_util), this);
	}
	
	@Override
	public void onDisable() {
		if (sql != null)
			sql.disconnect();
	}
}
