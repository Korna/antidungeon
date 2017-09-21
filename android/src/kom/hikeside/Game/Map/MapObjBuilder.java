package kom.hikeside.Game.Map;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;

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
import kom.hikeside.R;

import static java.security.AccessController.getContext;

/**
 * Created by Koma on 15.08.2017.
 */

public class MapObjBuilder {//проблемное место - карта. к ней колоссальное число запросов будет


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    private static Bitmap getBitmap(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable instanceof BitmapDrawable) {
            return BitmapFactory.decodeResource(context.getResources(), drawableId);
        } else if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable);
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }


    public Object smartBuild(GoogleMap map,Place place){
        MapView type = place.getType();


        MarkerOptions mo;
        LatLng l = new LatLng(place.getLatitude(), place.getLongtitude());

        switch (type) {
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
            case treasureChest:
                mo = new MarkerOptions()
                        .position(l)
                        .snippet(place.getType().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                return build(map, mo);
            case backpack:
            case bag:
                mo = new MarkerOptions()
                        .position(l)
                        .snippet(place.getType().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                return build(map, mo);
            default:
                mo = new MarkerOptions()
                        .position(l)
                        .snippet(place.getType().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).flat(true);
                return build(map, mo);

        }


    }



    public Marker build(GoogleMap map, MarkerOptions mo){
        Marker mark = map.addMarker(mo);
        return mark;
    }






}
