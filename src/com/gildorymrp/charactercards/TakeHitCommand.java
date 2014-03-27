package com.gildorymrp.charactercards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gildorymrp.gildorymclasses.CharacterClass;
import com.gildorymrp.gildorymclasses.GildorymClasses;

public class TakeHitCommand implements CommandExecutor {
	
	private GildorymCharacterCards plugin;
	
	public TakeHitCommand(GildorymCharacterCards plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED
					+ "You must be a player to perform this command!");
			return true;
		} else {
			Player player = (Player) sender;
			GildorymClasses gildorymClasses = (GildorymClasses) Bukkit.getServer().getPluginManager().getPlugin("GildorymClasses");
			CharacterCard characterCard = plugin.getCharacterCards().get(player.getName());
			if (characterCard == null) {
				characterCard = new CharacterCard(0, Gender.UNKNOWN, "", Race.UNKNOWN, gildorymClasses.levels.get(player.getName()), gildorymClasses.classes.get(player.getName()));
				plugin.getCharacterCards().put(player.getName(), characterCard);
			}
			Race race = characterCard.getRace();
			CharacterClass clazz;
			Integer level;
			
			try {
			clazz = gildorymClasses.classes.get(sender.getName());
			level = gildorymClasses.levels.get(sender.getName());
			} catch (Exception ex) {
				clazz = null;
				level = 0;
			}

			Integer maxHealth = CharacterCard.calculateHealth(clazz, race, level);

			plugin.getCharacterCards().get(player.getName()).setHealth(characterCard.getHealth() - 1);
			Integer newHealth = plugin.getCharacterCards().get(player.getName()).getHealth();

			ChatColor healthColor;
			double healthFraction = newHealth / (double) maxHealth;
			if (newHealth >= maxHealth) {
				healthColor = ChatColor.DARK_GREEN;
			} else if (healthFraction >= 0.75) {
				healthColor = ChatColor.GREEN;
			} else if (healthFraction >= 0.5) {
				healthColor = ChatColor.GOLD;
			} else if (healthFraction >= 0.25) {
				healthColor = ChatColor.YELLOW;
			} else if (healthFraction > 0) {
				healthColor = ChatColor.RED;
			} else {
				healthColor = ChatColor.DARK_RED;
			}
			
			GildorymCharacterCards.sendRadiusMessage(player, ChatColor.WHITE + player.getDisplayName()
					+ ChatColor.RED + " took a hit! " + ChatColor.WHITE
					+ "(" + healthColor + newHealth + "/" + maxHealth
					+ ChatColor.WHITE + ")", 24);
			return true;
		}
	}

}
