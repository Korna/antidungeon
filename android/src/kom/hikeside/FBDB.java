package kom.hikeside;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kom.hikeside.Atom.Place;

/**
 * Created by Koma on 14.08.2017.
 */

public class FBDB {

    public DatabaseReference myRef;
    ArrayList<Place> placeList;

    public FBDB(){
        placeList = new ArrayList<>();
    }


    public void onStart(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                placeList.clear();

                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    Place place = snap.getValue(Place.class);
                    placeList.add(place);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void loadAdapter(){



    }

}
