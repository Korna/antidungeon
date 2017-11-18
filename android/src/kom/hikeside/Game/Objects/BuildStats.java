package kom.hikeside.Game.Objects;

/**
 * Created by Koma on 16.09.2017.
 */

public class BuildStats{
    public BuildStats(){

    }

    public BuildStats(int strength, int agility, int intelligence, int luck, int stamina, int will) {
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.luck = luck;
        this.stamina = stamina;
        this.will = will;
    }

    int strength;//
    int agility;//больше ивентов в день можно посещать
    int intelligence;//больше интеллект = больше опыта

    int luck;
    int stamina;//резист у крону
    int will;//в каком числе ивентов в день может учавствовать?

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
}