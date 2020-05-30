package fr.cotax.coquest.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.cotax.coquest.sql.SqlQuestUtilities;

public class SecondQuestList {

	private SqlQuestUtilities util;
	private List<EntityType> kill_list;
	private List<Material> break_list;

	public String getQuestName(int index)
	{
		switch (index) {
		case 1: return ("§c§lBouum !!");	
		case 2: return ("§c§lAu bûcher !");
		case 3: return ("§c§lUn coprs d'acier.");
		default: return ("§c§lUhm... there is no quest N°" + index);
		}
	}
	
	public String DifficultyStar(int dif)
	{
		switch (dif) {
		case 1: return ("§a\u272D§7\u272D\u272D\u272D\u272D");
		case 2: return ("§2\u272D\u272D§7\u272D\u272D\u272D");
		case 3: return ("§e\u272D\u272D\u272D§7\u272D\u272D");
		case 4: return ("§c\u272D\u272D\u272D\u272D§7\u272D");
		case 5: return ("§4\u272D\u272D\u272D\u272D\u272D");
		default: return (null);
		}
	}
	
	public int progressNeeded(int idx)
	{
		switch (idx) {
		case 1: return (10);
		case 2: return (3);
		case 3: return (24);
		default: return (0);
		}
	}
	
	public String QuestDescBorder(int border)
	{
		switch (border) {
		case 1:	return ("§8§n                    ");
		case 2: return ("§8§m                    ");
		default: return (null);
		}
	}
	
	public List<String> getQuestLore(Player player, int index)
	{
		switch (index) {
		case 1: return (Arrays.asList(DifficultyStar(2), QuestDescBorder(1), "§aTuer " + progressNeeded(1) + " Creepers", "§r" + util.get_quest_progress(player, 2) + "/" + progressNeeded(1), QuestDescBorder(2), "§dRécompense :", "§7§l- §6" + getQuestReward(1) + "\u2726"));
		case 2: return (Arrays.asList(DifficultyStar(3), QuestDescBorder(1), "§aTuer " + progressNeeded(2) + " Sorcières", "§r" + util.get_quest_progress(player, 2) + "/" + progressNeeded(2), QuestDescBorder(2), "§dRécompense :", "§7§l- §6" + getQuestReward(2) + "\u2726"));
		case 3: return (Arrays.asList(DifficultyStar(2), QuestDescBorder(1), "§aDétruire " + progressNeeded(3) + " Minerais de fer", "§r" + util.get_quest_progress(player, 2) + "/" + progressNeeded(3), QuestDescBorder(2), "§dRécompense :", "§7§l- §6" + getQuestReward(3) + "\u2726"));
		default: return null;
		}
	}
	
	public int getQuestReward(int q_index)
	{
		switch (q_index) {
		case 1: return (15);
		case 2: return (23);
		case 3: return (13);
		default: return 0;
		}
	}
	
	public SecondQuestList(SqlQuestUtilities util)
	{
		this.util = util;
		kill_list = new ArrayList<EntityType>();
		kill_list.add(EntityType.CREEPER);
		kill_list.add(EntityType.WITCH);

		break_list = new ArrayList<Material>();
		break_list.add(Material.IRON_ORE);
	}
	
	public void CompleteQuest(Player player, int reward, int id)
	{
		String name = getQuestName(id);
		
		player.sendMessage(QuestDescBorder(2));
		player.sendMessage(getQuestName(id));
		player.sendMessage("§eQuête accomplie !");
		player.sendMessage("");
		player.sendMessage("§dRécompense: ");
		player.sendMessage("§7§l- §6 " + getQuestReward(id) + "\u2726");
		player.sendMessage(QuestDescBorder(2));
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 0.7f);
		int set_prog_zero = util.get_quest_progress(player, 1);
		set_prog_zero *= -1;
		util.change_progress(player, 2, set_prog_zero);
		util.change_quest(player, 2, true);
		util.add_points(player, reward);
	}
	
	public void check_quest_end(Player player, int id)
	{
		if (util.get_quest_progress(player, 1) >= progressNeeded(id)) {
			CompleteQuest(player, getQuestReward(id), id);
		}
	}
	
	public void check_break(Player player, Material mat) 
	{
		int id = util.get_quest_id(player, 1);
			if (!break_list.contains(mat) || player == null || id == 0)
			return;
		if (mat == Material.IRON_ORE && id == 3)
			util.change_progress(player, 2, 1);
		check_quest_end(player, id);
	}

	public void check_entity_kill(EntityType type, Player player)
	{
		int id = util.get_quest_id(player, 1);
			if (!kill_list.contains(type) || player == null || id == 0)
			return;
		if (type == EntityType.CREEPER && id == 1)
			util.change_progress(player, 2, 1);
		else if (type == EntityType.WITCH && id == 2)
			util.change_progress(player, 2, 1);
		check_quest_end(player, id);
	}
}