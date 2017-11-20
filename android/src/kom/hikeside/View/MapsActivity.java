package kom.hikeside.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.InfoWindowManager;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import kom.hikeside.AndroidLauncher;
import kom.hikeside.Models.Atom.Place;
import kom.hikeside.Models.LibraryMonsters;
import kom.hikeside.Custom.MarkerInfoWindows.FightFragment;
import kom.hikeside.Custom.MarkerInfoWindows.LootFragment;
import kom.hikeside.Custom.MyDialogFragment;
import kom.hikeside.Service.FBPlace;
import kom.hikeside.Game.Map.MapFacade;
import kom.hikeside.Models.MapView;
import kom.hikeside.Game.Mechanic.CollectionHandler;
import kom.hikeside.Game.Mechanic.FromMapGetter;
import kom.hikeside.Game.Map.CoordinateGenerator;
import kom.hikeside.Game.Mechanic.Randomizer;
import kom.hikeside.Service.UserDataFBHandler;
import kom.hikeside.R;
import kom.hikeside.Service.Singleton;
import kom.hikeside.View.layoutCode.Profile.GameProfileActivity;
import kom.hikeside.Models.LibraryObjects;
import kom.hikeside.Game.libgdx.BundleToLib;

import static kom.hikeside.Constants.FB_DIRECTORY_MARKS;

