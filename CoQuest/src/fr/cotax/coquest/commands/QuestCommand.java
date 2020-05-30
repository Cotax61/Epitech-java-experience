package fr.cotax.coquest.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.cotax.coquest.list.FifthQuestList;
import fr.cotax.coquest.list.FirstQuestList;
import fr.cotax.coquest.list.FourthQuestList;
import fr.cotax.coquest.list.SecondQuestList;
import fr.cotax.coquest.list.ThirdQuestList;
import fr.cotax.coquest.sql.SqlQuestUtilities;

public class QuestCommand implements CommandExecutor {

	public SqlQuestUtilities util;
	private FirstQuestList first;
	private SecondQuestList second;
	private ThirdQuestList third;
	private FourthQuestList fourth;
	private FifthQuestList fifth;

	public QuestCommand(SqlQuestUtilities util) {
		this.util = util;
		first = new FirstQuestList(util);
      	second = new SecondQuestList(util);
//		third = new ThirdQuestList(util);
//		fourth = new FourthQuestList(util);
//		fifth = new FifthQuestList(util);
	}
	
	public ItemMeta setDefaultMessage(ItemMeta meta)
	{
		meta.setDisplayName("§6Aucune quête");
		meta.setLore(Arrays.asList("§eVous n'avez aucune quête sur cet emplacement.", "§eCliquez pour en obtenir une !"));
		return (meta);
	}
	
	public ItemStack CreatePaperSheet(Player player, int quest_id)
	{
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		int player_quest = util.get_quest_id(player, quest_id);

		if (player_quest == 0) {
			meta = setDefaultMessage(meta);
		} else {
			if (quest_id == 1) {
				meta.setDisplayName(first.getQuestName(player_quest));
				meta.setLore(first.getQuestLore(player, player_quest));
			} else if (quest_id == 2) {
				meta.setDisplayName(second.getQuestName(player_quest));
				meta.setLore(second.getQuestLore(player, player_quest));
			}
			item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		item.setItemMeta(meta);
		return item;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player)
		{
			Player player = (Player)sender;
			Inventory menu = Bukkit.createInventory(null, 9, "§7Tâches à réaliser");
			
			menu.setItem(0, CreatePaperSheet(player, 1));
			menu.setItem(1, CreatePaperSheet(player, 2));
			player.openInventory(menu);
		}
		return false;
	}
	
	
}
