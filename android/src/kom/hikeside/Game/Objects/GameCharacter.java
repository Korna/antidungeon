package kom.hikeside.Game.Objects;

import java.io.Serializable;

import kom.hikeside.Models.GameClass;

/**
 * Created by Koma on 05.09.2017.
 */

public class GameCharacter implements Serializable {
    private static final long serialVersionUID = -2163051469151804394L;
    private String key;//java bean - класс, имеющий setter&getter для всех полей
    String name;
    int lvl;

    private int experience;
    private int skillPoints;
    String inventoryKey;//или хранить объект Inventory?

    public BuildStats buildStats;
    public BuildItems buildItems = new BuildItems();

    GameClass gameClass;

    private int maxHp = 100;
    private int maxMp = 50;
    private int maxStamina = 50;

    private int currentHp;
    private int currentMp;



    public GameCharacter(){
        //пустой конструктор для получения данных с FB
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public GameCharacter(String name, int lvl, GameClass gameClass, BuildStats buildStats) {
        this.name = name;
        this.lvl = lvl;
        this.gameClass = gameClass;
        this.buildStats = buildStats;

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


    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(int maxStamina) {
        this.maxStamina = maxStamina;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public void addSkillPoints(int skillPoints){
        this.skillPoints += skillPoints;
    }
    public void decreaseSkillPoint(){
        this.skillPoints--;
    }
    //привязка одного скилла к другому
    //переливание одного скилла в другой
    //обеспечение одного другим
    //подпитывание нескольких


    public int getCurrentHp() {
        return currentHp;
    }
}
