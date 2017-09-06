package kom.hikeside.layoutCode.Character;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kom.hikeside.R;
import kom.hikeside.layoutCode.Profile.ProfileQuestsFragment;

public class CharacterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        FragmentManager manager = getFragmentManager();

        CharacterAddFragment characterAddFragment = new CharacterAddFragment();
        manager.beginTransaction().replace(R.id.layout_character, characterAddFragment, characterAddFragment.getTag()).commit();

    }
}
