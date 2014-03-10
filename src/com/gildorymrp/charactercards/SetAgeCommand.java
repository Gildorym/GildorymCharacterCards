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

public class SetAgeCommand implements CommandExecutor {
	private MySQLDatabase sqlDB;

	public SetAgeCommand(Gildorym gildorym) {
		this.sqlDB = gildorym.getMySQLDatabase();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player;
		if (sender instanceof Player) {
			player = (Player) sender;
		} else {
			sender.sendMessage(ChatColor.RED + "Only a player can perform this command!");
			return true;
		}
		Gildorym gildorym = (Gildorym) Bukkit.getServer().getPluginManager().getPlugin("Gildorym");
		GildorymCharacter gChar = gildorym.getActiveCharacters().get(player.getName());
		CharacterCard characterCard = gChar.getCharCard();
		if (args.length >= 1) {
			try {
				characterCard.setAge(Integer.parseInt(args[0]));
				sqlDB.saveCharacter(gChar);
				sender.sendMessage(ChatColor.GREEN + "Set age to " + args[0]);
			} catch (NumberFormatException exception) {
				sender.sendMessage(ChatColor.RED + "You need to specify a number!");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You need to specify an age!");
		}
		return true;
	}

}
