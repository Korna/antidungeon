package kom.hikeside.Game.Objects.Inventory.ItemLists;

import kom.hikeside.Models.MainItemType;

/**
 * Created by Koma on 17.09.2017.
 */

public enum ItemHead {
    Helm_Berserker, Magicians_Hat, Archer_Hat;

    public MainItemType getType(){
        return MainItemType.Head;
    }
}
