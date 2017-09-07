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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
