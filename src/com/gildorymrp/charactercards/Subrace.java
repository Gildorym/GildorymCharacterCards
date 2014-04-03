package com.gildorymrp.charactercards;

public enum Subrace {
	DROW(Race.ELF, true),
	MOON_ELF(Race.ELF, false),
	SUN_ELF(Race.ELF, false),
	WOOD_ELF(Race.ELF, false),
	DARK_ELF(Race.ELF, false),
	DAMARAN(Race.HUMAN, true),
	ILLUSKAN(Race.HUMAN, false),
	MULHORANDHI(Race.HUMAN, false),
	CALISHITE(Race.HUMAN, false),
	CHONDATHANS(Race.HUMAN, false),
	ROCK_GNOME(Race.GNOME, true),
	FOREST_GNOME(Race.GNOME, false),
	DEEP_GNOME(Race.GNOME, false),
	LIGHTFOOT_HALFLING(Race.HALFLING, true),
	STRONGHEART_HALFLING(Race.HALFLING, false),
	HILL_DWARF(Race.DWARF, true),
	SHIELD_DWARf(Race.DWARF, false),
	GREY_DWARF(Race.DWARF, false),
	HALF_ELF(Race.HALFELF, true),
	HALF_ORC(Race.HALFORC, true),
	
	
	UNKNOWN(Race.UNKNOWN, true);
	
	private Race parent;
	private boolean def;
	
	Subrace(Race parentRace) {
		parent = parentRace;
	}
	
	Subrace(Race parentRace, boolean def) {
		this.def = def;
	}
	
	public Race getParentRace() {
		return parent;
	}
	
	public boolean isDefault() {
		return def;
	}
	
	public static Subrace defaultSubrace(Race parent) {
		Subrace[] srs = Subrace.values();

		for(Subrace sr : srs) {
			if(sr.isDefault() && sr.getParentRace() == parent) {
				return sr;
			}
		}
		
		return UNKNOWN;
	}
}
