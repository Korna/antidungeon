package kom.hikeside.Game.Objects.Inventory;

import kom.hikeside.Game.Objects.ObjList;

/**
 * Created by Koma on 16.08.2017.
 */

public class Armor extends InventoryObject {

    public Armor(){
        super(PotionHeal.class.getSimpleName(),  1, ObjList.inventory.plank);
    }

}
