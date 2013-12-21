package com.gildorymrp.charactercards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gildorymrp.gildorymclasses.GildorymClasses;

public class SetInfoCommand implements CommandExecutor {

	private GildorymCharacterCards plugin;

	public SetInfoCommand(GildorymCharacterCards plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		GildorymClasses gildorymClasses = (GildorymClasses) Bukkit.getServer().getPluginManager().getPlugin("GildorymClasses");
		if (plugin.getCharacterCards().get(sender.getName()) == null) {
			plugin.getCharacterCards().put(sender.getName(), new CharacterCard(0, Gender.UNKNOWN, "", Race.UNKNOWN, gildorymClasses.levels.get(sender.getName()), gildorymClasses.classes.get(sender.getName())));
		}
		if (args.length >= 1) {
			String info = "";
			for (String arg : args) {
				info += arg + " ";
			}
			plugin.getCharacterCards().get(sender.getName()).setDescription(info);
			sender.sendMessage(ChatColor.GREEN + "Set description.");
		} else {
			sender.sendMessage(ChatColor.RED + "You need to specify some information!");
		}
		return true;
	}

}
