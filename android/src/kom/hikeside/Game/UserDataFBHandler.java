package kom.hikeside.Game;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kom.hikeside.Atom.Place;
import kom.hikeside.Game.Objects.GameCharacter;
import kom.hikeside.Game.Objects.GameClass;
import kom.hikeside.Game.Objects.Inventory.InventoryObject;
import kom.hikeside.Singleton;

/**
 * Created by Koma on 05.09.2017.
 */

public class UserDataFBHandler {
    String uid;//id of any user

    public UserDataFBHandler(String uid){
        this.uid = uid;
    }

    public void addItemInUserInventory(ArrayList<InventoryObject> items){
        DatabaseReference reference;

        if(items != null) {

            reference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("inventory");

            for(InventoryObject item : items){
                String tableItemId =
                        reference.push()
                                .getKey();//ключ остается заголовком объекта, но не полем объекта

                reference.child(tableItemId).setValue(item);
            }

        }

    }
    private void deleteItem(){

    }
    public void addPlace(Place place){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("marks");
        String key = ref.push().getKey();

        place.setId(key);
        place.setUid(uid);

        ref.child(key).setValue(place);

    }

    public void deletePlace(String key){
        //запрос к удаленной бд на удаление
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query marksQuery = ref.child("marks").orderByChild("id").equalTo(key);

        marksQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    snap.getRef().removeValue();
                    Log.w( "dataChange", "removed from firebase");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e( "onCancelled", databaseError.toString());
            }
        });

    }


    public void addCharacter(GameCharacter character){
        DatabaseReference reference;

        reference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("characters");

        String tableCharId = reference.push().getKey();//ключ остается заголовком объекта, но не полем объекта
        reference.child(tableCharId).setValue(character);
    }

    public void deleteCharacter(String key){
        DatabaseReference ref;

        ref = FirebaseDatabase.getInstance().getReference();/*
        Query marksQuery = ref.child("users").child(uid).child("characters").equalTo(key);

        marksQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    snap.getRef().removeValue();
                    Log.w( "dataChange", "removed from firebase");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e( "onCancelled", databaseError.toString());
            }
        });*/


        Task s = ref.child("users").child(uid).child("characters").child(key).removeValue();

        Log.w( "delete", "removed from firebase");
    }

    public ArrayList<GameCharacter> getCharacters(){
        final ArrayList<GameCharacter> list = new ArrayList<>();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        ref.child(uid).child("characters").addListenerForSingleValueEvent(//глобальный и постоянный прослушиватель всех данных marks
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snap: dataSnapshot.getChildren()){
                            GameCharacter object = snap.getValue(GameCharacter.class);
                            String key = snap.getKey();
                            object.setKey(key);
                            list.add(object);
                            Log.v("received&added:", key + " " + object.toString());
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });


        return list;
    }


}
