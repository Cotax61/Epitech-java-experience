package fr.cotax.coquest.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.cotax.coquest.sql.SqlQuestUtilities;

public class FirstQuestList {

	private SqlQuestUtilities util;
	private List<EntityType> kill_list;

	public String getQuestName(int index)
	{
		switch (index) {
		case 1: return ("§c§lChasse aux zombies !");	
		case 2: return ("§c§lChasse aux squelettes !");
		default: return ("§c§lUhm... there is no quest N°" + index);
		}
	}
	
	public List<String> getQuestLore(Player player, int index)
	{
		switch (index) {
		case 1: return (Arrays.asList("§eLes zombies sont une vraie peste ici", "§eRend service a la communauté et débarasse toi", "§ede 12 d'entre eux, tu sera payer bien sûr", "", "§cTuer : " + util.get_quest_progress(player, 1) + "/12", "§eRécompense : " + getQuestReward(1)));
		case 2: return (Arrays.asList("$eAh ces squelettes m'énervent avec leurs arc !", "§eTu peux en tuer une dizaine ?", "§eils feront moins les malins après ça !", "", "§cTuer : " + util.get_quest_progress(player, 1) + "/10", "§eRécompense : " + getQuestReward(2)));
		default: return null;
		}
	}
	
	public int getQuestReward(int q_index)
	{
		switch (q_index) {
		case 1: return (12);
		case 2: return (14);
		default: return 0;
		}
	}
	
	public FirstQuestList(SqlQuestUtilities util)
	{
		this.util = util;
		kill_list = new ArrayList<EntityType>();
		kill_list.add(EntityType.ZOMBIE);
		kill_list.add(EntityType.SKELETON);
	}
	
	public void check_entity_kill(EntityType type, Player player)
	{
		if (!kill_list.contains(type) || player == null)
			return;
		if (type == EntityType.ZOMBIE && util.get_quest_id(player, 1) == 1)
			util.change_progress(player, 1, 1);
		if (type == EntityType.SKELETON && util.get_quest_id(player, 1) == 2)
			util.change_progress(player, 1, 1);
	}
}
