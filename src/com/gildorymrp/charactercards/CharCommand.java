package com.gildorymrp.charactercards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gildorymrp.gildorym.Gildorym;
import com.gildorymrp.gildorym.GildorymCharacter;

public class CharCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command!");
			return true;
		}

		sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + ((Player) sender).getDisplayName() + ChatColor.BLUE + ChatColor.BOLD + "'s character card");

		Gildorym gildorym = (Gildorym) Bukkit.getServer().getPluginManager().getPlugin("Gildorym");
		GildorymCharacter gChar = gildorym.getActiveCharacters().get(sender.getName());
		CharacterCard characterCard = gChar.getCharCard();

		Integer maxHealth = CharacterCard.calculateHealth(gChar);
		sender.sendMessage(ChatColor.GRAY + "Health: " + ChatColor.BLUE + characterCard.getHealth() + "/" + maxHealth);
		sender.sendMessage(ChatColor.GRAY + "Age: " + ChatColor.WHITE + characterCard.getAge());
		sender.sendMessage(ChatColor.GRAY + "Gender: " + ChatColor.WHITE + characterCard.getGender().toString());
		sender.sendMessage(ChatColor.GRAY + "Race: " + ChatColor.WHITE + characterCard.getRace().toString());
		sender.sendMessage(ChatColor.GRAY + "Description: " + ChatColor.WHITE + characterCard.getDescription());
		String alignmentMessage = ChatColor.GRAY + "Alignment: " + ChatColor.WHITE;
		if (characterCard.getBehavior() == CharacterBehavior.UNKNOWN || characterCard.getMorality() == CharacterMorality.UNKNOWN) {
			alignmentMessage += "UNKNOWN";
		} else if (characterCard.getBehavior() == CharacterBehavior.NEUTRAL && characterCard.getMorality() == CharacterMorality.NEUTRAL) {
			alignmentMessage += "TRUE NEUTRAL";
		} else {
			alignmentMessage += characterCard.getBehavior() + " " + characterCard.getMorality();
		}
		sender.sendMessage(alignmentMessage);
		return true;
	}

}
