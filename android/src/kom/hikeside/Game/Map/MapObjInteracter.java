package kom.hikeside.Game.Map;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

import kom.hikeside.Atom.Place;

import static java.lang.Math.abs;

/**
 * Created by Koma on 16.08.2017.
 */

public class MapObjInteracter {

    double radius = 0.0001;

    HashMap<String, Place> map;
    public MapObjInteracter(){}

    public MapObjInteracter(double radius){
        this.radius = radius;
    }
    public void prepareBDToSearch(HashMap<String, Place> map){
        this.map = map;

    }

//выдает глобальные айли ближайших объектов с которыми можно взаимодействовать
    public ArrayList<String> inRadius(LatLng myCoordinate){//выдаёт ключ объекта, с которым можно взаимодействовать. ОДИН, но не много
        //здесь запрос к функции firebase?
        //потому как глупо как то - получать БД, а потом парсить её
        //или локально определять в зоне ли я действия
        ArrayList<String> found = new ArrayList<>();

        for(Place place : map.values()){

            LatLng latLng = new LatLng(place.getLatitude(), place.getLongtitude());
            //если находится в радиусе
            if(abs(latLng.latitude - myCoordinate.latitude) < radius)
                if(abs(latLng.longitude - myCoordinate.longitude) < radius)
                    found.add(place.getId());

        }

        if(found != null)
            Log.d("inRadius", "found:" + found);
        else
            Log.w("inRadius", "not found");
        return found;
    }



}
