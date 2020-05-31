package fr.cotax.coquest.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.cotax.coquest.sql.SqlQuestUtilities;

public class ShopFrontPage {

	private SqlQuestUtilities util;
	
	public ShopFrontPage(SqlQuestUtilities util) {
		this.util = util;
	}

	public ItemStack CreateEmptyItem(Material mat)
	{
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		return (item);
	}
	
	public void OpenShop(Player player)
	{
		Inventory menu = Bukkit.createInventory(null, 27, "§eBoutique §7| §6" + util.get_points(player) + "\u2726");

		for (int i = 0; i != 10; i++)
			menu.setItem(i, CreateEmptyItem(Material.GRAY_STAINED_GLASS_PANE));
		for (int i = 17; i != 27; i++)
			menu.setItem(i, CreateEmptyItem(Material.GRAY_STAINED_GLASS_PANE));
		player.openInventory(menu);
	}
}
