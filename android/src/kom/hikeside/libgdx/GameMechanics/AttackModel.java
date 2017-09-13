package kom.hikeside.libgdx.GameMechanics;

/**
 * Created by Koma on 07.09.2017.
 */

public class AttackModel {
    public int lowestDamage;

    public int highestDamage;

    boolean ignoreArmor;

    public float chanceToHit;

    public AttackModel(int lowestDamage, int highestDamage, boolean ignoreArmor, float chanceToHit) {
        this.lowestDamage = lowestDamage;
        this.highestDamage = highestDamage;
        this.ignoreArmor = ignoreArmor;
        this.chanceToHit = chanceToHit;
    }
}
