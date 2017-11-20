package kom.hikeside.Game.Map;

import android.content.Context;
import android.util.Log;

import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.TaskCompletionSource;

import kom.hikeside.Models.Atom.Place;
import kom.hikeside.R;
import kom.hikeside.View.MapsActivity;

/**
 * Created by Koma on 21.09.2017.
 */

public class MapFacade {
    MapObjBuilder builder = new MapObjBuilder();
    GoogleMap map;


    public MapInfoWindowFragment setUpMap(MapInfoWindowFragment mapInfoWindowFragment, final TaskCompletionSource taskCompletionSource){

        mapInfoWindowFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                map.getUiSettings().setIndoorLevelPickerEnabled(false);
                map.getUiSettings().setTiltGesturesEnabled(false);
                map.getUiSettings().setMapToolbarEnabled(false);

                taskCompletionSource.setResult(true);


            }
        });

        return mapInfoWindowFragment;
    }

    public void setUpMapStyle(Context context){
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(context, R.raw.retro);
        map.setMapStyle(style);

    }


    public void setCamera(LatLng latLng){
        CameraPosition cameraPosition;
        try {
            cameraPosition = new CameraPosition.Builder().target(latLng).zoom(18).bearing(0).tilt(0).build();
        }catch(NullPointerException e){
            Log.e("Error", e.toString());
            latLng = new LatLng(0, 0);
            cameraPosition = new CameraPosition.Builder().target(latLng).zoom(18).bearing(0).tilt(0).build();
        }
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void clear(){
        map.clear();
    }

    public Marker build(Place place){
        return (Marker) builder.smartBuild(map, place);

    }

    public void setUserLocationEnabled(boolean enable) throws SecurityException{
        if(enable)
            map.setMyLocationEnabled(true);
        else
            map.setMyLocationEnabled(false);
    }


    public void setOnMarkerClickListener(MapsActivity mapsActivity){
        map.setOnMarkerClickListener(mapsActivity);
    }



}
