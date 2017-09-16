package kom.hikeside.Game.Objects.Inventory;

import kom.hikeside.Content.MainItemType;

/**
 * Created by Koma on 16.09.2017.
 */

public enum RingType {
    Iron_Ring, Wooden_Ring, Triangle_Ring;

    public MainItemType getType(){
        return MainItemType.Ring;
    }
}
