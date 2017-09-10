package kom.hikeside.libgdx.GameMechanics;

import kom.hikeside.Game.Mechanic.Randomizer;

/**
 * Created by Koma on 07.09.2017.
 */

public class EnemyModel {
    int maxHp;
    int lvl;
    int experience;

    String name;
    String description;
    //AttackModel attackModel;
    AbilityModel abilityModel;

    public EnemyModel(int maxHp, int lvl, int experience, String name, String description, AbilityModel abilityModel) {
        this.maxHp = maxHp;
        this.lvl = lvl;
        this.experience = experience;
        this.name = name;
        this.description = description;
        this.abilityModel = abilityModel;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }


}