public class MapsActivity extends FragmentActivity implements
       // OnMapReadyCallback,
        InfoWindowManager.WindowShowListener,
        GoogleMap.OnMarkerClickListener{
    private static final int MY_LOCATION_REQUEST_CODE = 1;
    public static final int REQ_PERMISSION = 2;


    Singleton instance = Singleton.getInstance();


    CollectionHandler cHandler = new CollectionHandler();
    FBPlace db = new FBPlace();
    MapFacade mapFacade = new MapFacade();

    @Bind(R.id.textView_maps_hp)
    TextView textViewHp;
    @Bind(R.id.textView_maps_exp)
    TextView textViewExp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.fragment_maps_gui);
        instance.context = this;
        if(googleServiceAvailable()){
            initInterface();
            ButterKnife.bind(this);
            initMap();
            float exp = instance.currentGameCharacter.getExperience();
            float maxHp = instance.currentGameCharacter.getMaxHp();
            float curHp = instance.currentGameCharacter.getCurrentHp();
            textViewExp.setText(exp + " Exp");
            textViewHp.setText(curHp + "/" + maxHp + " Hp");
        }else{
            Toast.makeText(this, "Services not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }





    private void initMap(){
        final MapInfoWindowFragment mapInfoWindowFragment = (MapInfoWindowFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        final TaskCompletionSource taskToLoad = new TaskCompletionSource();
        mapFacade.setUpMap(mapInfoWindowFragment, taskToLoad);
        infoWindowManager = mapInfoWindowFragment.infoWindowManager();
        infoWindowManager.setHideOnFling(true);
        infoWindowManager.setWindowShowListener(MapsActivity.this);


        Task task = taskToLoad.getTask();
        task.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                loadMarkers();
                mapFacade.setUpMapStyle(getApplicationContext());
                mapFacade.setOnMarkerClickListener(MapsActivity.this);
                if(checkPermission())
                    mapFacade.setUserLocationEnabled(true);
                else
                    askPermission();
                mapFacade.setCamera(myLocation());
            }
        });






    }

    private InfoWindowManager infoWindowManager;


    InfoWindow infoWindow = null;//глобальная переменная тк нужно закрывать окно по требованию
    @Override
    public boolean onMarkerClick(Marker marker) {//мы можем передавать таск в фрагмент и ждать пока придет результат

        String id = marker.getId();
        final Place placeToInteract = cHandler.idModelMap.get(id);//здесь может выскочить null pointer

        if(placeToInteract==null){
            Log.d("markerClick", "cant find place model for marker");
        }

        final InfoWindow.MarkerSpecification markerSpec = new InfoWindow.MarkerSpecification(25, 25);//offset: X, Y

        FightFragment f = new FightFragment();
        LootFragment l = new LootFragment();

        final MapView type = MapView.valueOf(marker.getSnippet());

        switch (type) {
            case boss:
            case enemy:
                infoWindow = new InfoWindow(marker, markerSpec, f);


                DialogInterface.OnClickListener dialogInterface = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {


                        BundleToLib bundle = BundleToLib.getInstance();
                        bundle.enemyNames.clear();
                        bundle.enemyNames.add(LibraryMonsters.valueOf(placeToInteract.getName()));


                        Intent intent = new Intent(getApplicationContext(), AndroidLauncher.class);
                        startActivity(intent);

                        bundle.taskCompletionSource = new TaskCompletionSource();

                        Task task = bundle.taskCompletionSource.getTask();
                        dialog.cancel();
                        task.addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                Boolean object = (Boolean) task.getResult();
                                if(object){
                                     setupRewardDialog();
                                    Log.w("SHOWING", "ot");


                                }else{
                                    //setupFailDialog();
                                    Log.e("NOO", "ot");
                                }


                            }
                        });
                    }
                };

                f.LoadWindowInfo(placeToInteract.getName(), placeToInteract.getDescription(), dialogInterface);//однако требуется понимать какой фрагмент именно загружать инфой
                f.loadPlace(placeToInteract);
                break;
            case treasureChest:
            case bag:
            case backpack:
                infoWindow = new InfoWindow(marker, markerSpec, l);
                String lvlText;
                try {
                    lvlText = LibraryObjects.getEnemyModel(placeToInteract.getName()).getLvl() + "";
                }catch(Exception e){
                    lvlText = "Unknown";
                }
                DialogInterface.OnClickListener dialogInterfaceLoot = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        FBHandler.addItemInUserInventory(new FromMapGetter().getItems(type));

                        FBHandler.deletePlace(placeToInteract.getId());

                        cHandler.localDeleteMark(placeToInteract.getId());
                        Toast.makeText(getApplicationContext(), "Looting...", Toast.LENGTH_SHORT).show();
                        infoWindowManager.toggle(infoWindow, true);

                    }
                };

                l.LoadWindowInfo(placeToInteract.getName(), lvlText, dialogInterfaceLoot);
                break;
            default:
                infoWindow = null;
                break;
        }

        if (infoWindow != null) {
            infoWindowManager.toggle(infoWindow, true);

        }

        return true;
    }

    private void setupRewardDialog(){

        final AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(this);

        alertDialog.setTitle("Reward");
        alertDialog.setMessage("You won");
        Log.d("dialog", "You won");
        //alertDialog.setIcon(R.drawable.ic_vpn_key_black_24dp);


        alertDialog.setPositiveButton("Take",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        dialog.cancel();
                        MyDialogFragment dialogFragment = MyDialogFragment.newInstance();
                        dialogFragment.show(getSupportFragmentManager(), null);



                    }
                });


        alertDialog.show();
    }


    private void initInterface(){

        Button buttonProfile = (Button) findViewById(R.id.f_button_profile);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameProfileActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fButtonAdd = (FloatingActionButton) findViewById(R.id.f_button_add);
        fButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateMarkers();
                mapFacade.setCamera(myLocation());
            }
        });
    }





    private void loadMarkers(){
        db = new FBPlace();
        FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_MARKS).addValueEventListener(//глобальный и постоянный прослушиватель всех данных marks
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("onDataChange", "refreshing markers");

                        cHandler.clear();
                        mapFacade.clear();

                        db.FBlist.clear();

                        db.receive(dataSnapshot);

                        for(Place place : db.FBlist){
                            Marker marker = mapFacade.build(place);
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
        }


    }


    UserDataFBHandler FBHandler = new UserDataFBHandler(instance.user.getUid());

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





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("onRequestPermissions", "onRequestPermissionsResult()");
        switch ( requestCode ) {
            case REQ_PERMISSION: {
                if ( grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    // Permission granted
                    if(checkPermission())
                        mapFacade.setUserLocationEnabled(true);

                } else {
                    // Permission denied

                }
                break;
            }
        }
    }
    public boolean checkPermission() {
        Log.d("checkPermission", "checkPermission()");
        return ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ;
    }

    private void askPermission() {
        Log.d("askPermission", "askPermission()");
        ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION);
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
/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }*/

}
