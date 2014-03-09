package com.gildorymrp.charactercards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gildorymrp.gildorym.Gildorym;
import com.gildorymrp.gildorym.GildorymCharacter;

public class HealHitCommand implements CommandExecutor {

	GildorymCharacterCards plugin;

	public HealHitCommand(GildorymCharacterCards plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!sender.hasPermission("gildorym.hitother")) {
			sender.sendMessage(ChatColor.RED
					+ "You do not have the permission to heal another player's hits!");
			return true;
		} else {
			Player player;
			if (args.length < 1) {
				if (sender instanceof Player) {
					player = (Player) sender;
				} else {
					sender.sendMessage("You must specify a player!");
					return true;
				}
			} else { 
				player = plugin.getServer().getPlayer(args[0]);
			}
			if (player == null) {
				sender.sendMessage(ChatColor.RED
						+ "That player does not exist!");
				return true;
			} else {
				Gildorym gildorym = (Gildorym) Bukkit.getServer().getPluginManager().getPlugin("Gildorym");
				GildorymCharacter gChar = gildorym.getActiveCharacters().get(player.getName());
				CharacterCard characterCard = gChar.getCharCard();
				
				Integer maxHealth = CharacterCard.calculateHealth(gChar);

				characterCard.setHealth(characterCard.getHealth() + 1);
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
						+ ChatColor.GREEN + " was healed one hit! "
						+ ChatColor.WHITE + "(" + healthColor + newHealth + "/"
						+ maxHealth + ChatColor.WHITE + ")");
				player.sendMessage(ChatColor.GREEN + "You were healed one hit! "
						+ ChatColor.WHITE + "(" + healthColor + newHealth + "/"
						+ maxHealth + ChatColor.WHITE + ")");
				return true;
			}
		}
	}

}
