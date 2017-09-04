package kom.hikeside.Game.Mechanic.Map;

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
import kom.hikeside.Game.Objects.Types.Crate;
import kom.hikeside.Game.Objects.Types.Dots;
import kom.hikeside.Game.Objects.Types.Wall;
import kom.hikeside.Game.Point;

/**
 * Created by Koma on 15.08.2017.
 */

public class MapObjBuilder {//проблемное место - карта. к ней колоссальное число запросов будет


    private Dots rotate(Dots poly, int angle){


        return poly;
    }

    public PolylineOptions prepare(LatLng latLng, Dots poly){
        double cfg = 0.0001;
        PolylineOptions p = new PolylineOptions().geodesic(true);

        LatLng c;
        for(Point obj : poly.pointList) {//latLng.latitude + obj.y, latLng.longitude + obj.x
            c = new LatLng(latLng.latitude + obj.y * cfg, latLng.longitude + obj.x * cfg);
            p.add(c);
        }

        p.color(Color.GRAY);
        return p;
    }

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
            case zone:
                /*
                Wall wall = new Wall();
                po = prepare(l, wall);
                return build(map, po);
*/
            case enemy:
                mo = new MarkerOptions().title("Enemy:" + place.getName())
                        .position(l)
                        .snippet(place.getType().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                return build(map, mo);
            case boss:
                mo = new MarkerOptions().title("Boss:" + place.getName())
                        .position(l)
                        .snippet(place.getType().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                return build(map, mo);
            case backpack:
                mo = new MarkerOptions().title("Backpack:" + place.getName())
                        //.snippet(place.getDescription())
                        .position(l)
                        .snippet(place.getType().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                return build(map, mo);
            case bag:
                mo = new MarkerOptions().title("Bag:" + place.getName())
                        //.snippet(place.getDescription())
                        .position(l)
                        .snippet(place.getType().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                return build(map, mo);
            default:
                mo = new MarkerOptions().title("Object:" + place.getName())
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


}
