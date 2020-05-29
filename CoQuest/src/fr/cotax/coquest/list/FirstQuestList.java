package fr.cotax.coquest.list;

import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.cotax.coquest.sql.SqlQuestUtilities;

public class FirstQuestList {

	private SqlQuestUtilities util;
	private List<EntityType> kill_list;
	
	public FirstQuestList(SqlQuestUtilities util)
	{
		this.util = util;
		kill_list.add(EntityType.ZOMBIE);
		kill_list.add(EntityType.SKELETON);
	}
	
	public void check_entity_kill(EntityType type, Player player)
	{
		if (!kill_list.contains(type))
			return;
		if (type == EntityType.ZOMBIE && util.get_quest_id(player, 1) == 1)
			util.change_progress(player, 1, 1);
		if (type == EntityType.SKELETON && util.get_quest_id(player, 1) == 2)
			util.change_progress(player, 1, 1);
	}
}
