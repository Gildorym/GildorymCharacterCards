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

public class SetGenderCommand implements CommandExecutor {

	private MySQLDatabase sqlDB;

	public SetGenderCommand(Gildorym gildorym) {
		this.sqlDB = gildorym.getMySQLDatabase();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Gildorym gildorym = (Gildorym) Bukkit.getServer().getPluginManager().getPlugin("Gildorym");

		Player player = null;
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED
					+ "You need to specify a gender!");
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
			if (!sender.hasPermission("gildorym.setgenderother")) {
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

		if (characterCard.getGender() != Gender.UNKNOWN) {
			if (!sender.hasPermission("gildorym.setgenderother")) {
				sender.sendMessage(ChatColor.RED
						+ "You have already set your gender!");
			}
		}

		try {
			characterCard.setGender(Gender.valueOf(args[0].toUpperCase()));
			sqlDB.saveCharacter(gChar);
			sender.sendMessage(ChatColor.GREEN + "Set gender to " + Gender.valueOf(args[0].toUpperCase()).toString());
		} catch (IllegalArgumentException exception) {
			sender.sendMessage(ChatColor.RED + "That gender isn't recognized!");
			sender.sendMessage(ChatColor.YELLOW + "If the gender is not standard, i.e. MALE or FEMALE, you may want to choose OTHER or UNKNOWN.");
		}
		return true;
	}

}
