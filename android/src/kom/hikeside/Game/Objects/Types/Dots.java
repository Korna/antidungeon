package kom.hikeside.Game.Objects.Types;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Koma on 15.08.2017.
 */

public class Dots {//вопрос: стоит ли вместе держать инфу об объекте с его представлением?
    public ArrayList<LatLng> pointList;//в метрах и отсчет от середины верхнего габарита

    public Dots(){
        pointList = new ArrayList<>();
    }

}
