package kom.hikeside.Game.Mechanic;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;

import kom.hikeside.Atom.Place;

/**
 * Created by Koma on 02.09.2017.
 */

public class CollectionHandler{    //TODO функция add complete MV, которая заносит по исходным данным все значения и манипулирует ими(контролирует? втф)

    //global object key - local view object
    public HashMap<String, Marker> keyViewMap = new HashMap<>();
    //local object key - model global object
    public HashMap<String, Place> idModelMap = new HashMap<>();

    private MapObjInteracter interacter = new MapObjInteracter();

    public void clear(){
        keyViewMap.clear();
        idModelMap.clear();
    }

    public ArrayList<String> nearby(LatLng myLocation){//получает глобальные ID мест
        //interacter.prepareBDToSearch(modelObject);
        interacter.prepareBDToSearch(idModelMap);
        return interacter.inRadius(myLocation);
    }

    public void localDeleteMark(String key){//удаление по глобальному ключу
        Marker m = keyViewMap.get(key);

        if(m != null) {
            String id = m.getId();
            m.remove();//удаление маркера

            keyViewMap.remove(key);//удаление из памяти
            idModelMap.remove(id);//удаление из памяти

        }else{
            Log.e("localDeleteMark", "cant find by ID");
        }

    }

}