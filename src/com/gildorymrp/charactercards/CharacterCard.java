package com.gildorymrp.charactercards;

import java.io.Serializable;

import com.gildorymrp.gildorym.GildorymCharacter;
import com.gildorymrp.gildorymclasses.CharacterClass;

public class CharacterCard implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer age;
	private Gender gender;
	private String description;
	private Race race;
	private Subrace subrace;
	private Integer health;
	private CharacterBehavior behavior;
	private CharacterMorality morality;

	public CharacterCard(Integer age, Gender gender, String description, Race race, Subrace subrace, Integer level, CharacterClass clazz, CharacterBehavior behavior, CharacterMorality morality) {
		this.age = age;
		this.gender = gender;
		this.description = description;
		this.race = race;
		this.subrace = subrace;
		this.health = calculateHealth(clazz, race, level);
		this.behavior = behavior;
		this.morality = morality;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the race
	 */
	public Race getRace() {
		return race;
	}

	/**
	 * @param race the race to set
	 */
	public void setRace(Race race) {
		this.race = race;
	}
	
	public Subrace getSubrace() {
		return subrace;
	}
	
	public void setSubrace(Subrace subrace) {
		this.subrace = subrace;
	}

	/**
	 * @return the health
	 */
	public Integer getHealth() {
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(Integer health) {
		this.health = health;
	}
	
	public CharacterBehavior getBehavior() {
		return behavior;
	}
	
	public void setBehavior(CharacterBehavior behavior) {
		this.behavior = behavior;
	}
	
	public CharacterMorality getMorality() {
		return morality;
	}
	
	public void setMorality(CharacterMorality morality) {
		this.morality = morality;
	}
	
	public static Integer calculateHealth(CharacterClass clazz, Race race, Integer level) {
		Integer health = 5;
		int rate;
		
		if (level == null) {
			level = 0;
		}
		
		if (clazz == null) {
			rate = 100;
		} else {		
			switch (clazz) 
			{
			case BARBARIAN:
				rate = 3;
				break;

			case FIGHTER:
			case PALADIN:
				rate = 4;
				break;

			case MONK:
			case CLERIC:
			case DRUID:
			case RANGER:
				rate = 5;
				break;

			case ROGUE:
			case BARD:
				rate = 6;
				break;

			case WIZARD:
			case SORCERER:
				rate = 7;
				break;

			default:
				rate = 100;
				break;
			}	
		}

		if (race == Race.ELF) {
			health -= 1;
			rate += 1;
		} else if (race == Race.DWARF) {
			health += 1;
			rate -= 1;
		} else if (race == Race.GNOME) {
			health += 1;
			rate -= 1;
		}
		
		health += (int) Math.floor(level / rate);
		
		return health;
	}

	@Override
	public String toString() {
		return "CharacterCard [age=" + age + ", gender=" + gender
				+ ", description=" + description + ", race=" + race
				+ ", health=" + health + ", behavior=" + behavior + ",morality" + morality + "]";
	}

	public static Integer calculateHealth(GildorymCharacter gChar) {
		Integer health = 5;
		Integer level = gChar.getLevel();
		CharacterClass clazz = gChar.getCharClass();
		Race race = gChar.getCharCard().getRace();
		
		int rate;
		
		if (level == null) {
			level = 0;
		}
		
		if (clazz == null) {
			rate = 100;
		} else {		
			switch (clazz) 
			{
			case BARBARIAN:
				rate = 3;
				break;

			case FIGHTER:
			case PALADIN:
				rate = 4;
				break;

			case MONK:
			case CLERIC:
			case DRUID:
			case RANGER:
				rate = 5;
				break;

			case ROGUE:
			case BARD:
				rate = 6;
				break;

			case WIZARD:
			case SORCERER:
				rate = 7;
				break;

			default:
				rate = 100;
				break;
			}	
		}

		if (race == Race.ELF) {
			health -= 1;
			rate += 1;
		} else if (race == Race.DWARF) {
			health += 1;
			rate -= 1;
		} else if (race == Race.GNOME) {
			health += 1;
			rate -= 1;
		}
		
		health += (int) Math.floor(level / rate);
		
		return health;
	}

	
}
