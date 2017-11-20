package kom.hikeside.Custom.MarkerInfoWindows;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kom.hikeside.Models.Atom.Place;
import kom.hikeside.Models.MainItemType;
import kom.hikeside.Custom.ModelView;
import kom.hikeside.Custom.MyRecyclerAdapter;
import kom.hikeside.Custom.OnRecyclerViewItemClickListener;
import kom.hikeside.Game.Mechanic.Randomizer;
import kom.hikeside.R;

/**
 * Created by Koma on 29.08.2017.
 */

public class FightFragment extends Fragment {
    TextView textViewName;
    TextView textViewType;
    TextView textViewLvl;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.window_fight, container, false);

        textViewName = (TextView) view.findViewById(R.id.tv_name);
        textViewLvl = (TextView) view.findViewById(R.id.tv_lng);
        setInfo();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.tv_button:
                        setupDialog();

                        break;
                    default:
                        break;
                }


            }
        };

        view.findViewById(R.id.tv_button).setOnClickListener(onClickListener);




    }

    private void setupDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setTitle("Битва");
        alertDialog.setMessage("Вы уверены?");

        //alertDialog.setIcon(R.drawable.ic_vpn_key_black_24dp);


        alertDialog.setPositiveButton("ОК", dialogInterface);

        alertDialog.setNegativeButton("Назад",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    private void setupRewardDialog(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setTitle("Reward");
        alertDialog.setMessage("You won");
        Log.d("dialog", "You won");
        //alertDialog.setIcon(R.drawable.ic_vpn_key_black_24dp);

        RecyclerView recyclerView = new RecyclerView(getActivity());
        final MyRecyclerAdapter adapter = new MyRecyclerAdapter(createMockList(), R.layout.item_building);
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<ModelView>() {
            @Override
            public void onItemClick(View view, ModelView viewModel) {
                adapter.remove(viewModel);

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(alertDialog.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
       // recyclerView.setItemAnimator(new DefaultItemAnimator());



        alertDialog.setPositiveButton("Take",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        dialog.cancel();


                    }
                });


        alertDialog.show();
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


    private void setupFailDialog(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setTitle("Fail");
        alertDialog.setMessage("You faled");
        Log.d("dialog", "You failed");
        //alertDialog.setIcon(R.drawable.ic_vpn_key_black_24dp);


        alertDialog.setPositiveButton("Revive character",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        dialog.cancel();


                    }
                });


        alertDialog.show();
    }


    String title;
    String lvlText;
    DialogInterface.OnClickListener dialogInterface;
    private void setInfo(){

        textViewName.setText(title);
        textViewLvl.setText(lvlText);

    }
    public void LoadWindowInfo(String title, String text, DialogInterface.OnClickListener dialogInterface){
        this.title = title;
        this.lvlText = text;
        this.dialogInterface = dialogInterface;
    }
    private Place place;
    public void loadPlace(Place place){
        this.place = place;

    }
/*
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }*/

}