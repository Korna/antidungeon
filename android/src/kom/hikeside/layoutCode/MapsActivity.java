package kom.hikeside.layoutCode;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.InfoWindowManager;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kom.hikeside.Atom.Place;
import kom.hikeside.Content.LibraryMonsters;
import kom.hikeside.Custom.MarkerInfoWindows.FightFragment;
import kom.hikeside.Custom.MarkerInfoWindows.LootFragment;
import kom.hikeside.FBDBHandler.FBPlace;
import kom.hikeside.Game.MapView;
import kom.hikeside.Game.Mechanic.CollectionHandler;
import kom.hikeside.Game.Mechanic.FromMapGetter;
import kom.hikeside.Game.Map.MapObjBuilder;
import kom.hikeside.Game.Map.CoordinateGenerator;
import kom.hikeside.Game.Mechanic.Randomizer;
import kom.hikeside.FBDBHandler.UserDataFBHandler;
import kom.hikeside.R;
import kom.hikeside.Singleton;
import kom.hikeside.layoutCode.Fragments.BuildFragment;
import kom.hikeside.layoutCode.Profile.GameProfileActivity;
import kom.hikeside.Content.LibraryObjects;

import static kom.hikeside.Constants.FB_DIRECTORY_MARKS;

public class MapsActivity extends FragmentActivity implements
       // OnMapReadyCallback,
        InfoWindowManager.WindowShowListener,
        GoogleMap.OnMarkerClickListener{
    private static final int MY_LOCATION_REQUEST_CODE = 1;
    private static final int REQ_PERMISSION = 2;


    Singleton instance = Singleton.getInstance();
    MapObjBuilder builder = new MapObjBuilder();


    private GoogleMap mMap;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // врубаем фуллскрин
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.fragment_maps_gui);

        context = this;

        instance.context = context;

        if(googleServiceAvailable()){
          //  initMap();
            initInterface();
        }else{
            Toast.makeText(this, "Services not available", Toast.LENGTH_SHORT).show();
        }

        //занесение локации в синглтон
        myLocation();

        BuildFragment buildFragment = new BuildFragment();
        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.layout_map_build, buildFragment, buildFragment.getTag()).commit();

        final MapInfoWindowFragment mapInfoWindowFragment = (MapInfoWindowFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        infoWindowManager = mapInfoWindowFragment.infoWindowManager();
        infoWindowManager.setHideOnFling(true);

        mapInfoWindowFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                loadMarkers();

                MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.retro);
                googleMap.setMapStyle(style);

                mMap.setOnMarkerClickListener(MapsActivity.this);
                setCamera(mMap, myLocation());

                if(checkPermission())
                    mMap.setMyLocationEnabled(true);
                else
                    askPermission();

            }
        });

        infoWindowManager.setWindowShowListener(MapsActivity.this);



    }


    private InfoWindowManager infoWindowManager;


    Place selectedPlace = null;
    InfoWindow infoWindow = null;//глобальная переменная тк нужно закрывать окно по требованию
    @Override
    public boolean onMarkerClick(Marker marker) {
        builder.setUp(marker);

        String id = marker.getId();
        Place p = cHandler.idModelMap.get(id);//здесь может выскочить null pointer
        if(p==null){
            Log.d("markerClick", "cant find place model for marker");
        }
        final InfoWindow.MarkerSpecification markerSpec =
                new InfoWindow.MarkerSpecification(1, 1);//offset: X, Y

        FightFragment f = new FightFragment();
        LootFragment l = new LootFragment();

        MapView type = MapView.valueOf(marker.getSnippet());

        switch (type) {
            case boss:
            case enemy:
                infoWindow = new InfoWindow(marker, markerSpec, f);
                f.LoadWindowInfo(p.getName(), p.getDescription());//однако требуется понимать какой фрагмент именно загружать инфой
                break;
            case bag:
            case backpack:
                infoWindow = new InfoWindow(marker, markerSpec, l);
                String lvlText;
                try {
                    lvlText = LibraryObjects.getEnemyModel(p.getName()).getLvl() + "";
                }catch(Exception e){
                    lvlText = "Unk";
                }
                l.LoadWindowInfo(p.getName(), lvlText);
                break;
            default:
                infoWindow = null;
                break;
        }

        if (infoWindow != null) {
            selectedPlace = p;
            infoWindowManager.toggle(infoWindow, true);


        }

        return true;
    }


    private void initInterface(){

        FloatingActionButton fButtonProfile = (FloatingActionButton) findViewById(R.id.f_button_profile);
        fButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameProfileActivity.class);
                startActivity(intent);
            }
        });



        FloatingActionButton fButtonInteract = (FloatingActionButton) findViewById(R.id.f_button);
        fButtonInteract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list = cHandler.nearby(myLocation());

                if(selectedPlace!=null) {
                    Log.d("interact", "selected");

                    String key = selectedPlace.getId();

                    if(includesId(key, list)){
                        Log.d("includes:", key);

                        FBHandler.deletePlace(key);

                        cHandler.localDeleteMark(key);

                        //отруб окна
                        infoWindowManager.toggle(infoWindow, true);

                      }

                }
            }
        });


        FloatingActionButton fButtonAdd = (FloatingActionButton) findViewById(R.id.f_button_add);
        fButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "Enemy";
                String desc = "Desc" + System.currentTimeMillis();
               // addNewCrate(instance.user.getUid(), name, desc, myLocation() );
                addNewMark(instance.user.getUid(), name, desc, myLocation() , MapView.enemy);
                generateMarkers();
                setCamera(mMap, myLocation());
            }
        });

        FloatingActionButton fButtonLoot = (FloatingActionButton) findViewById(R.id.f_button_loot);
        fButtonLoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list = cHandler.nearby(myLocation());


                if(selectedPlace!=null) {


                    String key = selectedPlace.getId();

                    String id = cHandler.keyViewMap.get(key).getId();

                    MapView type = cHandler.idModelMap.get(id).getType();//отсюда берем item type


                    FBHandler.addItemInUserInventory(new FromMapGetter().getItems(type));

                }

            }
        });
    }

    private boolean includesId(String id, ArrayList<String> list){

        for(String record : list){
            if(record.equals(id))
                return true;
        }

        return false;
    }


    CollectionHandler cHandler = new CollectionHandler();
    FBPlace db = new FBPlace();


    private void loadMarkers(){
        db = new FBPlace();
        FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_MARKS).addValueEventListener(//глобальный и постоянный прослушиватель всех данных marks
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("onDataChange", "refreshing markers");

                        cHandler.clear();
                        mMap.clear();

                        db.FBlist.clear();

                        db.receive(dataSnapshot);

                        for(Place place : db.FBlist){

                            Marker marker = (Marker) builder.smartBuild(mMap, place);
                            String id = marker.getId();
                            cHandler.keyViewMap.put(place.getId(), marker);

                            cHandler.idModelMap.put(id, place);


                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
    }

    private void generateMarkers(){
        //cHandler.clear();
        //mMap.clear();
        CoordinateGenerator og = new CoordinateGenerator();
        ArrayList<LatLng> tempList = og.initGenerate(myLocation(), 1, false);//координаты

        ArrayList<Place> list = new ArrayList<>();
        for(LatLng latLng : tempList){
            MapView type = Randomizer.getSimpleObject();
            String title = "Generic " + type.name();

            LibraryMonsters monsterName;
            if(type == MapView.enemy) {
                monsterName = Randomizer.simpleMonster();
                title = monsterName.name();
            }
            if(type == MapView.boss){
                monsterName = Randomizer.simpleMonster();
                title = monsterName.name();
            }


            list.add(new Place("id", instance.user.getUid(), title, "description", latLng.latitude, latLng.longitude, type));
        }


        for(Place place : list){

            FBHandler.addPlace(place);



            Marker marker = (Marker) builder.smartBuild(mMap, place);
            String id = marker.getId();

            cHandler.keyViewMap.put(place.getId(), marker);
            cHandler.idModelMap.put(id, place);

        }


    }


    UserDataFBHandler FBHandler = new UserDataFBHandler(instance.user.getUid());

    private void addNewMark(String uid, String name, String description, LatLng latLng, MapView type){


        Place place = new Place("key", uid, name, description, latLng.latitude, latLng.longitude, type);

        FBHandler.addPlace(place);

        // modelObject.put(place.getId(), place);
        //  mapObject.put(place.getId(), builder.smartBuild(mMap, place));

        Marker mapObject = (Marker) builder.smartBuild(mMap, place);
        String id = mapObject.getId();

        cHandler.keyViewMap.put(place.getId(), mapObject);
        cHandler.idModelMap.put(id, place);
    }

    /*
    private void addNewCrate(String uid, String name, String description, LatLng latLng){

        instance.myRef = FirebaseDatabase.getInstance().getReference("marks");
        String id = instance.myRef.push().getKey();
        Place place = new Place(id, uid, name, description, latLng.latitude, latLng.longitude, MapView.zone2);
        instance.myRef.child(id).setValue(place);

        modelObject.put(place.getId(), place);
        mapObject.put(place.getId(), builder.smartBuild(mMap, place));
    }*/




/*
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


        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout

                Place place = getModel(arg0.getId());

                View v = infoWindowBuilder(place);

                // Returning the view containing InfoWindow contents
                return v;

            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Place p = getModel(marker.getId());

                p.getType();

                //TODO
                        //смотрится objectmodel на соответствию объекту mark
                        //при совпадении по тому же индексу проверяем equals с тыкнутым маркером
                        //при соответствии берем из objectmodel всю инфу
                //TODO из мапмодели брать index совпадения и далее в objectmodel по тому же индексу брать уже объект и с него уже инфу всю брать

              //  int position = (int)(marker.getTag());
            //    String id = null;
             //   id = ((Marker) mapObject.get(position)).getId();
             //   Log.d("markerClick", id);
                //Using position get Value from arraylist
                return false;
            }
        });

    }*/


    private Place getModel(String id){

       // return modelObject.get(id);
        LatLng l = myLocation();
        return new Place(" ", " ", " Name", "description", l.latitude, l.longitude, MapView.enemy);
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
        CameraPosition cameraPosition;
        try {
            cameraPosition = new CameraPosition.Builder().target(latLng).zoom(18).bearing(0).tilt(0).build();
        }catch(NullPointerException e){
            Log.e("Error", e.toString());
            latLng = new LatLng(0, 0);
            cameraPosition = new CameraPosition.Builder().target(latLng).zoom(18).bearing(0).tilt(0).build();
        }
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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

    @Override
    public void onWindowShowStarted(@NonNull InfoWindow infoWindow) {
//        Log.d("debug", "onWindowShowStarted: " + infoWindow);
    }

    @Override
    public void onWindowShown(@NonNull InfoWindow infoWindow) {
//        Log.d("debug", "onWindowShown: " + infoWindow);
    }

    @Override
    public void onWindowHideStarted(@NonNull InfoWindow infoWindow) {
//        Log.d("debug", "onWindowHideStarted: " + infoWindow);
    }

    @Override
    public void onWindowHidden(@NonNull InfoWindow infoWindow) {
//        Log.d("debug", "onWindowHidden: " + infoWindow);
    }

}
