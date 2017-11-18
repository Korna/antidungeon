package kom.hikeside.Game.Mechanic;

import java.util.ArrayList;

import kom.hikeside.Content.MainItemType;
import kom.hikeside.Game.MapView;
import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemAccessory;
import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemArmor;
import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemHead;
import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemLegs;
import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemRing;
import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemShield;
import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemWeapon;
import kom.hikeside.Game.Objects.Inventory.InventoryObject;

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

                for(int i = 0; i < ItemArmor.values().length; ++i){
                    ItemArmor item = ItemArmor.values()[i];

                    items.add(new InventoryObject(item.name(), item.getType()));
                }

                for(int i = 0; i < ItemWeapon.values().length; ++i){
                    ItemWeapon item = ItemWeapon.values()[i];

                    items.add(new InventoryObject(item.name(), item.getType()));
                }

                for(int i = 0; i < ItemHead.values().length; ++i){
                    ItemHead item = ItemHead.values()[i];

                    items.add(new InventoryObject(item.name(), item.getType()));
                }

                for(int i = 0; i < ItemLegs.values().length; ++i){
                    ItemLegs item = ItemLegs.values()[i];

                    items.add(new InventoryObject(item.name(), item.getType()));
                }

                for(int i = 0; i < ItemShield.values().length; ++i){
                    ItemShield item = ItemShield.values()[i];

                    items.add(new InventoryObject(item.name(), item.getType()));
                }

                for(int i = 0; i < ItemRing.values().length; ++i){
                    ItemRing item = ItemRing.values()[i];

                    items.add(new InventoryObject(item.name(), item.getType()));
                }

                for(int i = 0; i < ItemAccessory.values().length; ++i){
                    ItemAccessory item = ItemAccessory.values()[i];

                    items.add(new InventoryObject(item.name(), item.getType()));
                }

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