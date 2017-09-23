package kom.hikeside.layoutCode.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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


public class InventoryFragment extends Fragment {//сетка с инвентарем

    private ArrayList<ModelView> list;//дб в памяти

    GridView gvMain;
    InventoryAdapter adapter;

    public InventoryFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_inventory, container, false);

        list = new ArrayList<>();


        adapter = new InventoryAdapter(getActivity(), list);



        gvMain = (GridView) v.findViewById(R.id.gridView_inventory);
        gvMain.setAdapter(adapter);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                ModelView modelView = list.get(position);
                Singleton instance = Singleton.getInstance();

                BuildItems buildItems = instance.currentGameCharacter.buildItems;
                buildItems.addItem(modelView.mainItemType, modelView.concreteType);
                UserDataFBHandler FBHandler = new UserDataFBHandler(instance.user.getUid());
                FBHandler.setBuildItema(buildItems, instance.userData.getCurrentCharacter());
                Log.i("added", "item " + modelView.getConcreteType());

                adapter.itemList.remove(position);
                adapter.notifyDataSetChanged();

            }
        });

        adjustGridView();

        return v;
    }

    private void adjustGridView() {
        gvMain.setNumColumns(4);
        gvMain.setColumnWidth(200);
        gvMain.setVerticalSpacing(5);
        gvMain.setHorizontalSpacing(5);
        gvMain.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);

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
