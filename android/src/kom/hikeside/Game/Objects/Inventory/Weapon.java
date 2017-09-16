package kom.hikeside.Game.Objects.Inventory;

import java.util.ArrayList;

import kom.hikeside.Content.MainItemType;

/**
 * Created by Koma on 14.09.2017.
 */

public class Weapon extends InventoryObject  {
    int minDamage;
    int maxDamage;
    ArrayList<AttackEffect> attackEffect = new ArrayList<>();

    public Weapon(){
        //super(Weapon.class.getSimpleName(), "",   1, MainItemType.Weapon);
    }

    enum AttackEffect{
        bleed, flame, poison, freeze, stun, stealMp, stealHp, stealSt, lighting
    }
}
