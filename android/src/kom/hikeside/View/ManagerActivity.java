package kom.hikeside.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import kom.hikeside.Models.Atom.Place;
import kom.hikeside.Custom.Adapters.MarkAdapter;
import kom.hikeside.R;
import kom.hikeside.Service.Singleton;
import kom.hikeside.View.layoutCode.Fragments.AddPlaceFragment;

import static kom.hikeside.Constants.FB_DIRECTORY_MARKS;

public class ManagerActivity extends AppCompatActivity {

    @Bind(R.id.listView_manager)
    ListView listView;//показ дб

    private ArrayList<Place> list;//дб в памяти

    DatabaseReference databaseReference;//дб на серваке

    Singleton instance = Singleton.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        ButterKnife.bind(this);

        list = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_MARKS);



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
