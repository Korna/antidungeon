package kom.hikeside.layoutCode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kom.hikeside.Atom.Place;
import kom.hikeside.Custom.MarkAdapter;
import kom.hikeside.R;
import kom.hikeside.Singleton;
import kom.hikeside.layoutCode.Fragments.AddPlaceFragment;

public class ManagerActivity extends AppCompatActivity {


    ListView listView;//показ дб

    private ArrayList<Place> list;//дб в памяти

    DatabaseReference databaseReference;//дб на серваке

    Singleton instance = Singleton.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        listView = (ListView) findViewById(R.id.listView_manager);
        list = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("marks");



        AddPlaceFragment addFragment = new AddPlaceFragment();
        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.manager_layout_add, addFragment, addFragment.getTag()).commit();

    }

    @Override
    protected void onStart(){
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    Place object = snap.getValue(Place.class);
                    list.add(object);
                }

                MarkAdapter customList = new MarkAdapter(ManagerActivity.this, list);
                listView.setAdapter(customList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
