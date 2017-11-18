package kom.hikeside.Game.Objects;

import kom.hikeside.Content.MainItemType;
import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemAccessory;
import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemArmor;
import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemHead;
import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemLegs;
import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemRing;
import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemShield;
import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemWeapon;

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
