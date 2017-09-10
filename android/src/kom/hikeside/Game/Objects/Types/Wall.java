package kom.hikeside.Game.Objects.Types;

import com.google.android.gms.maps.model.LatLng;


/**
 * Created by Koma on 15.08.2017.
 */

public class Wall extends Dots {
    private int health;//скок требуется чтоб уничтожить?
    private int defence;//коэфф прохождения?

    public Wall(){
        super();
        health = 100;
        defence = 5;

        pointList.add(new LatLng(-3, 0));
        pointList.add(new LatLng(+3, 0));
        pointList.add(new LatLng(+3, 1));
        pointList.add(new LatLng(-3, 1));
        pointList.add(new LatLng(-3, 0));//замыкание

    }

}
