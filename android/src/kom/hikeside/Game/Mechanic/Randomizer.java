package kom.hikeside.Game.Mechanic;

import com.badlogic.gdx.utils.Array;

import kom.hikeside.Game.MapView;
import kom.hikeside.Game.Objects.Inventory.InventoryObject;
import kom.hikeside.Game.Objects.MapViewPriority;

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


    public static InventoryObject getSimpleItem(){

        return new InventoryObject();
    }


}
