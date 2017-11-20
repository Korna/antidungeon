package kom.hikeside.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import kom.hikeside.AndroidLauncher;
import kom.hikeside.Service.UserDataFBHandler;
import kom.hikeside.R;
import kom.hikeside.Service.Singleton;
import kom.hikeside.View.layoutCode.Character.CharacterActivity;
import kom.hikeside.View.layoutCode.Profile.GameProfileActivity;


public class MainActivity extends AppCompatActivity  implements View.OnClickListener  {//TODO сделать минимальную привязку к данным класса в связек клиент-сервр
    //т.е чтоб имелось просто название типа и линковка к таблице с модификацией параметров, если она есть

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    Singleton instance = Singleton.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                instance.user = firebaseAuth.getCurrentUser();
                if (instance.user != null) {
                    Log.d("init", "onAuthStateChanged:signed_in:" + instance.user.getUid());
                } else {
                    Log.d("init", "onAuthStateChanged:signed_out");
                }

            }
        };


        mAuth.addAuthStateListener(mAuthListener);


        loadInterface();

        UserDataFBHandler FBHandler = new UserDataFBHandler(mAuth.getCurrentUser().getUid());
        instance.userData = FBHandler.getUserData();
    }




    private void loadInterface(){
        Button manager = (Button) findViewById(R.id.button3);
        manager.setOnClickListener(this);

        Button fastGame = (Button) findViewById(R.id.button_fast_game);
        fastGame.setOnClickListener(this);

        Button mapsGame = (Button) findViewById(R.id.button_maps_game);
        mapsGame.setOnClickListener(this);


        Button profile  = (Button) findViewById(R.id.button_profile);
        profile.setOnClickListener(this);

        Button character  = (Button) findViewById(R.id.button_character);
        character.setOnClickListener(this);

        Button logout  = (Button) findViewById(R.id.button_logout);
        logout.setOnClickListener(this);

        Button markers = (Button) findViewById(R.id.button_items);
        markers.setOnClickListener(this);

        Button achievements = (Button) findViewById(R.id.button_achievements);
        achievements.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.button3:
                intent = new Intent(getApplicationContext(), ManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.button_fast_game:
                intent = new Intent(getApplicationContext(), AndroidLauncher.class);
                startActivity(intent);
                break;
            case R.id.button_maps_game:
                intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.button_profile:
                intent = new Intent(getApplicationContext(), GameProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.button_character:
                intent = new Intent(getApplicationContext(), CharacterActivity.class);
                startActivity(intent);
                break;
            case R.id.button_logout:
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button_items:
                intent = new Intent(getApplicationContext(), MarkersActivity.class);
                startActivity(intent);
                break;
            case R.id.button_achievements:
                intent = new Intent(getApplicationContext(), AchievementsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
