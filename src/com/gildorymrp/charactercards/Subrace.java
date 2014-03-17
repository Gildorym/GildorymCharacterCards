package com.gildorymrp.charactercards;

public enum Subrace {
	DROW(Race.ELF, true),
	
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
