package kom.hikeside.Game.Objects.Types;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import kom.hikeside.Game.Objects.Inventory.InventoryObject;
import kom.hikeside.Game.Objects.Inventory.ObjList;

/**
 * Created by Koma on 16.08.2017.
 */

public class Crate extends Dots {
    ArrayList<InventoryObject> content;

    public Crate(){
        content = new ArrayList<>();
        for(int i = 0; i < 1; ++i){
            content.add(new InventoryObject("Item", "", 1, ObjList.inventory.food));//типа рандомный

        }
        pointList.add(new LatLng(-1, 0));
        pointList.add(new LatLng(+1, 0));
        pointList.add(new LatLng(+1, 1));
        pointList.add(new LatLng(-1, 1));
        pointList.add(new LatLng(-1, 0));
    }


}
