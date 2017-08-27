package kom.hikeside.FBDBHandler;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import kom.hikeside.Atom.Place;

/**
 * Created by Koma on 14.08.2017.
 */

public class FBPlace extends FBDB<Place> {

    public FBPlace(){
        super();
        identifier = "places";
    }

    @Override
    public void receive(DataSnapshot dataSnapshot) {
        for(DataSnapshot snap: dataSnapshot.getChildren()){
            Place object = snap.getValue(Place.class);
            FBlist.add(object);
            Log.v("received&added", object.toString());
        }
    }
}
