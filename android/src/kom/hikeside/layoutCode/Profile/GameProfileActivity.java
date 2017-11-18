package kom.hikeside.layoutCode.Profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import kom.hikeside.R;

public class GameProfileActivity extends AppCompatActivity {
    android.app.FragmentManager manager = getFragmentManager();



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.game_profile_character:
                    ProfileCharacterFragment characterFragment = new ProfileCharacterFragment();
                    manager.beginTransaction().replace(R.id.content, characterFragment, characterFragment.getTag()).commit();
                    return true;
                case R.id.game_profile_inventory:
                    ProfileInventoryFragment inventoryFragment = new ProfileInventoryFragment();
                    manager.beginTransaction().replace(R.id.content, inventoryFragment, inventoryFragment.getTag()).commit();
                    return true;
                case R.id.game_profile_skills:
                    ProfileSkillsFragment skillsFragment = new ProfileSkillsFragment();
                    manager.beginTransaction().replace(R.id.content, skillsFragment, skillsFragment.getTag()).commit();
                    return true;
                case R.id.game_profile_quests:
                    ProfileQuestsFragment questsFragment = new ProfileQuestsFragment();
                    manager.beginTransaction().replace(R.id.content, questsFragment, questsFragment.getTag()).commit();
                    return true;
            }
            return false;

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_profile);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);





    }

}
