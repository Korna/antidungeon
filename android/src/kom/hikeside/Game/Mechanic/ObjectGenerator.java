package kom.hikeside.Game.Mechanic;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import kom.hikeside.Atom.Place;
import kom.hikeside.Game.MapView;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.math.MathUtils.sinDeg;

/**
 * Created by Koma on 31.08.2017.
 */

public class ObjectGenerator {//а надо ли отдельно создавать класс для отправки запросов в functions?
    static final double INTERACT_RADIUS = 0.0001;//0.0001 это 8.89 м радиус

    MapObjInteracter interacter = new MapObjInteracter(INTERACT_RADIUS * 100);



// зависимость от лвла, вещей, предыдущей активности, местоположения итд, а также, находится ли около выбранного дома юзер


    public ArrayList<Place>initGenerate(LatLng myCoordinate, int lvl, boolean isPremium){//генерация при загрузке приложения(по идее надо делать server push, чтоб лишь раз в сутки можно было)
        // отправляет айди юзера и время, после котоорго всё пропадет
        ArrayList<Place> list = new ArrayList<>();

        double ZONE_DIVIDE = 0.005;

        /** если смотреть по окружностям, то каждые 100 метров длины окружности должны иметь в себе минимум 1 ивент
         *  */

        for(int l = 1; l <= 4; ++l){

            double radiusMax = l * ZONE_DIVIDE;


            double length = radiusMax * 6.28;

            int blocksSource = (int) Math.round(length/(ZONE_DIVIDE*2));

            for(int blocks = (int) Math.round(length/(ZONE_DIVIDE*2)); 0 < blocks; --blocks){
                Log.d("generate", "time");
                int randomDegree = random.nextInt(360)%(360/blocksSource) + (360/blocksSource)*blocks;

                double radiusRandom = random.nextDouble()%radiusMax + (radiusMax - ZONE_DIVIDE);

                double x = radiusRandom * sinDeg(randomDegree) + myCoordinate.latitude;
                double y = radiusRandom * cosDeg(randomDegree) + myCoordinate.longitude;
                list.add(new Place("", "", "generated", "generated description", x, y, MapView.enemy));
            }



        }
        return list;

    }//если текущая позиция выходит за пределы радиуса генерации, то перегенерировать



}
