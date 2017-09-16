package kom.hikeside.Game.Objects.Inventory;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kom.hikeside.Custom.ModelView;
import kom.hikeside.Singleton;

import static com.badlogic.gdx.math.MathUtils.random;
import static kom.hikeside.Constants.FB_DIRECTORY_INVENTORY;
import static kom.hikeside.Constants.FB_DIRECTORY_USERS;

/**
 * Created by Koma on 16.09.2017.
 */

public class WebInventoryItem {
    String key;
    String className;//armour, weapon, consumable, ring and etc
    String typeName;//exact behaviour

    public ArrayList<WebInventoryItem> getBasicItemsFromFB(){
        final ArrayList<WebInventoryItem> itemList = new ArrayList<WebInventoryItem>();

        Singleton instance = Singleton.getInstance();

        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_USERS).child(instance.user.getUid()).child(FB_DIRECTORY_INVENTORY);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    WebInventoryItem model = snap.getValue(WebInventoryItem.class);

                    InventoryObject model2 = snap.getValue(Armour.class);

                    itemList.add(model);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return itemList;
    }

    public void loadGameCharacter(ArrayList<WebInventoryItem> itemList){
        Singleton instance = Singleton.getInstance();
        String className;




    }

}
