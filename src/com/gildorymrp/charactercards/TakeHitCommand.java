package com.gildorymrp.charactercards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gildorymrp.gildorym.Gildorym;
import com.gildorymrp.gildorym.GildorymCharacter;

public class TakeHitCommand implements CommandExecutor {

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
		characterCard.setHealth(characterCard.getHealth() - 1);
		sender.sendMessage(ChatColor.RED + "Took a hit!");
		return true;
	}

}
