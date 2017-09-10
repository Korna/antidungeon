package kom.hikeside.libgdx.GameObjects;

/**
 * Created by Koma on 09.09.2017.
 */

public class Killable {
    protected int maxHp = 100;
    protected int maxMp = 50;
    protected int maxStamina = 50;


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
}
