package kom.hikeside.Game.Objects.Types;


import java.util.ArrayList;

import kom.hikeside.Game.Objects.Inventory.InventoryObject;
import kom.hikeside.Game.Objects.ObjList;
import kom.hikeside.Game.Point;

/**
 * Created by Koma on 16.08.2017.
 */

public class Crate extends Dots {
    ArrayList<InventoryObject> content;

    public Crate(){
        content = new ArrayList<>();
        for(int i = 0; i < 1; ++i){
            content.add(new InventoryObject("Item", 100, 1, ObjList.inventory.food));//типа рандомный

        }
        pointList.add(new Point(-1, 0));
        pointList.add(new Point(+1, 0));
        pointList.add(new Point(+1, 1));
        pointList.add(new Point(-1, 1));
        pointList.add(new Point(-1, 0));
    }


}
