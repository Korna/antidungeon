package kom.hikeside.Game.Objects;

import java.io.Serializable;

/**
 * Created by Koma on 05.09.2017.
 */

public class GameCharacter implements Serializable {
    private static final long serialVersionUID = -2163051469151804394L;

    String name;
    int lvl;

    int experience;
    int skillPoints;

    GameClass gameClass;

    int strength;
    int agility;
    int intelligence;

    int luck;
    int stamina;//резист у крону
    int will;//в каком числе ивентов в день может учавствовать?

    public GameCharacter(String name, int lvl, GameClass gameClass, int strength, int agility, int intelligence, int luck, int stamina, int will) {
        this.name = name;
        this.lvl = lvl;
        this.gameClass = gameClass;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.luck = luck;
        this.stamina = stamina;
        this.will = will;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getWill() {
        return will;
    }

    public void setWill(int will) {
        this.will = will;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public GameClass getGameClass() {
        return gameClass;
    }

    public void setGameClass(GameClass gameClass) {
        this.gameClass = gameClass;
    }
}
