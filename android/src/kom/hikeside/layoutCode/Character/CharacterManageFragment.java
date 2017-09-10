package kom.hikeside.layoutCode.Character;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import kom.hikeside.Game.Objects.GameClasses.GameCharacter;
import kom.hikeside.Game.Objects.GameClasses.GameClass;
import kom.hikeside.FBDBHandler.UserDataFBHandler;
import kom.hikeside.R;
import kom.hikeside.Singleton;
import kom.hikeside.layoutCode.Fragments.StatsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharacterManageFragment extends Fragment {


    GameCharacter character = new GameCharacter(" ", 0, GameClass.priest, 0,0,0,0,0,0);
    Bundle args = new Bundle();
    StatsFragment statsFragment = new StatsFragment();

    int i = 0;

    public CharacterManageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_character_manage, container, false);

        Singleton instance = Singleton.getInstance();

        final UserDataFBHandler FBHandler = new UserDataFBHandler((instance.user.getUid()));
        final ArrayList<GameCharacter> list = FBHandler.getCharacters();



        Button buttonForward = (Button) v.findViewById(R.id.button_character_forward);
        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.size() > i + 1){
                    ++i;

                    character = list.get(i);
                    View v = statsFragment.getView();
                    statsFragment.loadCharacterCommon(v, character);
                    statsFragment.loadCharacterStats(v, character);
                }

            }
        });
        Button buttonBack = (Button) v.findViewById(R.id.button_character_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i >= 1 && list.size() != 0){
                    --i;
                    character = list.get(i);
                    View v = statsFragment.getView();
                    statsFragment.loadCharacterCommon(v, character);
                    statsFragment.loadCharacterStats(v, character);
                }

            }
        });

        Button buttonDelete = (Button) v.findViewById(R.id.button_character_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.size() != 0){
                    String key = list.get(i).getKey();
                    FBHandler.deleteCharacter(key);
                    FBHandler.updateUserDataCharacterStatus("Not Selected");
                    list.remove(i);
                    Log.d("click", "character deleted");
                }


                if(i>0 && list.size() != 0)
                    character = list.get(i-1);
                else
                    character = new GameCharacter("No characters left after deletiong", 0, GameClass.priest, 0,0,0,0,0,0);

                View v = statsFragment.getView();
                statsFragment.loadCharacterCommon(v, character);
                statsFragment.loadCharacterStats(v, character);
            }
        });

        Button buttonSet = (Button) v.findViewById(R.id.button_character_set);
        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.size() != 0){
                    String key = list.get(i).getKey();
                    //  FBHandler.setCurrentCharacter(key);
                    Singleton instance = Singleton.getInstance();
                    FBHandler.updateUserDataCharacterStatus(key);
                    instance.userData.setCurrentCharacter(key);
                 //   FBHandler.updateUserData(instance.userData);

                }

            }
        });


        if(list.size() != 0)
            character = list.get(0);

        args.putSerializable("GameCharacter", character);
        statsFragment.setArguments(args);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_select_stats, statsFragment, statsFragment.getTag()).addToBackStack(statsFragment.getTag()).commit();



        return v;
    }

}
