package kom.hikeside.layoutCode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import kom.hikeside.AndroidLauncher;
import kom.hikeside.FBDBHandler.UserDataFBHandler;
import kom.hikeside.R;
import kom.hikeside.Singleton;
import kom.hikeside.layoutCode.Character.CharacterActivity;
import kom.hikeside.layoutCode.Profile.GameProfileActivity;


public class MainActivity extends AppCompatActivity {//TODO сделать минимальную привязку к данным класса в связек клиент-сервр
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
    }




    private void loadInterface(){

        Button buttonLogin = (Button) findViewById(R.id.button2);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        Button manager = (Button) findViewById(R.id.button3);
        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManagerActivity.class);
                startActivity(intent);
            }
        });

        Button fastGame = (Button) findViewById(R.id.button_fast_game);
        fastGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AndroidLauncher.class);
                startActivity(intent);
            }
        });

        Button mapsGame = (Button) findViewById(R.id.button_maps_game);
        mapsGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });



        Button profile  = (Button) findViewById(R.id.button_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameProfileActivity.class);
                startActivity(intent);

            }
        });

        Button character  = (Button) findViewById(R.id.button_character);
        character.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CharacterActivity.class);
                startActivity(intent);
            }
        });

        Button logout  = (Button) findViewById(R.id.button_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button markers = (Button) findViewById(R.id.button_items);
        markers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MarkersActivity.class);
                startActivity(intent);
            }
        });



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
}
