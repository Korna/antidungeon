package kom.hikeside.layoutCode.Fragments;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kom.hikeside.Game.Objects.GameCharacter;
import kom.hikeside.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {


    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stats, container, false);

        Bundle args = getArguments();
        GameCharacter c = (GameCharacter) args.getSerializable("GameCharacter");

        loadCharacterStats(v, c);

        loadCharacterCommon(v, c);
        return v;
    }

    private void loadCharacterStats(View v, GameCharacter character){
        TextView textView_strength = (TextView) v.findViewById(R.id.textView_strength);

        TextView textView_agility = (TextView) v.findViewById(R.id.textView_agility);

        TextView textView_intelligence = (TextView) v.findViewById(R.id.textView_intelligence);

        TextView textView_stamina = (TextView) v.findViewById(R.id.textView_stamina);

        TextView textView_luck = (TextView) v.findViewById(R.id.textView_luck);

        TextView textView_will = (TextView) v.findViewById(R.id.textView_will);

        textView_strength.setText(" " + character.getStrength());

        textView_agility.setText(" " +character.getAgility());

        textView_intelligence.setText(" " +character.getIntelligence());

        textView_stamina.setText(" " +character.getStamina());

        textView_luck.setText(" " +character.getLuck());

        textView_will.setText(" " +character.getLuck());

    }

    private void loadCharacterCommon(View v, GameCharacter character){
        TextView textView_name = (TextView) v.findViewById(R.id.textView_name);

        TextView textView_lvl = (TextView) v.findViewById(R.id.textView_lvl);

        TextView textView_class = (TextView) v.findViewById(R.id.textView_class);

        textView_class.setText(character.getGameClass().toString());

        textView_name.setText(" " + character.getName());

        textView_lvl.setText(" " + character.getLvl());

    }

}
