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

public class AddInfoCommand implements CommandExecutor {

	private MySQLDatabase sqlDB;

	public AddInfoCommand(Gildorym gildorym) {
		this.sqlDB = gildorym.getMySQLDatabase();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command!");
			return true;
		}
		Gildorym gildorym = (Gildorym) Bukkit.getServer().getPluginManager().getPlugin("Gildorym");
		if (args.length >= 1) {
			String info = "";
			for (String arg : args) {
				info += arg + " ";
			}
			GildorymCharacter gChar = gildorym.getActiveCharacters().get(sender.getName());
			CharacterCard characterCard = gChar.getCharCard();
			characterCard.setDescription(characterCard.getDescription() + info);
			sqlDB.saveCharacter(gChar);
			sender.sendMessage(ChatColor.GREEN + "Added to description.");
		} else {
			sender.sendMessage(ChatColor.RED + "You need to specify some information!");
		}
		return true;
	}

}
