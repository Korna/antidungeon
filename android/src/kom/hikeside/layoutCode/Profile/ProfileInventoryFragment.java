package kom.hikeside.layoutCode.Profile;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import kom.hikeside.Custom.Adapters.InventoryAdapter;
import kom.hikeside.Custom.ModelView;
import kom.hikeside.FBDBHandler.UserDataFBHandler;
import kom.hikeside.Game.Objects.BuildItems;
import kom.hikeside.Game.Objects.Inventory.InventoryObject;
import kom.hikeside.R;
import kom.hikeside.Singleton;

import static com.badlogic.gdx.math.MathUtils.random;
import static kom.hikeside.Constants.FB_DIRECTORY_INVENTORY;
import static kom.hikeside.Constants.FB_DIRECTORY_USERS;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileInventoryFragment extends Fragment {

    final ArrayList<ImageView> imageViewArrayList = new ArrayList<>();

    final private ArrayList<ModelView> list = new ArrayList<>();

    GridView gvMain;
    InventoryAdapter adapter;


    private BuildItems buildItems;

    public ProfileInventoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_inventory, container, false);



        loadImageViews(v);

        final Singleton instance = Singleton.getInstance();

        try {
            buildItems = instance.currentGameCharacter.buildItems;
        }catch(NullPointerException npe){
            Log.e("not loaded", npe.toString());
            buildItems = new BuildItems();
        }

        loadDrawable(buildItems);


        adapter = new InventoryAdapter(getActivity(), list);



        gvMain = (GridView) v.findViewById(R.id.gridView_inventory);
        gvMain.setAdapter(adapter);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int color = 0x00FFFFFF; // Transparent
                view.setBackgroundColor(color);


                ModelView modelView = list.get(position);

                buildItems.addItem(modelView.mainItemType, modelView.concreteType);

                loadDrawable(buildItems);//взаимоднефйствие с чаром

                UserDataFBHandler FBHandler = new UserDataFBHandler(instance.user.getUid());
                FBHandler.setBuildItema(buildItems, instance.userData.getCurrentCharacter());
                Log.i("added", "item " + modelView.getConcreteType());

                //adapter.itemList.remove(position);
               // adapter.notifyDataSetChanged();

            }
        });

        adjustGridView();



        return v;
    }

    private void adjustGridView() {
        gvMain.setNumColumns(4);
        gvMain.setColumnWidth(200);
      //  gvMain.setBackgroundColor(0xFF0000FF);

        gvMain.setVerticalSpacing(10);
        gvMain.setHorizontalSpacing(10);
        gvMain.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

    }

    private void loadImageViews(View v){
        ImageView imageViewAccessory = (ImageView) v.findViewById(R.id.imageView_accessory);
        ImageView imageViewRing = (ImageView) v.findViewById(R.id.imageView_ring);
        ImageView imageViewHead = (ImageView) v.findViewById(R.id.imageView_head);
        ImageView imageViewBody = (ImageView) v.findViewById(R.id.imageView_body);
        ImageView imageViewHandLeft = (ImageView) v.findViewById(R.id.imageView_hand_shield);
        ImageView imageViewHandRight = (ImageView) v.findViewById(R.id.imageView_weapon);
        ImageView imageViewLegs = (ImageView) v.findViewById(R.id.imageView_legs);
        ImageView imageViewHands = (ImageView) v.findViewById(R.id.imageView_hands);


        imageViewArrayList.add(imageViewBody);
        imageViewArrayList.add(imageViewHandRight);
        imageViewArrayList.add(imageViewHandLeft);
        imageViewArrayList.add(imageViewLegs);
        imageViewArrayList.add(imageViewHead);
        imageViewArrayList.add(imageViewRing);
        imageViewArrayList.add(imageViewAccessory);
        imageViewArrayList.add(imageViewHands);

        for(final ImageView imageView : imageViewArrayList){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView.setImageDrawable(null);
                }
            });
        }
    }

    private void loadDrawable(BuildItems buildItems){
        ArrayList<String> itemNames = new ArrayList<String>();



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
        for(int j = 0; j < imageViewArrayList.size(); ++j){

            ImageView imageView = imageViewArrayList.get(j);
            try {
                try {

                    ims = getActivity().getAssets().open("items/" + itemNames.get(j) + ".png");
                    d = Drawable.createFromStream(ims, null);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                  //  options.inScaled = false;
                    options.outHeight=64;
                    options.outWidth=64;

                    Bitmap b = BitmapFactory.decodeStream(ims, null, options);

                    Bitmap b2 = Bitmap.createScaledBitmap(b, 64, 64, false);

                    imageView.setImageBitmap(b2);

                }catch(IndexOutOfBoundsException e){
                    Log.e("array", e.toString());
                    d = ContextCompat.getDrawable(getActivity(), android.R.drawable.ic_delete);
                }

            } catch(IOException ex) {
                Log.e("assets", ex.toString());
                d = ContextCompat.getDrawable(getActivity(), android.R.drawable.ic_delete);
            }
            //imageView.setImageDrawable(d);



        }
    }

    @Override
    public void onStart(){
        super.onStart();


        Singleton instance = Singleton.getInstance();

        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_USERS).child(instance.user.getUid()).child(FB_DIRECTORY_INVENTORY);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                adapter.itemList.clear();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    InventoryObject model = snap.getValue(InventoryObject.class);

                    ModelView viewModel = new ModelView(snap.getKey(), model.getConcreteType(), random.nextInt(3), null, model.getMainType());

                    Log.d("added:", model.getConcreteType() + " firebase key is:" + snap.getKey());
                    adapter.itemList.add(viewModel);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
