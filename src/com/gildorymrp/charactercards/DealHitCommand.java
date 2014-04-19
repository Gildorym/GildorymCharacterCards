package com.gildorymrp.charactercards;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gildorymrp.gildorym.Gildorym;
import com.gildorymrp.gildorym.GildorymCharacter;
import com.gildorymrp.gildorym.MySQLDatabase;

public class DealHitCommand implements CommandExecutor {
	private Gildorym gildorym;
	private GildorymCharacterCards plugin;
	private MySQLDatabase sqlDB;

	public DealHitCommand(Gildorym gildorym, GildorymCharacterCards plugin) {
		this.gildorym = gildorym;
		this.plugin = plugin;
		this.sqlDB = gildorym.getMySQLDatabase();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!sender.hasPermission("gildorym.hitother")) {
			sender.sendMessage(ChatColor.RED
					+ "You do not have the permission to deal a hit to another player!");
			return true;
		} else {
			Player player;
			if (args.length < 1) {
				sender.sendMessage("You must specify a player!");
				return true;
			} else { 
				player = plugin.getServer().getPlayer(args[0]);
			}
			if (player == null) {
				sender.sendMessage(ChatColor.RED
						+ "That player does not exist!");
				return true;
			} else {
				GildorymCharacter gChar = gildorym.getActiveCharacters().get(player.getName());
				CharacterCard characterCard = gChar.getCharCard();

				Integer maxHealth = CharacterCard.calculateHealth(gChar);

				characterCard.setHealth(characterCard.getHealth() - 1);
				sqlDB.saveCharacter(gChar);
				Integer newHealth = characterCard.getHealth();

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

				sender.sendMessage(ChatColor.WHITE + player.getDisplayName()
						+ ChatColor.RED + " was dealt a hit! " + ChatColor.WHITE
						+ "(" + healthColor + newHealth + "/" + maxHealth
						+ ChatColor.WHITE + ")");
				player.sendMessage(ChatColor.RED + "You were dealt a hit! "
						+ ChatColor.WHITE + "(" + healthColor + newHealth + "/"
						+ maxHealth + ChatColor.WHITE + ")");
				return true;
			}
		}
	}

}
