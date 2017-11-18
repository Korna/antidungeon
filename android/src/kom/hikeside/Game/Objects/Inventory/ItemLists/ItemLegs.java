package kom.hikeside.Game.Objects.Inventory.ItemLists;

import kom.hikeside.Content.MainItemType;

/**
 * Created by Koma on 17.09.2017.
 */

public enum ItemLegs {
    Fancy_Shoes, Fasten_Boots;

    public MainItemType getType(){
        return MainItemType.Legs;
    }
}
