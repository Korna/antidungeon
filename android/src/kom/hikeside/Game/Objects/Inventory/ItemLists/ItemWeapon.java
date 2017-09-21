package kom.hikeside.Game.Objects.Inventory.ItemLists;

import kom.hikeside.Content.MainItemType;

/**
 * Created by Koma on 16.09.2017.
 */

public enum ItemWeapon {
    Sword, Axe, Wand;

    public MainItemType getType(){
        return MainItemType.Weapon;
    }
}
