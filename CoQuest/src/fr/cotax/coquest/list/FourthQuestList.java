package fr.cotax.coquest.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.cotax.coquest.sql.SqlQuestUtilities;

public class FourthQuestList {
	private SqlQuestUtilities util;
	private List<EntityType> kill_list;
	private List<Material> break_list;
	private List<EntityType> Tame_list;
	
	public String getQuestName(int index)
	{
		switch (index) {
		case 1: return ("§c§lGardien du fort.");
		case 2: return ("§c§lElimination de mastodonte.");
		case 3: return ("§c§lLa source du village.");
		case 4: return ("§c§lAmi agaçant.");
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
		case 2: return (2);
		case 3: return (12);
		case 4: return (1);
		case 5: return (18);
		case 6: return (12);
		case 7: return (24);
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
		case 1: return (Arrays.asList(DifficultyStar(3), QuestDescBorder(1), "§aTuer " + progressNeeded(1) + " Squelettes du wither", "§r" + util.get_quest_progress(player, 4) + "/" + progressNeeded(1), QuestDescBorder(2), "§dRécompense :", "§7§l- §6" + getQuestReward(1) + "\u2726"));
		case 2: return (Arrays.asList(DifficultyStar(5), QuestDescBorder(1), "§aTuer " + progressNeeded(2) + " Ravageurs", "§r" + util.get_quest_progress(player, 4) + "/" + progressNeeded(2), QuestDescBorder(2), "§dRécompense :", "§7§l- §6" + getQuestReward(2) + "\u2726"));
		case 3: return (Arrays.asList(DifficultyStar(5), QuestDescBorder(1), "§aDétruire " + progressNeeded(3) + " Minerais d'émeraude", "§r" + util.get_quest_progress(player, 4) + "/" + progressNeeded(3), QuestDescBorder(2), "§dRécompense :", "§7§l- §6" + getQuestReward(3) + "\u2726"));
		case 4: return (Arrays.asList(DifficultyStar(4), QuestDescBorder(1), "§aApprivoiser " + progressNeeded(4) + " Perroquet", "§r" + util.get_quest_progress(player, 4) + "/" + progressNeeded(4), QuestDescBorder(2), "§dRécompense :", "§7§l- §6" + getQuestReward(4) + "\u2726"));
		default: return null;
		}
	}
	
	public int getQuestReward(int q_index)
	{
		switch (q_index) {
		case 1: return (36);
		case 2: return (80);
		case 3: return (64);
		case 4: return (37);
		default: return 0;
		}
	}

	public FourthQuestList(SqlQuestUtilities util)
	{
		this.util = util;
		kill_list = new ArrayList<EntityType>();
		kill_list.add(EntityType.WITHER_SKELETON);
		kill_list.add(EntityType.RAVAGER);
		
		break_list = new ArrayList<Material>();
		break_list.add(Material.EMERALD_ORE);

		Tame_list = new ArrayList<EntityType>();
		Tame_list.add(EntityType.PARROT);
	}
	
	public void CheckTame(Player player, EntityType Tamed)
	{
		int id = util.get_quest_id(player, 4);
		
		if (id != 4 || player == null || Tamed == null)
			return;
		if (Tamed == EntityType.PARROT) {
			util.change_progress(player, 4, 1);
			check_quest_end(player, id);
		}			
	}
	
	public void CompleteQuest(Player player, int reward, int id)
	{
		player.sendMessage(QuestDescBorder(2));
		player.sendMessage(getQuestName(id));
		player.sendMessage("§eQuête accomplie !");
		player.sendMessage("");
		player.sendMessage("§dRécompense: ");
		player.sendMessage("§7§l- §6 " + getQuestReward(id) + "\u2726");
		player.sendMessage(QuestDescBorder(2));
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 0.7f);
		int set_prog_zero = util.get_quest_progress(player, 4);
		set_prog_zero *= -1;
		util.change_progress(player, 4, set_prog_zero);
		util.change_quest(player, 4, true);
		util.add_points(player, reward);
	}
	
	public void check_quest_end(Player player, int id)
	{
		if (util.get_quest_progress(player, 4) >= progressNeeded(id)) {
			CompleteQuest(player, getQuestReward(id), id);
		}
	}

	public void check_break(Player player, Material mat) 
	{
		int id = util.get_quest_id(player, 4);
		if (!break_list.contains(mat) || player == null || id == 0)
			return;
		else if (mat == Material.EMERALD_ORE && id == 3)
			util.change_progress(player, 4, 1);
		check_quest_end(player, id);
	}
	
	public void check_entity_kill(EntityType type, Player player)
	{
		int id = util.get_quest_id(player, 4);
		if (!kill_list.contains(type) || player == null || id == 0)
			return;
		if (type == EntityType.WITHER_SKELETON && id == 1)
			util.change_progress(player, 4, 1);
		else if (type == EntityType.RAVAGER && id == 2)
			util.change_progress(player, 4, 1);
		check_quest_end(player, id);
	}
}
