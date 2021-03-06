package fr.cotax.coquest.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.cotax.coquest.sql.SqlQuestUtilities;

public class ThirdQuestList {
	private SqlQuestUtilities util;
	private List<EntityType> kill_list;
	private List<Material> break_list;
	private List<EntityType> Tame_list;
	
	public String getQuestName(int index)
	{
		switch (index) {
		case 1: return ("�c�lUn ami a moustaches.");
		case 2: return ("�c�lS.O.S Ghast");
		case 3: return ("�c�lVous ne passerez pas !");
		case 4: return ("�c�lHomme myst�rieux.");
		case 5: return ("�c�lL'aide de merlin.");
		case 6: return ("�c�lEnvie de brillance.");
		case 7: return ("�c�lDe l'or, des montagnes d'or !");
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
		case 1: return (1);
		case 2: return (2);
		case 3: return (10);
		case 4: return (6);
		case 5: return (18);
		case 6: return (12);
		case 7: return (24);
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
		case 1: return (Arrays.asList(DifficultyStar(3), QuestDescBorder(1), "�aApprivoiser " + progressNeeded(1) + " Chat", "�r" + util.get_quest_progress(player, 3) + "/" + progressNeeded(1), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(1) + "\u2726"));
		case 2: return (Arrays.asList(DifficultyStar(3), QuestDescBorder(1), "�aTuer " + progressNeeded(2) + " Ghasts", "�r" + util.get_quest_progress(player, 3) + "/" + progressNeeded(2), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(2) + "\u2726"));
		case 3: return (Arrays.asList(DifficultyStar(4), QuestDescBorder(1), "�aTuer " + progressNeeded(3) + " Guardiens", "�r" + util.get_quest_progress(player, 3) + "/" + progressNeeded(3), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(3) + "\u2726"));
		case 4: return (Arrays.asList(DifficultyStar(3), QuestDescBorder(1), "�aTuer " + progressNeeded(4) + " Endermans", "�r" + util.get_quest_progress(player, 3) + "/" + progressNeeded(4), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(4) + "\u2726"));
		case 5: return (Arrays.asList(DifficultyStar(2), QuestDescBorder(1), "�aD�truire " + progressNeeded(5) + " Minerais de quartz", "�r" + util.get_quest_progress(player, 3) + "/" + progressNeeded(5), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(5) + "\u2726"));
		case 6: return (Arrays.asList(DifficultyStar(4), QuestDescBorder(1), "�aD�truire " + progressNeeded(6) + " Minerais de diamant", "�r" + util.get_quest_progress(player, 3) + "/" + progressNeeded(6), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(6) + "\u2726"));
		case 7: return (Arrays.asList(DifficultyStar(3), QuestDescBorder(1), "�aD�truire " + progressNeeded(7) + " Minerais d'or", "�r" + util.get_quest_progress(player, 3) + "/" + progressNeeded(7), QuestDescBorder(2), "�dR�compense :", "�7�l- �6" + getQuestReward(7) + "\u2726"));
		default: return null;
		}
	}
	
	public int getQuestReward(int q_index)
	{
		switch (q_index) {
		case 1: return (31);
		case 2: return (30);
		case 3: return (45);
		case 4: return (34);
		case 5: return (24);
		case 6: return (48);
		case 7: return (32);
		default: return 0;
		}
	}

	public ThirdQuestList(SqlQuestUtilities util)
	{
		this.util = util;
		kill_list = new ArrayList<EntityType>();
		kill_list.add(EntityType.GHAST);
		kill_list.add(EntityType.GUARDIAN);
		kill_list.add(EntityType.ENDERMAN);

		break_list = new ArrayList<Material>();
		break_list.add(Material.LAPIS_ORE);
		break_list.add(Material.GOLD_ORE);
		break_list.add(Material.DIAMOND_ORE);

		Tame_list = new ArrayList<EntityType>();
		Tame_list.add(EntityType.CAT);
	}
	
	public void CheckTame(Player player, EntityType Tamed)
	{
		int id = util.get_quest_id(player, 3);
		
		if (id != 1 || player == null || Tamed == null)
			return;
		if (Tamed == EntityType.CAT) {
			util.change_progress(player, 3, 1);
			check_quest_end(player, id);
		}			
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
		int set_prog_zero = util.get_quest_progress(player, 3);
		set_prog_zero *= -1;
		util.change_progress(player, 3, set_prog_zero);
		util.change_quest(player, 3, true);
		util.add_points(player, reward);
	}
	
	public void check_quest_end(Player player, int id)
	{
		if (util.get_quest_progress(player, 3) >= progressNeeded(id)) {
			CompleteQuest(player, getQuestReward(id), id);
		}
	}

	public void check_arrow_hit(Player player)
	{
		int id = util.get_quest_id(player, 3);
		if (player == null || id != 4)
			return;
		util.change_progress(player, 3, 1);
		check_quest_end(player, id);
	}
	
	public void check_break(Player player, Material mat) 
	{
		int id = util.get_quest_id(player, 3);
		if (!break_list.contains(mat) || player == null || id == 0)
			return;
		else if (mat == Material.GOLD_ORE && id == 7)
			util.change_progress(player, 3, 1);
		else if (mat == Material.LAPIS_ORE && id == 5)
			util.change_progress(player, 3, 1);
		else if (mat == Material.DIAMOND_ORE && id == 6)
			util.change_progress(player, 3, 1);
		check_quest_end(player, id);
	}

	public void death_reset(Player p)
	{
		int id = util.get_quest_id(p, 3);
		
		if (id != 5)
			return;
		int set_prog_zero = util.get_quest_progress(p, 2);
		set_prog_zero *= -1;
		util.change_progress(p, 2, set_prog_zero);		
	}
	
	public void check_entity_kill(EntityType type, Player player)
	{
		int id = util.get_quest_id(player, 3);
		if (!kill_list.contains(type) || player == null || id == 0)
			return;
		if (type == EntityType.GHAST && id == 2)
			util.change_progress(player, 3, 1);
		else if (type == EntityType.GUARDIAN && id == 3)
			util.change_progress(player, 3, 1);
		else if (type == EntityType.ENDERMAN && id == 4)
			util.change_progress(player, 3, 1);
		check_quest_end(player, id);
	}
}
