package fr.cotax.coquest;

import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.cotax.coquest.sql.SqlQuestUtilities;

public class QuestListeners implements Listener {

	private SqlQuestUtilities sql_util;
	private main main;
	private List<EntityType> quest_1_ent;
	
	public QuestListeners(main main, SqlQuestUtilities tools)
	{
		this.sql_util = tools;
		this.main = main;
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();		
		if (!sql_util.has_account(p))
			sql_util.CreateUser(p);
	}
	
	@EventHandler
	public void onMobDeath(EntityDeathEvent e) {
	}
}
