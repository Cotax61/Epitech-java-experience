package fr.cotax.coquest;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.cotax.coquest.sql.SqlQuestUtilities;

public class PlayerListeners implements Listener {
	
	private SqlQuestUtilities sql_util;
	private main main
	
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();		
		if (!sql_util.has_account(p))
			sql_util.CreateUser(p);
	}

}
