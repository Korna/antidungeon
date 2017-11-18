package kom.hikeside.Game.Objects.Inventory.ItemLists;

import kom.hikeside.Content.MainItemType;

/**
 * Created by Koma on 17.09.2017.
 */

public enum ItemAccessory {
    Azure_Pendant, Magic_Necklace;

    public MainItemType getType(){
        return MainItemType.Accessory;
    }
}
