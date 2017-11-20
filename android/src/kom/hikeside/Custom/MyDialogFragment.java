package kom.hikeside.Custom;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kom.hikeside.Models.MainItemType;
import kom.hikeside.Game.Mechanic.Randomizer;
import kom.hikeside.R;
import kom.hikeside.Game.libgdx.BundleToLib;

/**
 * Created by Koma on 22.09.2017.
 */

public class MyDialogFragment extends DialogFragment {

    public static MyDialogFragment newInstance() {
        return new MyDialogFragment();
    }

    TextView textView;
    Button buttonOk;

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    // this method create view for your Dialog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_reward_fragment, container, false);


        textView = (TextView) v.findViewById(R.id.textView_dialog_experience);
        buttonOk = (Button) v.findViewById(R.id.button_dialog_ok) ;
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performClose();
            }
        });

        mRecyclerView = (RecyclerView) v.findViewById(R.id.dialog_recyclerView);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        //setadapter
        adapter = new MyRecyclerAdapter(createMockList(), R.layout.item_building);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        loadData();

        return v;
    }
    private void performClose(){
        this.getDialog().cancel();
    }

    private void loadData(){
        BundleToLib bundleToLib = BundleToLib.getInstance();
        textView.setText(String.valueOf(bundleToLib.getExperience()));

    }
    private List<ModelView> createMockList() {
        List<ModelView> items = new ArrayList<>();
        for (int i = 1; i <=20; i++) {
            Drawable d = ContextCompat.getDrawable(getContext(), android.R.drawable.ic_delete);//хранящее в памяти значение . нах сейчас надо, если всё равно меняется при показе?
            String concreteItem = Randomizer.getSimpleItem().getConcreteType();

            ModelView modelView = new ModelView(concreteItem, concreteItem, 1, d, MainItemType.Armour);
            items.add(modelView);
        }
        return items;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        //super.onSaveInstanceState(outState);
    }

}