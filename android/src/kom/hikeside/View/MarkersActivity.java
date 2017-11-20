package kom.hikeside.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import kom.hikeside.R;
import kom.hikeside.View.layoutCode.Fragments.CharacterItemBuildFragment;
import kom.hikeside.View.layoutCode.Fragments.InventoryFragment;

public class MarkersActivity extends AppCompatActivity {

    InventoryFragment addFragment;
    CharacterItemBuildFragment characterItemBuildFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markers);

        addFragment = new InventoryFragment();
        characterItemBuildFragment = new CharacterItemBuildFragment();

        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.inventory_layout, addFragment, addFragment.getTag()).commit();
        manager.beginTransaction().replace(R.id.character_build_layout, characterItemBuildFragment, characterItemBuildFragment.getTag()).commit();



    }



}
