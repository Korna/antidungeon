package kom.hikeside.Game.Mechanic;

import java.util.ArrayList;

import kom.hikeside.Game.MapView;
import kom.hikeside.Game.Objects.Inventory.PotionHeal;
import kom.hikeside.Game.Objects.Inventory.InventoryObject;
import kom.hikeside.Game.Objects.Inventory.Armor;

/**
 * Created by Koma on 16.08.2017.
 */

public class FromMapGetter {//


    public ArrayList<InventoryObject> getItems(MapView type){//
        ArrayList<InventoryObject> items = new ArrayList<>();

        switch(type){
            case bag:
                items.add(new PotionHeal());
                items.add(new Armor());
                return items;
            case treasureChest:
                items.add(new PotionHeal());
            case backpack:
                items.add(new PotionHeal());
                return items;
            default:
                return null;
        }

    }

}
