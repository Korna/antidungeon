package kom.hikeside.Game.Mechanic;

import java.util.ArrayList;

import kom.hikeside.Game.MapView;
import kom.hikeside.Game.Objects.Inventory.Bandage;
import kom.hikeside.Game.Objects.Inventory.InventoryObject;
import kom.hikeside.Game.Objects.Inventory.Plank;

/**
 * Created by Koma on 16.08.2017.
 */

public class FromMapItemGetter {


    public ArrayList<InventoryObject> open(MapView type){//
        ArrayList<InventoryObject> items = new ArrayList<>();

        switch(type){
            case crate:
                items.add(new Bandage());
                items.add(new Bandage());
                items.add(new Plank());
                return items;
            case backpack:
                items.add(new Bandage());
                return items;
            default:
                return null;
        }

    }

}
