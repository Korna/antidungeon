package kom.hikeside.View.layoutCode.Profile;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kom.hikeside.Game.Objects.BuildStats;
import kom.hikeside.Game.Objects.GameCharacter;
import kom.hikeside.Models.GameClass;
import kom.hikeside.R;
import kom.hikeside.View.layoutCode.Fragments.StatsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileCharacterFragment extends Fragment {


    public ProfileCharacterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_character, container, false);


        GameCharacter character = new GameCharacter("Random", 1, GameClass.Archer, new BuildStats(5,10,5,10,5,7));

        Bundle args = new Bundle();
        args.putSerializable("GameCharacter", character);

        StatsFragment statsFragment = new StatsFragment();
        statsFragment.setArguments(args);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_profile_character, statsFragment, statsFragment.getTag())
                //.addToBackStack(statsFragment.getTag())
                .commit();

        return v;
    }

    private void prepareBundle(){
/*
        Bundle args = new Bundle();
        args.putSerializable(TAG_MY_CLASS, myClass);
        Fragment toFragment = new ToFragment();
        toFragment.setArguments(args);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.body, toFragment, TAG_TO_FRAGMENT)
                .addToBackStack(TAG_TO_FRAGMENT).commit();*/

    }

}
