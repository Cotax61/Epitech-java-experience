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
 		third = new ThirdQuestList(util);
		fourth = new FourthQuestList(util);
		fifth = new FifthQuestList(util);
	}

	public String getQuestLength(int quest_id)
	{
		String length = "§7Durée §8- ";
		
		switch (quest_id) {
		case 1: return (length + "§aTrès courte");
		case 2: return (length + "§2Courte");
		case 3: return (length + "§eMoyenne");
		case 4: return (length + "§6Longue");
		case 5: return (length + "§4Très très longue!");
		default: return (null);
		}
	}
	
	public ItemMeta setDefaultMessage(ItemMeta meta, int quest_id)
	{

		meta.setDisplayName("§6Aucune quête");
		meta.setLore(Arrays.asList("", getQuestLength(quest_id), "", "§eVous n'avez aucune quête sur cet emplacement.", "§eCliquez pour en obtenir une !"));
		return (meta);
	}
	
	public ItemStack CreateQuestItem(Player player, int quest_id)
	{
		Material quest_item_list[] = {Material.PAPER, Material.PAPER, Material.BOOK, Material.BOOK, Material.BOOKSHELF};
		ItemStack item = new ItemStack(quest_item_list[quest_id - 1]);
		ItemMeta meta = item.getItemMeta();
		int player_quest = util.get_quest_id(player, quest_id);

		if (player_quest == 0) {
			meta = setDefaultMessage(meta, quest_id);
		} else {
			if (quest_id == 1) {
				meta.setDisplayName(first.getQuestName(player_quest));
				meta.setLore(first.getQuestLore(player, player_quest));
			} else if (quest_id == 2) {
				meta.setDisplayName(second.getQuestName(player_quest));
				meta.setLore(second.getQuestLore(player, player_quest));
			} else if (quest_id == 3) {
				meta.setDisplayName(third.getQuestName(player_quest));
				meta.setLore(third.getQuestLore(player, player_quest));
			} else if (quest_id == 4) {
				meta.setDisplayName(fourth.getQuestName(player_quest));
				meta.setLore(fourth.getQuestLore(player, player_quest));
			} else if (quest_id == 5) {
				meta.setDisplayName(fifth.getQuestName(player_quest));
				meta.setLore(fifth.getQuestLore(player, player_quest));
			}
			item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack CreateEmptyItem(Material mat)
	{
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		return (item);
	}

	public ItemStack CreateShopItem(Player player)
	{
		ItemStack shop = new ItemStack(Material.CHEST);
		ItemMeta meta = shop.getItemMeta();
		
		meta.setDisplayName("§6§l§nBoutique de quête");
		meta.setLore(Arrays.asList("", "§eEchangez vos §6\u2726 §econtre", "§eDes objets unique !", "", "§eVous possédez : " + util.get_points(player) + "§6\u2726"));
		shop.setItemMeta(meta);
		return (shop);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player)
		{
			Player player = (Player)sender;
			Inventory menu = Bukkit.createInventory(null, 9, "§7Tâches à réaliser");
			
			menu.setItem(0, CreateQuestItem(player, 1));
			menu.setItem(1, CreateQuestItem(player, 2));
			menu.setItem(2, CreateQuestItem(player, 3));
			menu.setItem(3, CreateQuestItem(player, 4));
			menu.setItem(4, CreateQuestItem(player, 5));
			menu.setItem(5, CreateEmptyItem(Material.GRAY_STAINED_GLASS_PANE));
			menu.setItem(6, CreateEmptyItem(Material.GRAY_STAINED_GLASS_PANE));
			menu.setItem(7, CreateEmptyItem(Material.IRON_BARS));
			menu.setItem(8, CreateShopItem(player));
			player.openInventory(menu);
		}
		return false;
	}
	
	
}
