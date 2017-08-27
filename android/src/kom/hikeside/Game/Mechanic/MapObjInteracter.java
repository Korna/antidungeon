package kom.hikeside.Game.Mechanic;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.TreeMap;

import kom.hikeside.Atom.Place;

import static java.lang.Math.abs;

/**
 * Created by Koma on 16.08.2017.
 */

public class MapObjInteracter {

    double radius = 0.0001;

    TreeMap<String, Place> map;
    public void prepareBDToSearch(TreeMap<String, Place> map){
        this.map = map;

    }


    public String inRadius(LatLng myCoordinate){//выдаёт ключ объекта, с которым можно взаимодействовать. ОДИН, но не много
        //здесь запрос к функции firebase?
        //потому как глупо как то - получать БД, а потом парсить её
        //или локально определять в зоне ли я действия
        String founded = null;
        for(Place place : map.values()){

            LatLng latLng = new LatLng(place.getLatitude(), place.getLongtitude());
            //если находится в радиусе
            if(abs(latLng.latitude - myCoordinate.latitude) < radius)
                if(abs(latLng.longitude - myCoordinate.longitude) < radius)
                    founded = place.getId();

        }

        if(founded != null)
            Log.d("inRadius", "found:" + founded);
        else
            Log.w("inRadius", "not found");
        return founded;
    }



}
