package kom.hikeside.Game.Objects;

import kom.hikeside.Content.MainItemType;
import kom.hikeside.Game.Objects.Inventory.Armour;
import kom.hikeside.Game.Objects.Inventory.ItemAccessory;
import kom.hikeside.Game.Objects.Inventory.ItemArmor;
import kom.hikeside.Game.Objects.Inventory.ItemHead;
import kom.hikeside.Game.Objects.Inventory.ItemLegs;
import kom.hikeside.Game.Objects.Inventory.ItemRing;
import kom.hikeside.Game.Objects.Inventory.ItemShield;
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

    public ItemHead head;
    public ItemArmor armour;
    public ItemWeapon weapon;
    public ItemRing ring;
    public ItemShield shield;
    public ItemLegs legs;
    public ItemAccessory accessory;


    public void addItem(MainItemType item, String concreteType){
        switch(item){
            case Accessory:
                accessory = ItemAccessory.valueOf(concreteType);
                break;
            case Armour:
                armour = ItemArmor.valueOf(concreteType);
                break;
            case Legs:
                legs = ItemLegs.valueOf(concreteType);
                break;
            case Weapon:
                weapon = ItemWeapon.valueOf(concreteType);
                break;
            case Ring:
                ring = ItemRing.valueOf(concreteType);
                break;
            case Shield:
                shield = ItemShield.valueOf(concreteType);
                break;
            case Head:
                head = ItemHead.valueOf(concreteType);
                break;

        }
    }
}
