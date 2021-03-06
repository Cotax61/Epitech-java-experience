package fr.cotax.coquest.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.cotax.coquest.sql.SqlQuestUtilities;

public class FifthQuestList {
	private SqlQuestUtilities util;
	private List<EntityType> kill_list;
	private List<Material> break_list;

	public String getQuestName(int index)
	{
		switch (index) {
		case 1: return ("�c�lBunker mobile.");
		case 2: return ("�c�lUsine a charbon.");
		case 3: return ("�c�lTitan de fer.");
		case 4: return ("�c�lOncle piqueur de sous.");
		case 5: return ("�c�lMining diamonds !");
		case 6: return ("�c�lLe temps c'est de l'argent !");
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
		case 1: return (1500);
		case 2: return (2500);
		case 3: return (1750);
		case 4: return (1000);
		case 5: return (350);
		case 6: return (75);
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
		case 1: return (Arrays.asList(DifficultyStar(5), QuestDescBorder(1), "�aTanker " + progressNeeded(1) + " D�g�ts sans mourrir", "�r" + util.get_quest_progress(player, 5) + "/" + progressNeeded(1), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(1) + "\u2726"));
		case 2: return (Arrays.asList(DifficultyStar(5), QuestDescBorder(1), "�aD�truire " + progressNeeded(2) + " Minerais de charbon", "�r" + util.get_quest_progress(player, 5) + "/" + progressNeeded(2), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(2) + "\u2726"));
		case 3: return (Arrays.asList(DifficultyStar(5), QuestDescBorder(1), "�aD�truire " + progressNeeded(3) + " Minerais de fer", "�r" + util.get_quest_progress(player, 5) + "/" + progressNeeded(3), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(3) + "\u2726"));
		case 4: return (Arrays.asList(DifficultyStar(5), QuestDescBorder(1), "�aD�truire " + progressNeeded(4) + " Minerais d'or", "�r" + util.get_quest_progress(player, 5) + "/" + progressNeeded(4), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(4) + "\u2726"));
		case 5: return (Arrays.asList(DifficultyStar(5), QuestDescBorder(1), "�aD�truire " + progressNeeded(5) + " Minerais de diamant", "�r" + util.get_quest_progress(player, 5) + "/" + progressNeeded(5), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(5) + "\u2726"));
		case 6: return (Arrays.asList(DifficultyStar(5), QuestDescBorder(1), "�aD�truire " + progressNeeded(6) + " Minerais d'�meraude", "�r" + util.get_quest_progress(player, 5) + "/" + progressNeeded(6), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(6) + "\u2726"));
		default: return null;
		}
	}
	
	public int getQuestReward(int q_index)
	{
		switch (q_index) {
		case 1: return (250);
		case 2: return (175);
		case 3: return (183);
		case 4: return (190);
		case 5: return (280);
		case 6: return (267);
		default: return 0;
		}
	}

	public FifthQuestList(SqlQuestUtilities util)
	{
		this.util = util;
		kill_list = new ArrayList<EntityType>();

		break_list = new ArrayList<Material>();
		break_list.add(Material.COAL_ORE);
		break_list.add(Material.IRON_ORE);
		break_list.add(Material.GOLD_ORE);
		break_list.add(Material.DIAMOND_ORE);
		break_list.add(Material.EMERALD_ORE);
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
		int set_prog_zero = util.get_quest_progress(player, 5);
		set_prog_zero *= -1;
		util.change_progress(player, 5, set_prog_zero);
		util.change_quest(player, 5, true);
		util.add_points(player, reward);
	}
	
	public void check_quest_end(Player player, int id)
	{
		if (util.get_quest_progress(player, 5) >= progressNeeded(id)) {
			CompleteQuest(player, getQuestReward(id), id);
		}
	}

	public void check_break(Player player, Material mat) 
	{
		int id = util.get_quest_id(player, 5);
			if (!break_list.contains(mat) || player == null || id == 0)
			return;
		if (mat == Material.COAL_ORE && id == 2)
			util.change_progress(player, 5, 1);
		else if (mat == Material.IRON_ORE && id == 3)
			util.change_progress(player, 5, 1);
		else if (mat == Material.GOLD_ORE && id == 4)
			util.change_progress(player, 5, 1);
		else if (mat == Material.DIAMOND_ORE && id == 5)
			util.change_progress(player, 5, 1);
		else if (mat == Material.EMERALD_ORE && id == 6)
			util.change_progress(player, 5, 1);
		check_quest_end(player, id);
	}

	public void death_reset(Player p)
	{
		int id = util.get_quest_id(p, 5);
		
		if (id != 1)
			return;
		int set_prog_zero = util.get_quest_progress(p, 5);
		set_prog_zero *= -1;
		util.change_progress(p, 5, set_prog_zero);		
	}
	
	public void check_dmg_recieved(Player player, int damages)
	{
		int id = util.get_quest_id(player, 5);
		
		if (id != 1)
			return;
		util.change_progress(player, 5, damages);
		check_quest_end(player, id);
	}
	
	public void check_entity_kill(EntityType type, Player player)
	{
		int id = util.get_quest_id(player, 2);
		if (!kill_list.contains(type) || player == null || id == 0)
			return;
		if (type == EntityType.CREEPER && id == 1)
			util.change_progress(player, 2, 1);
		else if (type == EntityType.WITCH && id == 2)
			util.change_progress(player, 2, 1);
		check_quest_end(player, id);
	}
}
