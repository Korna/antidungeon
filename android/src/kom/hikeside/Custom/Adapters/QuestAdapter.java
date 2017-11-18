package kom.hikeside.Custom.Adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kom.hikeside.Content.MainItemType;
import kom.hikeside.Custom.ModelView;
import kom.hikeside.Custom.MyRecyclerAdapter;
import kom.hikeside.Custom.OnRecyclerViewItemClickListener;
import kom.hikeside.FBDBHandler.UserDataFBHandler;
import kom.hikeside.Game.Map.Quest;
import kom.hikeside.Game.Mechanic.Randomizer;
import kom.hikeside.R;
import kom.hikeside.Singleton;

/**
 * Created by Koma on 18.09.2017.
 */


public class QuestAdapter extends ArrayAdapter<Quest> {

    private Activity context;
    public List<Quest> questList;

    public QuestAdapter(Activity context, List<Quest> questList){
        super(context, R.layout.quest_row_list, questList);
        this.context = context;
        this.questList = questList;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View row = inflater.inflate(R.layout.quest_row_list, null, true);

        TextView textViewName = (TextView) row.findViewById(R.id.textView_name);
        TextView textViewDesc = (TextView) row.findViewById(R.id.textView_description);
        Button buttonGetQuest = (Button) row.findViewById(R.id.button_get_quest);




        buttonGetQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton instance = Singleton.getInstance();
                String uid = instance.user.getUid();

                UserDataFBHandler FBHandler = new UserDataFBHandler(uid);

                FBHandler.acceptQuest(questList.get(position).getName());


            }
        });
        Quest quest = questList.get(position);

        textViewName.setText(quest.getName());
        //textViewDesc.setText(quest.getDescription());


        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView mRecyclerView = (RecyclerView) row.findViewById(R.id.recyclerView_reward);
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



        return row;
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



}