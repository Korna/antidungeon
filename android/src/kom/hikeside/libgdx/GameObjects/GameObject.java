package kom.hikeside.libgdx.GameObjects;

import kom.hikeside.Game.Mechanic.Randomizer;
import kom.hikeside.libgdx.Entities.GameObjectView;
import kom.hikeside.libgdx.GameMechanics.AttackModel;

/**
 * Created by Koma on 09.09.2017.
 */

public class GameObject {
    public boolean turn = true;
    int currentHp;
    int currentMp;
    int currentStamina;

    int maxHp;
    int maxMp;
    int maxStamina;

    AttackModel attackModel;//basic attack

    public GameObjectView view;




    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public int getCurrentMp() {
        return currentMp;
    }

    public void setCurrentMp(int currentMp) {
        this.currentMp = currentMp;
    }

    public int getCurrentStamina() {
        return currentStamina;
    }

    public void setCurrentStamina(int currentStamina) {
        this.currentStamina = currentStamina;
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

    public int getAttackValue(){
        return Randomizer.getAttackValue(attackModel);
    }
}
