package kom.hikeside.Game.Objects;

import com.badlogic.gdx.utils.Array;

import kom.hikeside.Game.MapView;

/**
 * Created by Koma on 04.09.2017.
 */

public class MapViewPriority {

    public Array<MapView> priority_1(){
        Array<MapView> list = new Array<MapView>();

        list.add(MapView.boss);
        list.add(MapView.treasureChest);
        return list;
    }
}
