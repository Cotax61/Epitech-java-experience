package fr.cotax.coquest.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Sound;
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
		case 1: return (12);
		case 2: return (10);
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
		case 1: return (Arrays.asList(DifficultyStar(1), QuestDescBorder(1), "§aTuer " + progressNeeded(1) + " Zombies", "§r" + util.get_quest_progress(player, 1) + "/" + progressNeeded(1), QuestDescBorder(2), "§dRécompense :", "§7§l- §6" + getQuestReward(1) + "\u2726"));
		case 2: return (Arrays.asList(DifficultyStar(1), QuestDescBorder(1), "§aTuer " + progressNeeded(2) + " Squelettes", "§r" + util.get_quest_progress(player, 1) + "/" + progressNeeded(2), QuestDescBorder(2), "§dRécompense :", "§7§l- §6" + getQuestReward(2) + "\u2726"));
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
	
	public void CompleteQuest(Player player, int reward, int id)
	{
		String name = getQuestName(id);

		player.sendMessage(QuestDescBorder(1));
		player.sendMessage("§eQuête accomplie !");
		player.sendMessage("");
		player.sendMessage("§dRécompense: ");
		player.sendMessage("§7§l- §6 " + getQuestReward(1) + "\u2726");
		player.sendMessage(QuestDescBorder(2));
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 0.7f);
		util.change_quest(player, 1, true);
		util.add_points(player, reward);
		util.change_progress(player, id, progressNeeded(id) * -1);
	}
	
	public void check_entity_kill(EntityType type, Player player)
	{
		int id = util.get_quest_id(player, 1);

		if (!kill_list.contains(type) || player == null || id == 0)
			return;
		if (type == EntityType.ZOMBIE && id == 1)
			util.change_progress(player, 1, 1);
		else if (type == EntityType.SKELETON && id == 2)
			util.change_progress(player, 1, 1);
		if (util.get_quest_progress(player, 1) >= progressNeeded(id)) {
			CompleteQuest(player, getQuestReward(id), id);
		}
	}
}
