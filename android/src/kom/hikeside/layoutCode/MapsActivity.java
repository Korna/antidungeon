package kom.hikeside.layoutCode;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import kom.hikeside.Atom.Place;
import kom.hikeside.FBDBHandler.FBPlace;
import kom.hikeside.Game.MapView;
import kom.hikeside.Game.Mechanic.FromMapItemGetter;
import kom.hikeside.Game.Mechanic.MapObjBuilder;
import kom.hikeside.Game.Mechanic.MapObjInteracter;
import kom.hikeside.Game.Objects.Inventory.InventoryObject;
import kom.hikeside.R;
import kom.hikeside.Singleton;
import kom.hikeside.layoutCode.Fragments.BuildFragment;

import static kom.hikeside.R.id.map;

public class MapsActivity<E, T> extends FragmentActivity implements OnMapReadyCallback {
    private static final int MY_LOCATION_REQUEST_CODE = 1;
    private static final int REQ_PERMISSION = 2;


    Singleton instance = Singleton.getInstance();
    MapObjBuilder builder;
    MapObjInteracter interacter;
    private void initMechanics(){
        interacter = new MapObjInteracter();
        builder = new MapObjBuilder();
    }

    private GoogleMap mMap;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.fragment_blank);


        context = this;
        if(googleServiceAvailable()){
            Toast.makeText(this, "Service available", Toast.LENGTH_SHORT).show();
            initMap();
            initInterface();
        }


        initMechanics();
        myLocation();

        BuildFragment buildFragment = new BuildFragment();
        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.layout_map_build, buildFragment, buildFragment.getTag()).commit();


    }


    private void initMap(){
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);
    }
    private void initInterface(){
        final Context context = this;

        FloatingActionButton fButtonInteract = (FloatingActionButton) findViewById(R.id.f_button);
        fButtonInteract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = nearby();

                if(id!=null){

                    //запрос к удаленной бд на удаление
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Query marksQuery = ref.child("marks").orderByChild("id").equalTo(id);

                    marksQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snap: dataSnapshot.getChildren()) {
                                snap.getRef().removeValue();
                                Log.w( "dataChange", "kinda removed from firebase");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e( "onCancelled", databaseError.toString());
                        }
                    });

                    localDelete(id);

                }

            }
        });



        FloatingActionButton fButtonAdd = (FloatingActionButton) findViewById(R.id.f_button_add);
        fButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "Crate";
                String desc = "Empty";
                addNewCrate(instance.user.getUid(), name, desc, myLocation() );
                setCamera(mMap, myLocation());
                Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();

            }
        });

        FloatingActionButton fButtonLoot = (FloatingActionButton) findViewById(R.id.f_button_loot);
        fButtonLoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = nearby();
                if(id!=null) {
                    addItemInUserInventory(id, instance.user.getUid());
                    Toast.makeText(context, "looting", Toast.LENGTH_SHORT).show();
                    localDelete(id);
                }
            }
        });
    }

    private String nearby(){
        interacter.prepareBDToSearch(modelObject);
        return interacter.inRadius(myLocation());
    }

    private void localDelete(String id){
        if(mapObject.get(id)!=null) {

            try {
                ((Marker) mapObject.get(id)).setVisible(false);
                ((Marker) mapObject.get(id)).remove();
            }catch(ClassCastException e){
                try {
                    ((Polygon) mapObject.get(id)).setVisible(false);
                    ((Polygon) mapObject.get(id)).remove();
                }catch(ClassCastException e2){
                    try {
                        ((Polyline) mapObject.get(id)).setVisible(false);
                        ((Polyline) mapObject.get(id)).remove();
                    }catch(ClassCastException e3){
                        Log.e("cast", e3.toString());
                    }
                }
            }
            mapObject.remove(id);//сразу удаляет из модельной памяти и соответственно из карты
            modelObject.remove(id);
        }else{
            Log.e("localDelete", "cant find by ID");
        }


    }
    private void addNewCrate(String uid, String name, String description, LatLng latLng){

        instance.myRef = FirebaseDatabase.getInstance().getReference("marks");
        String id = instance.myRef.push().getKey();
        Place place = new Place(id, uid, name, description, latLng.latitude, latLng.longitude, MapView.crate);
        instance.myRef.child(id).setValue(place);

        modelObject.put(place.getId(), place);
        mapObject.put(place.getId(), builder.smartBuild(mMap, place));
    }

    private void addItemInUserInventory(String itemId, String uid){
        MapView type = modelObject.get(itemId).getType();//отсюда берем item type
        ArrayList<InventoryObject> items = new FromMapItemGetter().open(type);
        if(items != null) {

            instance.myRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("inventory");



            for(InventoryObject item : items){
                String tableItemId =
                instance.myRef.push()
                .getKey();//ключ остается заголовком объекта, но не полем объекта

                instance.myRef.child(tableItemId).setValue(item);
            }

        }

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);


        //    mMap.setMapType( options.getMapType() );

       // mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        LatLng latLng = new LatLng(55.041804, 83.006806);

        mMap.addMarker(new MarkerOptions().title("Home").snippet("Its your only frontier.").position(latLng));
        setCamera(mMap, latLng);
        //addLineTest();



        if(checkPermission()){
            Toast.makeText(context, "Permission granted" , Toast.LENGTH_SHORT).show();
            mMap.setMyLocationEnabled(true);
        }
        else {
            Toast.makeText(context, "Asking permission" , Toast.LENGTH_SHORT).show();
            askPermission();
        }

        loadMarkers();

       // MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(this, R.raw.dark);
      //  googleMap.setMapStyle(style);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });

    }

    private LatLng myLocation(){
        LatLng l = null;
        LocationManager m_locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            m_locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, mLocationListener);
            Location location = m_locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.i("PointByNet", location.getLatitude() + " long:" + location.getLongitude());
            if(location!=null){
                l = new LatLng(location.getLatitude(), location.getLongitude());
                instance.setMyPosition(l);
            }


        }catch(SecurityException e){

        }
        return l;
    }
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            Log.i("listener", location.getLatitude() + "long:" + location.getLongitude());

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



    private void setCamera(GoogleMap googleMap, LatLng latLng){
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(18).bearing(0).tilt(0).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    TreeMap<String, Place> modelObject = new TreeMap<>();//модель в памяти

    HashMap<String, Object> mapObject = new HashMap<>();//представление на карте


    FBPlace db = new FBPlace();
    private void loadMarkers(){
        db = new FBPlace();


        FirebaseDatabase.getInstance().getReference("marks").addValueEventListener(//глобальный и постоянный прослушиватель всех данных marks
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("onDataChange", "refreshing markers");

                        mapObject.clear();
                        modelObject.clear();
                        mMap.clear();
                        db.FBlist.clear();

                        db.receive(dataSnapshot);
                        int i = 0;
                        for(Place place : db.FBlist){// = ObjList.ground.
                            /*
                            LatLng c = new LatLng(place.getLatitude(), place.getLongtitude());

                            MarkerOptions m = new MarkerOptions().title(place.getName()).snippet(place.getDescription()).position(c);
                            Marker objMarker = builder.build(mMap, m);


                            mapObject.put(place.getId(), objMarker);
                            modelObject.put(place.getId(), place);
                            */

                            modelObject.put(place.getId(), place);
                            mapObject.put(place.getId(), builder.smartBuild(mMap, place));
                            ++i;

                        }
                        Log.w("added", " "+ i + " objects in modelDB, mapDb and mapView");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}

                });



    }





    private boolean checkPermission() {
        Log.d("checkPermission", "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ;
    }

    private void askPermission() {
        Log.d("askPermission", "askPermission()");
        ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("onRequestPermissions", "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch ( requestCode ) {
            case REQ_PERMISSION: {
                if ( grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    // Permission granted
                    if(checkPermission())
                        mMap.setMyLocationEnabled(true);

                } else {
                    // Permission denied

                }
                break;
            }
        }
    }



    public boolean googleServiceAvailable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS){
            return true;
        }else if(api.isUserResolvableError(isAvailable)){
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        }else{
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

}
