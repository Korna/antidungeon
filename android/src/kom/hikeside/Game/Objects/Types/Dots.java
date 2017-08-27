package kom.hikeside.Game.Objects.Types;

import java.util.ArrayList;

import kom.hikeside.Game.Point;

/**
 * Created by Koma on 15.08.2017.
 */

public class Dots {//вопрос: стоит ли вместе держать инфу об объекте с его представлением?
    public ArrayList<Point> pointList;//в метрах и отсчет от середины верхнего габарита

    public Dots(){
        pointList = new ArrayList<>();
    }

}
