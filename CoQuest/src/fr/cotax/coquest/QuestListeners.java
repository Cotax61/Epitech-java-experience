package fr.cotax.coquest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import fr.cotax.coquest.list.FifthQuestList;
import fr.cotax.coquest.list.FirstQuestList;
import fr.cotax.coquest.list.FourthQuestList;
import fr.cotax.coquest.list.SecondQuestList;
import fr.cotax.coquest.list.ThirdQuestList;
import fr.cotax.coquest.sql.SqlQuestUtilities;

public class QuestListeners implements Listener {

	private SqlQuestUtilities sql_util;
	private main main;
	private Plugin plug;
	private FirstQuestList first;
	private SecondQuestList second;
	private ThirdQuestList third;
	private FourthQuestList fourth;
	private FifthQuestList fifth;
	
	public QuestListeners(main main, SqlQuestUtilities tools)
	{
		this.sql_util = tools;
		this.main = main;
		first = new FirstQuestList(sql_util);
		second = new SecondQuestList(sql_util);
		third = new ThirdQuestList(sql_util);
		plug = main.getServer().getPluginManager().getPlugin("CoQuest");
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();		
		if (!sql_util.has_account(p))
			sql_util.CreateUser(p);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		Block b = e.getBlockPlaced();
		b.setMetadata("PlacedBlock", new FixedMetadataValue(main, 1));
	}
	
	@EventHandler
	public void OnHit(EntityDamageByEntityEvent e)
	{
		Player p;
		Arrow a;
		
		if (e.getDamager() instanceof Arrow) {
			a = (Arrow)e.getDamager();
			if (a.getShooter() instanceof Player) {
				p = (Player)a.getShooter();
				second.check_arrow_hit(p);
			}
		}
	}
	
	@EventHandler
	public void onFish(PlayerFishEvent e) {
		Player player = e.getPlayer();
		Entity ent = e.getCaught();
		
		if (ent != null && player != null) {
			first.check_fishing(player);
		}
			
	}

	@EventHandler
	public void onTame(EntityTameEvent e)
	{
		Player player = (Player)e.getOwner();
		
		third.CheckTame(player, e.getEntityType());
	}
	
	@EventHandler
	public void OnDamage(EntityDamageEvent e)
	{
		if (e.getEntity() instanceof Player)
		{
			int dmg = (int)e.getDamage();
			if (dmg == 0)
				dmg = 1;
			second.check_dmg_recieved((Player)e.getEntity(), dmg);
		}
	}
	
	@EventHandler
	public void OnBreak(BlockBreakEvent e)
	{
		Block b = e.getBlock();
		Player p = e.getPlayer();
		
		if (b.hasMetadata("PlacedBlock") || p == null)
			return;
		first.check_break(p, b.getType());
		second.check_break(p, b.getType());
	}

	
	@EventHandler
	public void onMobDeath(EntityDeathEvent e) {
		first.check_entity_kill(e.getEntityType(), e.getEntity().getKiller());
		second.check_entity_kill(e.getEntityType(), e.getEntity().getKiller());
		if (e.getEntity() instanceof Player)
			second.death_reset((Player)e.getEntity());
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		
		InventoryView view = e.getView();
		Player player = (Player)e.getWhoClicked();
		ItemStack current = e.getCurrentItem();

		if (current == null) return;
		
		if (view.getTitle().equalsIgnoreCase("�7T�ches � r�aliser")) {
			e.setCancelled(true);
			if (current.getType() == Material.PAPER && sql_util.get_quest_id(player, e.getSlot() + 1) == 0) {
				sql_util.change_quest(player, e.getSlot() + 1, false);
				view.close();
				Bukkit.getServer().dispatchCommand(player, "quest");
			}
		}
	}
}
