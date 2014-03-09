package com.gildorymrp.charactercards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gildorymrp.gildorym.Gildorym;
import com.gildorymrp.gildorym.GildorymCharacter;
import com.gildorymrp.gildorym.MySQLDatabase;

public class SetRaceCommand implements CommandExecutor {
	private MySQLDatabase sqlDB;
	
	public SetRaceCommand(Gildorym gildorym) {
		this.sqlDB = gildorym.getMySQLDatabase();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Gildorym gildorym = (Gildorym) Bukkit.getServer().getPluginManager().getPlugin("Gildorym");
		
		Player player = null;
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED
					+ "You need to specify a race!");
			return true;
		} else if (args.length == 1) {
			if (sender instanceof Player) {
				player = (Player) sender;
			} else {
				sender.sendMessage(ChatColor.RED
						+ "Only a player can perform this command!");
				return true;
			}
		} else {
			if (!sender.hasPermission("gildorym.setraceother")) {
				sender.sendMessage(ChatColor.RED
						+ "You do not have permission to change another player's race!");
			}
			player = sender.getServer().getPlayer(args[1]);
			if (player == null) {
				sender.sendMessage(ChatColor.RED
						+ "That player does not exist!");
			}
		}
		
		GildorymCharacter gChar = gildorym.getActiveCharacters().get(player.getName());
		CharacterCard characterCard = gChar.getCharCard();
		
		if (characterCard.getRace() != Race.UNKNOWN) {
			if (!sender.hasPermission("gildorym.setraceother")) {
				sender.sendMessage(ChatColor.RED
						+ "You have already set your race!");
			}
		}
		
		try {
			characterCard.setRace(Race.valueOf(args[0].toUpperCase()));
			sender.sendMessage(ChatColor.GREEN + "Set race to " + Race.valueOf(args[0].toUpperCase()).toString());
			sqlDB.saveCharacter(gChar);
		} catch (IllegalArgumentException exception) {
			sender.sendMessage(ChatColor.RED + "That race does not exist!");
			sender.sendMessage(ChatColor.YELLOW + "If the race is a subrace, you may want to choose the main race.");
			sender.sendMessage(ChatColor.YELLOW + "If the race is a special case, such as an event, you may want to choose OTHER or UNKNOWN.");
		}
		return true;
	}

}
