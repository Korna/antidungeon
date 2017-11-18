package kom.hikeside.layoutCode.Fragments;


import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kom.hikeside.Content.MainItemType;
import kom.hikeside.Custom.ModelView;
import kom.hikeside.Custom.MyRecyclerAdapter;
import kom.hikeside.Custom.OnRecyclerViewItemClickListener;
import kom.hikeside.FBDBHandler.UserDataFBHandler;
import kom.hikeside.Game.Objects.BuildItems;
import kom.hikeside.R;
import kom.hikeside.Singleton;


public class BuildFragment extends Fragment {//recyclerView фрагмент с горизонтальной прокруткой


    public BuildFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_build, container, false);

        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_build);
        mRecyclerView.setLayoutManager(layoutManager);


        final MyRecyclerAdapter adapter = new MyRecyclerAdapter(createMockList(), R.layout.item_building);
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<ModelView>() {
            @Override
            public void onItemClick(View view, ModelView viewModel) {
                adapter.remove(viewModel);

            }
        });

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());



        return v;
    }
    private List<ModelView> createMockList() {
        List<ModelView> items = new ArrayList<>();
        for (int i = 1; i <=20; i++) {
            Drawable d = ContextCompat.getDrawable(getActivity(), android.R.drawable.ic_delete);
            items.add(new ModelView("key", "Item " + i, 1, d, MainItemType.Armour));
        }
        return items;
    }


}
