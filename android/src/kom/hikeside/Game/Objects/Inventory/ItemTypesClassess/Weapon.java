package kom.hikeside.Game.Objects.Inventory.ItemTypesClassess;

import java.util.ArrayList;

import kom.hikeside.Content.MainItemType;
import kom.hikeside.Game.Objects.Inventory.InventoryObject;

/**
 * Created by Koma on 14.09.2017.
 */

public class Weapon extends InventoryObject {
    int minDamage;
    int maxDamage;
    float extraStunChance;
    float extraHitChance;

    ArrayList<AttackEffect> attackEffect = new ArrayList<>();

    public Weapon(){
        //super(Weapon.class.getSimpleName(), "",   1, MainItemType.Weapon);
    }

    public enum AttackEffect{
        bleed, flame, poison, freeze, stun, stealMp, stealHp, stealSt, lighting
    }
}
