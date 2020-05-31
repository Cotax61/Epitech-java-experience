package fr.cotax.coskills;

import org.bukkit.event.Listener;

import fr.cotax.coskills.sql.SqlSkillsUtilities;

public class SkillListener implements Listener {
	
	public SqlSkillsUtilities util;
	
	public SkillListener(SqlSkillsUtilities util) {
		this.util = util;
	}
	
	
}
