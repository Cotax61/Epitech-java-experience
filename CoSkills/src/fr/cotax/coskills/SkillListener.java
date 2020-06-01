package fr.cotax.coskills;

import org.bukkit.event.Listener;

import fr.cotax.coskills.sql.SqlSkillsUtilities;
import fr.cotax.coskills.xp.XpNeeded;

public class SkillListener implements Listener {
	
	public SqlSkillsUtilities util;
	public XpNeeded xp_need;

	public SkillListener(SqlSkillsUtilities util) {
		this.util = util;
		this.xp_need = new XpNeeded();
	}
	
	
}
