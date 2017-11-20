package kom.hikeside.View.layoutCode.Profile;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kom.hikeside.Custom.Adapters.QuestAdapter;
import kom.hikeside.Game.Map.Quest;
import kom.hikeside.R;
import kom.hikeside.Service.Singleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileQuestsFragment extends Fragment {

    TextView textViewCurrentQuest;

    public ProfileQuestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_quests, container, false);


        textViewCurrentQuest = (TextView) v.findViewById(R.id.textView_current_quest);

        Singleton instance = Singleton.getInstance();
        instance.user.getUid();

        try {
            textViewCurrentQuest.setText(instance.userData.acceptedQuests.get(0).getName());
        }catch(IndexOutOfBoundsException ie){
            textViewCurrentQuest.setText("No Quest");
        }
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
