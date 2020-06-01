package fr.cotax.coskills.xp;

import java.util.List;

import org.bukkit.entity.EntityType;

public class GetXpBreak {
	
	public List<EntityType> MobList;
	public int xp_per_kill[];
	
	public GetXpBreak() {
		MobList.add(EntityType.PIG);
		xp_per_kill[0] = 8;
		MobList.add(EntityType.CHICKEN);
		xp_per_kill[1] = 6;
		MobList.add(EntityType.RABBIT);
		xp_per_kill[2] = 12;
		MobList.add(EntityType.SHEEP);
		xp_per_kill[3] = 8;
		MobList.add(EntityType.COW);
		xp_per_kill[4] = 8;
		MobList.add(EntityType.COD);
		xp_per_kill[5] = 10;
		MobList.add(EntityType.SALMON);
		xp_per_kill[6] = 10;
		MobList.add(EntityType.PUFFERFISH);
		xp_per_kill[7] = 15;
		MobList.add(EntityType.SQUID);
		xp_per_kill[8] = 8;
		MobList.add(EntityType.TURTLE);
		xp_per_kill[9] = 12;
		MobList.add(EntityType.FOX);
		xp_per_kill[10] = 18;
		MobList.add(EntityType.PANDA);
		xp_per_kill[11] = 24;
		MobList.add(EntityType.POLAR_BEAR);
		xp_per_kill[12] = 24;
		MobList.add(EntityType.CAVE_SPIDER);
		xp_per_kill[13] = 40;
		MobList.add(EntityType.SPIDER);
		xp_per_kill[14] = 35;

		MobList.add(EntityType.ZOMBIE);
		xp_per_kill[15] = 38;
		MobList.add(EntityType.SKELETON);
		xp_per_kill[16] = 43;
		MobList.add(EntityType.STRAY);
		xp_per_kill[17] = 55;
		MobList.add(EntityType.HUSK);
		xp_per_kill[18] = 50;
		MobList.add(EntityType.WITCH);
		xp_per_kill[19] = 75;
		MobList.add(EntityType.CREEPER);
		xp_per_kill[20] = 67;
		MobList.add(EntityType.DROWNED);
		xp_per_kill[21] = 47;
		MobList.add(EntityType.ELDER_GUARDIAN);
		xp_per_kill[22] = 500;
		MobList.add(EntityType.EVOKER);
		xp_per_kill[23] = 210;
		MobList.add(EntityType.GHAST);
		xp_per_kill[24] = 130;
		MobList.add(EntityType.GUARDIAN);
		xp_per_kill[25] = 74;
		MobList.add(EntityType.MAGMA_CUBE);
		xp_per_kill[26] = 35;
		MobList.add(EntityType.PHANTOM);
		xp_per_kill[27] = 78;
		MobList.add(EntityType.PILLAGER);
		xp_per_kill[28] = 68;
		MobList.add(EntityType.RAVAGER);
		xp_per_kill[29] = 500;
		MobList.add(EntityType.SHULKER);
		xp_per_kill[30] = 175;
		MobList.add(EntityType.SLIME);
		xp_per_kill[31] = 20;
		MobList.add(EntityType.VINDICATOR);
		xp_per_kill[32] = 130;
		MobList.add(EntityType.WITHER_SKELETON);
		xp_per_kill[33] = 155;
		MobList.add(EntityType.ENDER_DRAGON);
		xp_per_kill[34] = 10000;
		MobList.add(EntityType.WITHER);
		xp_per_kill[35] = 7500;
	}
	
	public int GetXpForKill(EntityType ent) {
		int idx = MobList.indexOf(ent);
		
		if (idx == -1)
			return (0);
		return (xp_per_kill[idx]);
	}
}
