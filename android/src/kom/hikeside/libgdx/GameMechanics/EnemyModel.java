package kom.hikeside.libgdx.GameMechanics;

/**
 * Created by Koma on 07.09.2017.
 */

public class EnemyModel {
    int hp;
    int lvl;
    int experience;

    String name;
    String description;
    AttackModel attackModel;
    AbilityModel abilityModel;

    public EnemyModel(int hp, int lvl, int experience, String name, String description, AttackModel attackModel, AbilityModel abilityModel) {
        this.hp = hp;
        this.lvl = lvl;
        this.experience = experience;
        this.name = name;
        this.description = description;
        this.attackModel = attackModel;
        this.abilityModel = abilityModel;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
