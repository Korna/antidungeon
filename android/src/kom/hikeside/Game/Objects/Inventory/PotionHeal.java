package kom.hikeside.Game.Objects.Inventory;

import kom.hikeside.Game.Objects.ObjList;

/**
 * Created by Koma on 18.08.2017.
 */

public class PotionHeal extends InventoryObject {

    public PotionHeal(){
        super(PotionHeal.class.getSimpleName(), 1, ObjList.inventory.bandage);
    }

}
