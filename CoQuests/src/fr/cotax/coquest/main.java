package fr.cotax.coquest;

import org.bukkit.plugin.java.JavaPlugin;

import fr.cotax.coquest.sql.SqlConnection;

public class main extends JavaPlugin {

	public SqlConnection sql = null;
	
	@Override
	public void onEnable() {
		sql = new SqlConnection("jdbc:mysql://","localhost","quest_saves","root","");
		sql.connection();

//		PluginManager pm = getServer().getPluginManager();
//		getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
	}
	
	@Override
	public void onDisable() {
		if (sql != null)
			sql.disconnect();
	}
}
