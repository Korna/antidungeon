package kom.hikeside.Service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Koma on 14.08.2017.
 */

public abstract class FBDB<E> {

    protected String identifier;
    public ArrayList<E> FBlist;

    public FBDB(){
        FBlist = new ArrayList<>();
    }


    public void insert(String key, Object object, DatabaseReference reference) {
        reference = FirebaseDatabase.getInstance().getReference(identifier);
        reference.child(key).setValue(object);
    }

    public void update(Object object, DatabaseReference reference){
        reference = FirebaseDatabase.getInstance().getReference(identifier);
        reference.setValue(object);
    }

    public abstract void receive(DataSnapshot dataSnapshot);
}
