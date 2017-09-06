package kom.hikeside.layoutCode.Character;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kom.hikeside.Game.UserDataFBHandler;
import kom.hikeside.R;
import kom.hikeside.Singleton;
import kom.hikeside.layoutCode.Profile.ProfileQuestsFragment;

public class CharacterActivity extends AppCompatActivity {

    FragmentManager manager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        loadInterface();


    }

    private void loadInterface(){

        Button btnAdd = (Button) findViewById(R.id.button_add_character_layout);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharacterAddFragment characterAddFragment = new CharacterAddFragment();
                manager.beginTransaction().replace(R.id.layout_character, characterAddFragment, characterAddFragment.getTag()).commit();
            }
        });
        Button btnManage = (Button) findViewById(R.id.button_manage_character_layout);
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharacterManageFragment characterManageFragment = new CharacterManageFragment();
                manager.beginTransaction().replace(R.id.layout_character, characterManageFragment, characterManageFragment.getTag()).commit();
            }
        });

    }

}
