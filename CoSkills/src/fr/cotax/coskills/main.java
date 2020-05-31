package fr.cotax.coskills;

import java.sql.Connection;

import org.bukkit.plugin.java.JavaPlugin;

import fr.cotax.coskills.sql.SqlConnection;
import fr.cotax.coskills.sql.SqlSkillsUtilities;

public class main extends JavaPlugin {

	public SqlConnection sql = null;
	public SqlSkillsUtilities sql_util = null;
	public Connection connect = null;
	
	@Override
	public void onEnable() {
		sql = new SqlConnection("jdbc:mysql://","localhost","skills_save","root","");		
		sql.connection();
		connect = sql.GetConnection();
		sql_util = new SqlSkillsUtilities(this.connect);
		getServer().getPluginManager().registerEvents(new SkillListener(sql_util), this);
	}
	
	@Override
	public void onDisable() {
		if (sql != null)
			sql.disconnect();
	}
	
}
