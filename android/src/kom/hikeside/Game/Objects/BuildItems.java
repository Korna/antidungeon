package kom.hikeside.Game.Objects;

import kom.hikeside.Content.MainItemType;
import kom.hikeside.Game.Objects.Inventory.Armour;
import kom.hikeside.Game.Objects.Inventory.ItemArmor;
import kom.hikeside.Game.Objects.Inventory.ItemWeapon;
import kom.hikeside.Game.Objects.Inventory.Weapon;

/**
 * Created by Koma on 16.09.2017.
 */

public class BuildItems {
    public BuildItems(){
        armour = null;
        weapon = null;
    }

    //Head head;
    public ItemArmor armour;
    public ItemWeapon weapon;
    //Shield shield;
    //Legs legs
    //Accessory accessory
    //Ring ring

    public void addItem(MainItemType item, String concreteType){
        switch(item){
            case Accessory:
            case Armour:
                armour = ItemArmor.valueOf(concreteType);
                break;
            case Consumable:
            case Weapon:
                weapon = ItemWeapon.valueOf(concreteType);
                break;
            case Ring:
            case Shield:
            case Head:
        }
    }
}
