package kom.hikeside.Game.Objects.Inventory;

import kom.hikeside.Content.MainItemType;
import kom.hikeside.Game.Objects.Inventory.ItemTypesClassess.Consumable;

/**
 * Created by Koma on 18.08.2017.
 */

public class PotionHeal extends Consumable {

    public PotionHeal(){
        super(PotionHeal.class.getSimpleName(), "",   1, MainItemType.Consumable);
    }


}
