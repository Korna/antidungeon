package kom.hikeside.Game.Mechanic;

import com.badlogic.gdx.utils.Array;

import kom.hikeside.Game.MapView;
import kom.hikeside.Game.Objects.Inventory.InventoryObject;
import kom.hikeside.Game.Objects.MapViewPriority;
import kom.hikeside.Game.Objects.ObjList;
import kom.hikeside.libgdx.GameMechanics.AttackModel;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Koma on 04.09.2017.
 */

public class Randomizer {

    public static MapView getSimpleObject(){
        int randomIndex = random.nextInt(MapView.values().length);

        return MapView.values() [randomIndex];
    }

    public static MapView getLayerObject(int layerLevel){
        Array<MapView> list = new MapViewPriority().priority_1();

        int randomIndex = random.nextInt(list.size);


        return list.get(randomIndex);
    }


    public static ObjList.inventory getSimpleItem(){
        int randomIndex = random.nextInt(ObjList.inventory.values().length);

        return ObjList.inventory.values() [randomIndex];
    }

    public static int getAttackValue(AttackModel model){
        float chance = random.nextFloat();
        if(chance < model.chanceToHit)
            return random.nextInt(model.highestDamage - model.lowestDamage) + model.lowestDamage;
        else
            return 0;
    }

    public static String[] battleFieldTexture(){
        int randomNumber = random.nextInt(3) + 1;
        if(randomNumber == 1)
            return new String[]{"grass", "wall"};
        if(randomNumber == 2)
            return new String[]{"brown_stone", "orange_brick"};
        else
            return new String[]{"orange_brick", "brown_stone"};
    }

    public static String simpleMonster(){
        int randomNumber = random.nextInt(8) + 1;
        return "monster_" + randomNumber;
    }


}
