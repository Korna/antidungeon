package kom.hikeside.layoutCode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import kom.hikeside.R;
import kom.hikeside.layoutCode.Fragments.InventoryFragment;

public class MarkersActivity extends AppCompatActivity {

    InventoryFragment addFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markers);

        addFragment = new InventoryFragment();
        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.profile_layout, addFragment, addFragment.getTag()).commit();


    }



}
