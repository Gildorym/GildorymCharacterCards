package com.gildorymrp.charactercards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gildorymrp.gildorym.Gildorym;
import com.gildorymrp.gildorym.GildorymCharacter;

public class SetInfoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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
			String info = "";
			for (String arg : args) {
				info += arg + " ";
			}
			characterCard.setDescription(info);
			sender.sendMessage(ChatColor.GREEN + "Set description.");
		} else {
			sender.sendMessage(ChatColor.RED + "You need to specify some information!");
		}
		return true;
	}

}
