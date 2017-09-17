package kom.hikeside.Game.Mechanic;

import java.util.ArrayList;

import kom.hikeside.Content.MainItemType;
import kom.hikeside.Game.MapView;
import kom.hikeside.Game.Objects.Inventory.ItemAccessory;
import kom.hikeside.Game.Objects.Inventory.ItemArmor;
import kom.hikeside.Game.Objects.Inventory.ItemHead;
import kom.hikeside.Game.Objects.Inventory.ItemLegs;
import kom.hikeside.Game.Objects.Inventory.ItemRing;
import kom.hikeside.Game.Objects.Inventory.ItemShield;
import kom.hikeside.Game.Objects.Inventory.ItemWeapon;
import kom.hikeside.Game.Objects.Inventory.PotionHeal;
import kom.hikeside.Game.Objects.Inventory.InventoryObject;
import kom.hikeside.Game.Objects.Inventory.Weapon;
import kom.hikeside.Content.LibraryObjects;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Koma on 16.08.2017.
 */

public class FromMapGetter {//


    public ArrayList<InventoryObject> getItems(MapView type){//
        ArrayList<InventoryObject> items = new ArrayList<>();

        switch(type){
            case bag:
            case treasureChest:
            case backpack:
                items.add(new InventoryObject(ItemArmor.Leather.name(), MainItemType.Armour));
                items.add(new InventoryObject(ItemArmor.Mail.name(), MainItemType.Armour));
                items.add(new InventoryObject(ItemArmor.Robe.name(), MainItemType.Armour));

                items.add(new InventoryObject(ItemWeapon.Sword.name(), MainItemType.Weapon));
                items.add(new InventoryObject(ItemWeapon.Axe.name(), MainItemType.Weapon));
                items.add(new InventoryObject(ItemWeapon.Wand.name(), MainItemType.Weapon));

                items.add(new InventoryObject(ItemHead.Helm_Berserker.name(), MainItemType.Head));

                items.add(new InventoryObject(ItemLegs.Fancy_Boots.name(), MainItemType.Legs));

                items.add(new InventoryObject(ItemShield.Wooden_Shield.name(), MainItemType.Shield));
                items.add(new InventoryObject(ItemShield.Simple_Book.name(), MainItemType.Shield));

                items.add(new InventoryObject(ItemRing.Golden_Ring.name(), MainItemType.Ring));

                items.add(new InventoryObject(ItemAccessory.Azure_Pendant.name(), MainItemType.Accessory));
                return items;
            default:
                return null;
        }

    }

}



//
//
//что, если в тип отсылать имя класса? а потом инстансить его и проверять принадлежность по суперклассу?
//
//
//
//
//