package kom.hikeside.Game.Objects.Inventory.ItemLists;

import kom.hikeside.Models.MainItemType;

/**
 * Created by Koma on 17.09.2017.
 */

public enum ItemRing {
    Golden_Ring, Wooden_Ring, Triangle_Ring;

    public MainItemType getType(){
        return MainItemType.Ring;
    }
}
