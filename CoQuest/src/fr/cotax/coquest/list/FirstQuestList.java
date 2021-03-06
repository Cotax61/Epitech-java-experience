package fr.cotax.coquest.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.cotax.coquest.sql.SqlQuestUtilities;

public class FirstQuestList {

	private SqlQuestUtilities util;
	private List<EntityType> kill_list;
	private List<Material> break_list;

	public String getQuestName(int index)
	{
		switch (index) {
		case 1: return ("�c�lChasse aux zombies.");	
		case 2: return ("�c�lChasse aux squelettes.");
		case 3: return ("�c�lBesoins de poissons.");
		case 4: return ("�cAttaque de saumons sauvage.");
		case 5: return ("�cAu fourneau !.");
		case 6: return ("�cCourte s�ssion de minage.");
		case 7: return ("�cTimber !.");
		default: return ("�c�lUhm... there is no quest N�" + index);
		}
	}
	
	public String DifficultyStar(int dif)
	{
		switch (dif) {
		case 1: return ("�a\u272D�7\u272D\u272D\u272D\u272D");
		case 2: return ("�2\u272D\u272D�7\u272D\u272D\u272D");
		case 3: return ("�e\u272D\u272D\u272D�7\u272D\u272D");
		case 4: return ("�c\u272D\u272D\u272D\u272D�7\u272D");
		case 5: return ("�4\u272D\u272D\u272D\u272D\u272D");
		default: return (null);
		}
	}
	
	public int progressNeeded(int idx)
	{
		switch (idx) {
		case 1: return (12);
		case 2: return (10);
		case 3: return (5);
		case 4: return (5);
		case 5: return (32);
		case 6: return (64);
		case 7: return (50);
		default: return (0);
		}
	}
	
	public String QuestDescBorder(int border)
	{
		switch (border) {
		case 1:	return ("�8�n                    ");
		case 2: return ("�8�m                    ");
		default: return (null);
		}
	}
	
	public List<String> getQuestLore(Player player, int index)
	{
		switch (index) {
		case 1: return (Arrays.asList(DifficultyStar(1), QuestDescBorder(1), "�aTuer " + progressNeeded(1) + " Zombies", "�r" + util.get_quest_progress(player, 1) + "/" + progressNeeded(1), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(1) + "\u2726"));
		case 2: return (Arrays.asList(DifficultyStar(1), QuestDescBorder(1), "�aTuer " + progressNeeded(2) + " Squelettes", "�r" + util.get_quest_progress(player, 1) + "/" + progressNeeded(2), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(2) + "\u2726"));
		case 3: return (Arrays.asList(DifficultyStar(1), QuestDescBorder(1), "�aP�che " + progressNeeded(3) + " Objets", "�r" + util.get_quest_progress(player, 1) + "/" + progressNeeded(3), QuestDescBorder(2), "�dR�compense :", "�7�1- �6" + getQuestReward(3) + "\u2726"));
		case 4: return (Arrays.asList(DifficultyStar(1), QuestDescBorder(1), "�aTuer " + progressNeeded(4) + " Saumons", "�r" + util.get_quest_progress(player, 1) + "/" + progressNeeded(4), QuestDescBorder(2), "�dR�compense :", "�7�1- �6" + getQuestReward(4) + "\u2726"));
		case 5: return (Arrays.asList(DifficultyStar(1), QuestDescBorder(1), "�aDetruire " + progressNeeded(5) + " Minerais de charbon", "�r" + util.get_quest_progress(player, 1) + "/" + progressNeeded(5), QuestDescBorder(2), "�dR�compense :", "�7�1- �6" + getQuestReward(5) + "\u2726"));
		case 6: return (Arrays.asList(DifficultyStar(1), QuestDescBorder(1), "�aDetruire " + progressNeeded(6) + " Pierres", "�r" + util.get_quest_progress(player, 1) + "/" + progressNeeded(6), QuestDescBorder(2), "�dR�compense :", "�7�1- �6" + getQuestReward(6) + "\u2726"));
		case 7: return (Arrays.asList(DifficultyStar(1), QuestDescBorder(1), "�aDetruire " + progressNeeded(7) + " B�ches de ch�ne", "�r" + util.get_quest_progress(player, 1) + "/" + progressNeeded(7), QuestDescBorder(2), "�dR�compense :", "�7�1- �6" + getQuestReward(7) + "\u2726"));
		default: return null;
		}
	}
	
	public int getQuestReward(int q_index)
	{
		switch (q_index) {
		case 1: return (4);
		case 2: return (5);
		case 3: return (6);
		case 4: return (4);
		case 5: return (5);
		case 6: return (4);
		case 7: return (6);
		default: return 0;
		}
	}
	
	public FirstQuestList(SqlQuestUtilities util)
	{
		this.util = util;
		kill_list = new ArrayList<EntityType>();
		kill_list.add(EntityType.ZOMBIE);
		kill_list.add(EntityType.SKELETON);
		kill_list.add(EntityType.SALMON);

		break_list = new ArrayList<Material>();
		break_list.add(Material.STONE);
		break_list.add(Material.COAL_ORE);
		break_list.add(Material.OAK_LOG);
	}
	
	public void CompleteQuest(Player player, int reward, int id)
	{
		player.sendMessage(QuestDescBorder(2));
		player.sendMessage(getQuestName(id));
		player.sendMessage("�eQu�te accomplie !");
		player.sendMessage("");
		player.sendMessage("�dR�compense: ");
		player.sendMessage("�7�l- �6 " + getQuestReward(id) + "\u2726");
		player.sendMessage(QuestDescBorder(2));
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 0.7f);
		int set_prog_zero = util.get_quest_progress(player, 1);
		set_prog_zero *= -1;
		util.change_progress(player, 1, set_prog_zero);
		util.change_quest(player, 1, true);
		util.add_points(player, reward);
	}
	
	public void check_quest_end(Player player, int id)
	{
		if (util.get_quest_progress(player, 1) >= progressNeeded(id)) {
			CompleteQuest(player, getQuestReward(id), id);
		}
	}

	public void check_break(Player player, Material mat) {
		int id = util.get_quest_id(player, 1);

		if (!break_list.contains(mat) || player == null || id == 0)
			return;
		if (mat == Material.COAL_ORE && id == 5)
			util.change_progress(player, 1, 1);
		else if (mat == Material.STONE && id == 6)
			util.change_progress(player, 1, 1);
		else if (mat == Material.OAK_LOG && id == 7)
			util.change_progress(player, 1, 1);
		check_quest_end(player, id);
	}
	
	public void check_fishing(Player player) {
		if (player == null)
			return;

		int id = util.get_quest_id(player, 1);
		if (id == 3)
			util.change_progress(player, 1, 1);
		check_quest_end(player, id);
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
		else if (type == EntityType.SALMON && id == 4)
			util.change_progress(player, 1, 1);
		check_quest_end(player, id);
	}
}
