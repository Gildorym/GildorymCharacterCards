package com.gildorymrp.charactercards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.gildorymrp.gildorym.Gildorym;
import com.gildorymrp.gildorym.GildorymCharacter;

public class GildorymCharacterCards extends JavaPlugin {


	//TODO: Add alignment. Hook to classes and prevent classes from having illegal alignments.

	protected static final int level = 0;

	public void onEnable() {

		this.registerListeners(new Listener[] {
				new PlayerInteractEntityListener(),
				new EntityRegainHealthListener(),
		});

		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {	

			@Override
			public void run() {
				Gildorym gildorym = (Gildorym) Bukkit.getServer().getPluginManager().getPlugin("Gildorym");
				for (String playerName : gildorym.getActiveCharacters().keySet()) {
					GildorymCharacter gChar = gildorym.getActiveCharacters().get(playerName);
					Integer maxHealth = CharacterCard.calculateHealth(gChar);
					if (gChar.getCharCard().getHealth() < maxHealth) {
						gChar.getCharCard().setHealth(gChar.getCharCard().getHealth() + 1);
						Player player = GildorymCharacterCards.this.getServer().getPlayer(playerName);
						if (player != null) {
							player.sendMessage(ChatColor.GREEN + "You feel your strength returning! (+1 RP-PVP Health)");
						}
					} else if (gChar.getCharCard().getHealth() > maxHealth) {
						gChar.getCharCard().setHealth(maxHealth);
						Player player = GildorymCharacterCards.this.getServer().getPlayer(playerName);
						if (player != null) {
							player.sendMessage(ChatColor.BLUE + "You feel your strength returning to normal. (Temporary HP Removed)");
						}
					}
				}

				for (Player player : Bukkit.getServer().getOnlinePlayers()) {
					if (player.hasPermission("gildorym.hitother")) {
						player.sendMessage(ChatColor.BLUE + "Automatic HP Regen has occured.");
						player.sendMessage(ChatColor.BLUE + "If currently in an event, you may need to reduce participant HP or restore temporary HP.");
					}
				}
			}

		}, 200L, 345600L);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				for (Player player : Bukkit.getServer().getOnlinePlayers()) {
					if (player.getHealth() < player.getHealth() && player.isSleeping()) {
						player.setHealth(player.getHealth() + Math.min(Math.round((float) player.getMaxHealth() / 20F), player.getMaxHealth() - player.getHealth()));
						player.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You feel a little more refreshed from sleep. +" + Math.floor(Math.min(Math.round((float) player.getMaxHealth() / 20F), player.getMaxHealth() - player.getHealth())) + "HP");
					}
				}
			}

		}, 2400L, 2400L);
		this.getCommand("setage").setExecutor(new SetAgeCommand());
		this.getCommand("setgender").setExecutor(new SetGenderCommand());
		this.getCommand("setrace").setExecutor(new SetRaceCommand());
		this.getCommand("setinfo").setExecutor(new SetInfoCommand());
		this.getCommand("addinfo").setExecutor(new AddInfoCommand());
		this.getCommand("char").setExecutor(new CharCommand());
		this.getCommand("takehit").setExecutor(new TakeHitCommand());
		this.getCommand("dealhit").setExecutor(new DealHitCommand(this));
		this.getCommand("healhit").setExecutor(new HealHitCommand(this));
		this.getCommand("viewhealth").setExecutor(new ViewHealthCommand(this));
	}
	
	private void registerListeners(Listener... listeners) {
		for (Listener listener : listeners) {
			this.getServer().getPluginManager().registerEvents(listener, this);
		}
	}

}
