package kom.hikeside.FBDBHandler;

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
import kom.hikeside.Atom.UserData;
import kom.hikeside.Game.Objects.BuildItems;
import kom.hikeside.Game.Objects.GameCharacter;
import kom.hikeside.Game.Objects.Inventory.InventoryObject;
import kom.hikeside.Singleton;
import kom.hikeside.libgdx.BundleToLib;

import static kom.hikeside.Constants.FB_DIRECTORY_BUILD_ITEMS;
import static kom.hikeside.Constants.FB_DIRECTORY_CHARS;
import static kom.hikeside.Constants.FB_DIRECTORY_INVENTORY;
import static kom.hikeside.Constants.FB_DIRECTORY_MARKS;
import static kom.hikeside.Constants.FB_DIRECTORY_USERS;
import static kom.hikeside.Constants.FB_DIRECTORY_USER_DATA;

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

            reference = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_USERS).child(uid).child(FB_DIRECTORY_INVENTORY);

            for(InventoryObject item : items){
                String tableItemId = reference.push().getKey();//ключ остается заголовком объекта, но не полем объекта

                reference.child(tableItemId).setValue(item);
            }

        }

    }

    public void addPlace(Place place){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_MARKS);
        String key = ref.push().getKey();

        place.setId(key);
        place.setUid(uid);

        ref.child(key).setValue(place);

    }

    public void deletePlace(String key){
        //запрос к удаленной бд на удаление
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query marksQuery = ref.child(FB_DIRECTORY_MARKS).orderByChild("id").equalTo(key);

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


    public void setCurrentCharacter(String currentCharacterKey){
        DatabaseReference reference;

        reference = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_USERS).child(uid);
        String tableCharId = reference.push().getKey();//ключ остается заголовком объекта, но не полем объекта
        reference.child(tableCharId).setValue(currentCharacterKey);
    }





    public void addCharacter(GameCharacter character){
        DatabaseReference reference;

        reference = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_USERS).child(uid).child(FB_DIRECTORY_CHARS);

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


        Task s = ref.child(FB_DIRECTORY_USERS).child(uid).child(FB_DIRECTORY_CHARS).child(key).removeValue();

        Log.w( "delete", "removed from firebase");
    }

    public ArrayList<GameCharacter> getCharacters(){
        final ArrayList<GameCharacter> list = new ArrayList<>();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_USERS);

        ref.child(uid).child(FB_DIRECTORY_CHARS).addListenerForSingleValueEvent(//глобальный и постоянный прослушиватель всех данных marks
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snap: dataSnapshot.getChildren()){
                            GameCharacter object = snap.getValue(GameCharacter.class);
                            String key = snap.getKey();
                            object.setKey(key);
                            list.add(object);
                            Log.v("received:", object.getName() + " " + key);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });


        return list;
    }

    UserData object = null;
    public UserData getUserData(){

        FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_USERS).child(uid).child(FB_DIRECTORY_USER_DATA).addListenerForSingleValueEvent(//глобальный и постоянный прослушиватель всех данных marks
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        object = dataSnapshot.getValue(UserData.class);


                        // TODO !fix this shit
                        // TODO fix this shit
                        if(object != null) {
                            Singleton instance = Singleton.getInstance();
                            instance.currentGameCharacter = getGameCharacter(object.getCurrentCharacter());
                            Log.i("onDataChange", "current game Char Loaded");
                        }
                        else{
                            Log.e("onDataChange", "character not set");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
        return object;
    }

    public UserData createNewUserData(){//возвращает дефолтно созданный аккаунт юзера
        UserData userData = new UserData(uid, 100, 1, 0);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_USERS);

        ref.child(uid).child(FB_DIRECTORY_USER_DATA).setValue(userData);

        return userData;
    }

    public UserData updateUserData(UserData userData){//возвращает дефолтно созданный аккаунт юзера
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_USERS);

       // ref.child(uid).child("userData").setValue(userData);

        return userData;
    }

    public void updateUserDataCharacterStatus(String status) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_USERS);
            ref.child(uid).child(FB_DIRECTORY_USER_DATA).child("currentCharacter").setValue(status);
    }

    private GameCharacter gameCharacter = null;
    public GameCharacter getGameCharacter(final String key){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_USERS);

        ref.child(uid).child(FB_DIRECTORY_CHARS).child(key).addListenerForSingleValueEvent(//глобальный и постоянный прослушиватель всех данных marks
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        gameCharacter = dataSnapshot.getValue(GameCharacter.class);
                        try {
                            gameCharacter.setKey(key);
                            Log.d("found gameChar", gameCharacter.getName() + " " + gameCharacter.getKey());
                            Singleton instance = Singleton.getInstance();
                            instance.currentGameCharacter = gameCharacter;
                            BundleToLib bundle = BundleToLib.getInstance();
                            bundle.gameCharacters.add(gameCharacter);
                        }catch(Exception e){
                            Log.e("gameCharNotFound", e.toString());
                            updateUserDataCharacterStatus("NoCharacter");
                        }



                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

        return gameCharacter;
    }

    public void setBuildItema(BuildItems buildItema, String characterKey){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_USERS);


        ref.child(uid).child(FB_DIRECTORY_CHARS).child(characterKey).child(FB_DIRECTORY_BUILD_ITEMS).setValue(buildItema);


    }



}
