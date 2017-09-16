package kom.hikeside.Game.Objects.Inventory;

import java.util.ArrayList;

import kom.hikeside.Content.MainItemType;

/**
 * Created by Koma on 16.08.2017.
 */

public class Armour extends InventoryObject {
    int absorbDamage;//aka defence
    float extraAvoidChance;
    float extraBlockChance;
    ArrayList<ArmorEffect> armorEffects = new ArrayList<>();


    public Armour(){

    }

    public Armour(int absorbDamage, float extraAvoidChance, float extraBlockChance, ArrayList<ArmorEffect> armorEffects) {
      //  super(Armour.class.getSimpleName(), "sample description",   1, MainItemType.Armour);
        this.absorbDamage = absorbDamage;
        this.extraAvoidChance = extraAvoidChance;
        this.extraBlockChance = extraBlockChance;
        this.armorEffects = armorEffects;
    }

    public Armour(String name, String description, int levelRequered, MainItemType type, int absorbDamage, float extraAvoidChance, float extraBlockChance, ArrayList<ArmorEffect> armorEffects) {
       // super(name, description, levelRequered, type);
        this.absorbDamage = absorbDamage;
        this.extraAvoidChance = extraAvoidChance;
        this.extraBlockChance = extraBlockChance;
        this.armorEffects = armorEffects;
    }

    public enum ArmorEffect{
        reflectAttackEffectToSomeone, useMpInsteadOfHp, useStInsteadOfHp, noStun, curseAttacker
    }
}
