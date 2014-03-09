package com.gildorymrp.charactercards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gildorymrp.gildorym.Gildorym;
import com.gildorymrp.gildorym.GildorymCharacter;	

public class PlayerInteractEntityListener implements Listener {
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (event.getPlayer().isSneaking()) {
			if (event.getRightClicked() instanceof Player) {
				Player player = (Player) event.getRightClicked();
				Gildorym gildorym = (Gildorym) Bukkit.getServer().getPluginManager().getPlugin("Gildorym");
				GildorymCharacter gChar = gildorym.getActiveCharacters().get(player.getName());
				CharacterCard characterCard = gChar.getCharCard();
				Integer maxHealth = CharacterCard.calculateHealth(gChar);
				
				event.getPlayer().sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + player.getDisplayName() + ChatColor.BLUE + ChatColor.BOLD + "'s character card");
				event.getPlayer().sendMessage(ChatColor.GRAY + "Health: " + ChatColor.BLUE + characterCard.getHealth() + "/" + maxHealth);
				event.getPlayer().sendMessage(ChatColor.GRAY + "Age: " + ChatColor.WHITE + characterCard.getAge());
				event.getPlayer().sendMessage(ChatColor.GRAY + "Gender: " + ChatColor.WHITE + characterCard.getGender().toString());
				event.getPlayer().sendMessage(ChatColor.GRAY + "Race: " + ChatColor.WHITE + characterCard.getRace().toString());
				event.getPlayer().sendMessage(ChatColor.GRAY + "Description: " + ChatColor.WHITE + characterCard.getDescription());
				if (event.getPlayer().hasPermission("gildorym.viewalignment")) {
					String alignmentMessage = ChatColor.GRAY + "Alignment: " + ChatColor.WHITE;
					if (characterCard.getBehavior() == CharacterBehavior.UNKNOWN || characterCard.getMorality() == CharacterMorality.UNKNOWN) {
						alignmentMessage += "UNKNOWN";
					} else if (characterCard.getBehavior() == CharacterBehavior.NEUTRAL && characterCard.getMorality() == CharacterMorality.NEUTRAL) {
						alignmentMessage += "TRUE NEUTRAL";
					} else {
						alignmentMessage += characterCard.getBehavior() + " " + characterCard.getMorality();
					}
					event.getPlayer().sendMessage(alignmentMessage);
				}
			}
		}
	}

}
