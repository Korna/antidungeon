package kom.hikeside.Game.Objects.Inventory.ItemLists;

import kom.hikeside.Content.MainItemType;

/**
 * Created by Koma on 16.09.2017.
 */

public enum ItemArmor {
    Leather, Mail, Robe, Iron_Plate;

    public MainItemType getType(){
        return MainItemType.Armour;
    }
}
