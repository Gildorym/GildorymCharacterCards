package com.gildorymrp.charactercards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gildorymrp.gildorym.Gildorym;

public class AddInfoCommand implements CommandExecutor {

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
			CharacterCard characterCard = gildorym.getActiveCharacters().get(sender.getName()).getCharCard();
			characterCard.setDescription(characterCard.getDescription() + info);
			sender.sendMessage(ChatColor.GREEN + "Added to description.");
		} else {
			sender.sendMessage(ChatColor.RED + "You need to specify some information!");
		}
		return true;
	}

}
