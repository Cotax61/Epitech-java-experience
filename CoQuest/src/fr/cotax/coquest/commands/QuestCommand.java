package fr.cotax.coquest.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.cotax.coquest.sql.SqlQuestUtilities;

public class QuestCommand implements CommandExecutor {

	public SqlQuestUtilities util;

	public QuestCommand(SqlQuestUtilities util) {
		this.util = util;
	}
	
	public ItemMeta setDefaultMessage(ItemMeta meta)
	{
		meta.setDisplayName("$6Aucune quête");
		meta.setLore(Arrays.asList("$eVous n'avez aucune quête sur cet emplacement.", "$eCliquez pour en obtenir une !"));
		return (meta);
	}
	
	public ItemStack CreatePaperSheet(Player player, int quest_id)
	{
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		int player_quest = util.get_quest_id(player, quest_id);
		
		if (player_quest == 0) {
			meta = setDefaultMessage(meta);
			item.setItemMeta(meta);
			return (item);
		} else {
			meta = 
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player)
		{
			Player player = (Player)sender;
			Inventory menu = Bukkit.createInventory(null, 9, "§7Tâches à réaliser");
			
			menu.setItem(0, CreatePaperSheet(player, 1));
		}
		return false;
	}
	
	
}
