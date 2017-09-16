package kom.hikeside.Game.Map;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import kom.hikeside.Atom.Place;
import kom.hikeside.Game.MapView;

/**
 * Created by Koma on 15.08.2017.
 */

public class MapObjBuilder {//проблемное место - карта. к ней колоссальное число запросов будет






    public Object smartBuild(GoogleMap map,Place place){
        MapView type = place.getType();


        PolylineOptions po;
        MarkerOptions mo;
        LatLng l = new LatLng(place.getLatitude(), place.getLongtitude());

        switch (type) {
                /*
                Crate crate = new Crate();
                po = prepare(l, crate);
                return build(map, po);
                */
                
                /*
                Wall wall = new Wall();
                po = prepare(l, wall);
                return build(map, po);
*/
            case enemy:
                mo = new MarkerOptions()
                        .position(l)
                        .snippet(place.getType().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                return build(map, mo);
            case boss:
                mo = new MarkerOptions()
                        .position(l)
                        .snippet(place.getType().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                return build(map, mo);
            case backpack:
            case bag:
                mo = new MarkerOptions()
                        //.snippet(place.getDescription())
                        .position(l)
                        .snippet(place.getType().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                return build(map, mo);
            default:
                mo = new MarkerOptions()
                        .position(l)
                        .snippet(place.getType().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                return build(map, mo);

        }


    }

    public Polyline build(GoogleMap map, PolylineOptions po){
        Polyline poly = map.addPolyline(po);
        return poly;
    }

    public Polygon build(GoogleMap map, PolygonOptions po){
        Polygon poly = map.addPolygon(po);
        return poly;
    }

    public Circle build(GoogleMap map, CircleOptions co){
        Circle circle = map.addCircle(co);
        return circle;
    }

    public Marker build(GoogleMap map, MarkerOptions mo){
        Marker mark = map.addMarker(mo);
        return mark;
    }




    public Marker setUp(Marker marker){


        return marker;
    }


}
