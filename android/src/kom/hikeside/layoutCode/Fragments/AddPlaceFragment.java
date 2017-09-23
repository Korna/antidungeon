package kom.hikeside.layoutCode.Fragments;


import android.app.Fragment;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import kom.hikeside.Atom.Place;
import kom.hikeside.Content.MapView;
import kom.hikeside.FBDBHandler.UserDataFBHandler;
import kom.hikeside.R;
import kom.hikeside.Singleton;

import static android.content.Context.LOCATION_SERVICE;

public class AddPlaceFragment extends Fragment implements View.OnClickListener {

    private final static int LOCATION_REFRESH_TIME = 2000;
    private final static int LOCATION_REFRESH_DISTANCE = 10;

    EditText textName;
    EditText textDescription;

    public AddPlaceFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_place, container, false);

        loadLocation();

        LocationManager m_locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(LOCATION_SERVICE);
        try {
            m_locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 10, mLocationListener);
            Location location = m_locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.d("PointByNet", location.getLatitude() + " long:" + location.getLongitude());
        }catch(SecurityException e){

        }

        textName = (EditText) v.findViewById(R.id.editText_character_name);
        textDescription = (EditText) v.findViewById(R.id.editText_description);
        Button buttonAdd = (Button) v.findViewById(R.id.button_add_mark);
        buttonAdd.setOnClickListener(this);

        return v;
    }

    private void loadLocation(){
        LocationManager mLocationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(LOCATION_SERVICE); //getActivity().getApplicationContext()
        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, mLocationListener);
            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            try {
                latLng = new LatLng(location.getLatitude(), location.getLatitude());

                Log.d("PointByGPS", latLng.toString());
            }catch(NullPointerException e){
                Log.i("PointByGPS", e.toString());
            }


        }catch(SecurityException se){
            Log.e("security", se.toString());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_mark:

                if(!isEmpty()) {
                    String name = textName.getText().toString();
                    String desc = textDescription.getText().toString();
                    textName.setText("");
                    textDescription.setText("");

                    if(latLng==null){

                        latLng = instance.myPosition;
                        latLng = new LatLng(1.1,2.2);
                    }



                    addNewRecord(instance.user.getUid(), name, desc, latLng.latitude, latLng.longitude);
                }

                break;
        }
    }

    private boolean isEmpty(){
        boolean status = true;

        if(TextUtils.isEmpty(textName.getText().toString()))
            textName.setError("Введите название");
        else
            status = false;

        return status;
    }



    LatLng latLng;

    Singleton instance = Singleton.getInstance();

    private void addNewRecord(String uid, String name, String description, double latitude, double longtitude){
        Place place = new Place("id", uid, "Created enemy " + name, description, latitude, longtitude, MapView.enemy);


        UserDataFBHandler FBHandler = new UserDataFBHandler(instance.user.getUid());
        FBHandler.addPlace(place);



    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            Log.d("updateLatitude", latLng.toString());

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}
