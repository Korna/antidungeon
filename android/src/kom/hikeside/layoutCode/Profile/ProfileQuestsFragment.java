package kom.hikeside.layoutCode.Profile;


import android.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionDefault;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionRemoveItem;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.annotation.SwipeableItemDrawableTypes;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.annotation.SwipeableItemResults;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractSwipeableItemViewHolder;

import java.util.ArrayList;
import java.util.List;

import kom.hikeside.Atom.Place;
import kom.hikeside.Custom.Adapters.MarkAdapter;
import kom.hikeside.Custom.Adapters.QuestAdapter;
import kom.hikeside.Game.Map.Quest;
import kom.hikeside.R;
import kom.hikeside.layoutCode.ManagerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileQuestsFragment extends Fragment {


    public ProfileQuestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_quests, container, false);

        ListView listView =(ListView) v.findViewById(R.id.listView_quests);

        ArrayList<Quest> list = new ArrayList<>();
        Quest quest = new Quest();
        quest.setName("random");
        list.add(quest);
        

        QuestAdapter customList = new QuestAdapter(getActivity(), list);
        listView.setAdapter(customList);


        return v;
    }







}
