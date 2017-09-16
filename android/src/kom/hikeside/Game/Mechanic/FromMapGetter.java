package kom.hikeside.Game.Mechanic;

import java.util.ArrayList;

import kom.hikeside.Content.MainItemType;
import kom.hikeside.Game.MapView;
import kom.hikeside.Game.Objects.Inventory.ItemArmor;
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