package kom.hikeside.FBDBHandler;

import com.google.firebase.database.DataSnapshot;

import kom.hikeside.Atom.User;

/**
 * Created by Koma on 14.08.2017.
 */

public class FBUser extends FBDB<User> {

    public FBUser(){
        super();
        identifier = "users";
    }


    @Override
    public void receive(DataSnapshot dataSnapshot) {
        for(DataSnapshot snap: dataSnapshot.getChildren()){
            User object = snap.getValue(User.class);
            FBlist.add(object);
        }


    }
}
