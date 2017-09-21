package kom.hikeside.Game.Objects.Inventory.ItemLists;

import kom.hikeside.Content.MainItemType;

/**
 * Created by Koma on 17.09.2017.
 */

public enum ItemShield {
    Wooden_Shield, Simple_Book;


    public MainItemType getType(){
        return MainItemType.Shield;
    }
}
