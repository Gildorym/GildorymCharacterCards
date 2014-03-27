package com.gildorymrp.charactercards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.gildorymrp.gildorymclasses.CharacterClass;
import com.gildorymrp.gildorymclasses.GildorymClasses;

public class PlayerItemConsumeListener {

	GildorymCharacterCards plugin;

	public PlayerItemConsumeListener(GildorymCharacterCards plugin) {
		this.plugin = plugin;
	}	
	public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) {	
		if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			if (event.getPlayer().getItemInHand() != null) {
				if (event.getPlayer().getItemInHand().getType() == Material.POTION && Potion.fromItemStack(event.getItem()).getType() == PotionType.INSTANT_HEAL) {
					Potion potion = Potion.fromItemStack(event.getItem());
					int healingAmount = potion.getLevel();
					Player player = event.getPlayer();
					GildorymClasses gildorymClasses = (GildorymClasses) Bukkit.getServer().getPluginManager().getPlugin("GildorymClasses");
					CharacterCard characterCard = plugin.getCharacterCards().get(player.getName());

					if (characterCard == null) {
						characterCard = new CharacterCard(0, Gender.UNKNOWN, "", Race.UNKNOWN, gildorymClasses.levels.get(player.getName()), gildorymClasses.classes.get(player.getName()));
						plugin.getCharacterCards().put(player.getName(), characterCard);
					}

					CharacterClass clazz = gildorymClasses.classes.get(player.getName());
					Integer level = gildorymClasses.levels.get(player.getName());
					Race race = characterCard.getRace();

					Integer maxHealth = CharacterCard.calculateHealth(clazz, race, level);

					plugin.getCharacterCards().get(player.getName())
					.setHealth(characterCard.getHealth() + healingAmount);
					Integer newHealth = plugin.getCharacterCards()
							.get(player.getName()).getHealth();

					ChatColor healthColor;
					double healthFraction = newHealth / (double) maxHealth;
					if (newHealth >= maxHealth) {
						healthColor = ChatColor.DARK_GREEN;
					} else if (healthFraction >= 0.75) {
						healthColor = ChatColor.GREEN;
					} else if (healthFraction >= 0.5) {
						healthColor = ChatColor.GOLD;
					} else if (healthFraction >= 0.25) {
						healthColor = ChatColor.YELLOW;
					} else if (healthFraction > 0) {
						healthColor = ChatColor.RED;
					} else {
						healthColor = ChatColor.DARK_RED;
					}

					GildorymCharacterCards.sendRadiusMessage(player, ChatColor.WHITE + player.getDisplayName()
							+ ChatColor.GREEN + " has drank a potion and was healed! "
							+ ChatColor.WHITE + "(" + healthColor + newHealth + "/"
							+ maxHealth + ChatColor.WHITE + ")", 24);
				}
			}
		}
	} 
}		
