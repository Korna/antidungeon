package kom.hikeside.View.layoutCode.Fragments;


import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import kom.hikeside.Game.Objects.BuildItems;
import kom.hikeside.R;
import kom.hikeside.Service.Singleton;

import static kom.hikeside.Constants.FB_DIRECTORY_BUILD_ITEMS;
import static kom.hikeside.Constants.FB_DIRECTORY_CHARS;
import static kom.hikeside.Constants.FB_DIRECTORY_USERS;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharacterItemBuildFragment extends Fragment {

    final ArrayList<ImageView> list = new ArrayList<>();

    public CharacterItemBuildFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();

        Singleton instance = Singleton.getInstance();
        String uid = instance.user.getUid();
        String key;
        try {
            key = instance.userData.getCurrentCharacter();
        }catch(NullPointerException e){
            Log.e("current Char key", e.toString());
            key = "";
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_USERS);
        ref.child(uid).child(FB_DIRECTORY_CHARS).child(key).child(FB_DIRECTORY_BUILD_ITEMS).addValueEventListener(//глобальный и постоянный прослушиватель всех данных marks
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    // throws NullPointerException
                    {
                        BuildItems buildItems;
                        try {
                            buildItems = dataSnapshot.getValue(BuildItems.class);
                        }catch(DatabaseException de){
                            Log.e("buildItems", de.toString());
                            buildItems = null;
                        }
                        ArrayList<String> itemNames = new ArrayList<>();

                        try{
                            itemNames.add(buildItems.armour.name());//он может всё равно добавлять нулл
                        }catch(NullPointerException e){
                            itemNames.add(null);//TODO if theres error?
                        }
                        try{
                            itemNames.add(buildItems.weapon.name());
                        }catch(NullPointerException e){
                            itemNames.add(null);
                        }
                        try{
                            itemNames.add(buildItems.shield.name());
                        }catch(NullPointerException e){
                            itemNames.add(null);
                        }
                        try{
                            itemNames.add(buildItems.legs.name());
                        }catch(NullPointerException e){
                            itemNames.add(null);
                        }
                        try{
                            itemNames.add(buildItems.head.name());
                        }catch(NullPointerException e){
                            itemNames.add(null);
                        }
                        try{
                            itemNames.add(buildItems.ring.name());
                        }catch(NullPointerException e){
                            itemNames.add(null);
                        }
                        try{
                            itemNames.add(buildItems.accessory.name());
                        }catch(NullPointerException e){
                            itemNames.add(null);
                        }
                        try{
                            itemNames.add(null);
                        }catch(NullPointerException e){
                            itemNames.add(null);
                        }


                        Drawable d;
                        InputStream ims;
                        for(int j = 0; j < list.size(); ++j){

                            ImageView imageView = list.get(j);
                            try {
                                try {
                                    try {
                                        ims = getActivity().getAssets().open("items/" + itemNames.get(j) + ".png");
                                    }catch(NullPointerException e){
                                        Log.e("null", e.toString());
                                        ims = getActivity().getAssets().open("items/" + itemNames.get(j) + ".png");
                                    }
                                    d = Drawable.createFromStream(ims, null);
                                }catch(IndexOutOfBoundsException e){
                                    Log.e("array", e.toString());
                                    d = ContextCompat.getDrawable(getActivity(), android.R.drawable.ic_delete);
                                }

                            } catch(IOException ex) {
                                Log.e("assets", ex.toString());
                                d = ContextCompat.getDrawable(getActivity(), android.R.drawable.ic_delete);
                            }
                            imageView.setImageDrawable(d);
                        }




                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_character_item_build, container, false);
        //this.setRetainInstance(true);

        ImageView imageViewAccessory = (ImageView) v.findViewById(R.id.imageView_accessory);
        ImageView imageViewRing = (ImageView) v.findViewById(R.id.imageView_ring);
        ImageView imageViewHead = (ImageView) v.findViewById(R.id.imageView_head);
        ImageView imageViewBody = (ImageView) v.findViewById(R.id.imageView_body);
        ImageView imageViewHandLeft = (ImageView) v.findViewById(R.id.imageView_hand_shield);
        ImageView imageViewHandRight = (ImageView) v.findViewById(R.id.imageView_weapon);
        ImageView imageViewLegs = (ImageView) v.findViewById(R.id.imageView_legs);
        ImageView imageViewHands= (ImageView) v.findViewById(R.id.imageView_hands);

        list.add(imageViewBody);
        list.add(imageViewHandRight);
        list.add(imageViewHandLeft);
        list.add(imageViewLegs);
        list.add(imageViewHead);
        list.add(imageViewRing);
        list.add(imageViewAccessory);
        list.add(imageViewHands);

        for(final ImageView imageView : list){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView.setImageDrawable(null);
                }
            });
        }

        return v;
    }

}
