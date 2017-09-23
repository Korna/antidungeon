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

import java.util.ArrayList;
import java.util.List;

import kom.hikeside.Content.MainItemType;
import kom.hikeside.Custom.MarkerInfoWindows.FightFragment;
import kom.hikeside.Game.Mechanic.Randomizer;
import kom.hikeside.R;

/**
 * Created by Koma on 22.09.2017.
 */

public class MyDialogFragment extends DialogFragment {
    public static MyDialogFragment newInstance() {
        return new MyDialogFragment();
    }


    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    // this method create view for your Dialog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate layout with recycler view
        View v = inflater.inflate(R.layout.dialog_reward_fragment, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.dialog_recyclerView);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        //setadapter
        adapter = new MyRecyclerAdapter(createMockList(), R.layout.item_building);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //get your recycler view and populate it.
        return v;
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