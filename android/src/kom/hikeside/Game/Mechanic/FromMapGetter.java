package kom.hikeside.Game.Mechanic;

import java.util.ArrayList;

import kom.hikeside.Game.MapView;
import kom.hikeside.Game.Objects.Inventory.Bandage;
import kom.hikeside.Game.Objects.Inventory.InventoryObject;
import kom.hikeside.Game.Objects.Inventory.Plank;

/**
 * Created by Koma on 16.08.2017.
 */

public class FromMapGetter {//


    public ArrayList<InventoryObject> getItems(MapView type){//
        ArrayList<InventoryObject> items = new ArrayList<>();

        switch(type){
            case bag:
                items.add(new Bandage());
                items.add(new Plank());
                return items;
            case treasureChest:
                items.add(new Bandage());
            case backpack:
                items.add(new Bandage());
                return items;
            default:
                return null;
        }

    }

}
