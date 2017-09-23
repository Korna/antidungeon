package kom.hikeside.layoutCode.Fragments;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kom.hikeside.FBDBHandler.UserDataFBHandler;
import kom.hikeside.Game.Objects.GameCharacter;
import kom.hikeside.R;
import kom.hikeside.Singleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {
    GameCharacter character;

    TextView textView_strength;
    TextView textView_agility;
    TextView textView_intelligence;
    TextView textView_stamina;
    TextView textView_luck;
    TextView textView_will;



    TextView textView_name;
    TextView textView_lvl;
    TextView textView_class;

    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stats, container, false);

        /*      отключено ввиду того, что не происходит синхронизации в памяти устройства
        Bundle args = getArguments();
        GameCharacter c = (GameCharacter) args.getSerializable("GameCharacter");
        character = c;*/

        Singleton instance = Singleton.getInstance();
        this.character = instance.currentGameCharacter;

        if(this.character != null){
            initGui(v);
            initButtons(v);

            handleProgress();

            loadCharacterStats();
            loadCharacterCommon();
        }

        return v;
    }




    public void initGui(View v){
        textView_strength = (TextView) v.findViewById(R.id.textView_strength);

        textView_agility = (TextView) v.findViewById(R.id.textView_agility);

        textView_intelligence = (TextView) v.findViewById(R.id.textView_intelligence);

        textView_stamina = (TextView) v.findViewById(R.id.textView_stamina);

        textView_luck = (TextView) v.findViewById(R.id.textView_luck);

        textView_will = (TextView) v.findViewById(R.id.textView_will);

        textView_name = (TextView) v.findViewById(R.id.textView_name);

        textView_lvl = (TextView) v.findViewById(R.id.textView_lvl);

        textView_class = (TextView) v.findViewById(R.id.textView_class);


    }
    private ArrayList<ImageView> imageViewArrayList = new ArrayList<>();

    private void initButtons(View v){
        ImageView imageView_strength = (ImageView) v.findViewById(R.id.imageView_strength);
        imageView_strength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                character.buildStats.setStrength(character.buildStats.getStrength() + 1);

                synchronize();
            }
        });

        ImageView imageView_agility = (ImageView) v.findViewById(R.id.imageView_agility);
        imageView_agility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                character.buildStats.setAgility(character.buildStats.getAgility() + 1);

                synchronize();
            }
        });

        ImageView imageView_intelligence = (ImageView) v.findViewById(R.id.imageView_intelligence);
        imageView_intelligence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                character.buildStats.setIntelligence(character.buildStats.getIntelligence() + 1);

                synchronize();
            }
        });

        ImageView imageView_stamina = (ImageView) v.findViewById(R.id.imageView_stamina);
        imageView_stamina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                character.buildStats.setStamina(character.buildStats.getStamina() + 1);

                synchronize();
            }
        });

        ImageView imageView_luck = (ImageView) v.findViewById(R.id.imageView_luck);
        imageView_luck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                character.buildStats.setLuck(character.buildStats.getLuck() + 1);

                synchronize();
            }
        });

        ImageView imageView_will = (ImageView) v.findViewById(R.id.imageView_will);
        imageView_will.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                character.buildStats.setWill(character.buildStats.getWill() + 1);

                synchronize();
            }
        });

        imageViewArrayList.add(imageView_strength);
        imageViewArrayList.add(imageView_agility);
        imageViewArrayList.add(imageView_intelligence);
        imageViewArrayList.add(imageView_stamina);
        imageViewArrayList.add(imageView_luck);
        imageViewArrayList.add(imageView_will);
    }

    private void synchronize(){
        decreaseSkillPoint();
        handleProgress();
        loadCharacterStats();

        Singleton instance = Singleton.getInstance();
        String uid = instance.user.getUid();
        //instance.currentGameCharacter;
        UserDataFBHandler FBHandler = new UserDataFBHandler(uid);

        FBHandler.setBuildStats(character.buildStats, instance.userData.getCurrentCharacter());

    }

    private void handleProgress(){
        if( true )
            setButtonsVisibility(true);
        else
            setButtonsVisibility(false);
    }

    private boolean checkForSkillPoints(){
        boolean result = false;

        if(character.getSkillPoints() > 0)
            result = true;

        return result;
    }

    private void decreaseSkillPoint(){
        character.setSkillPoints(character.getSkillPoints() - 1);
    }

    private void setButtonsVisibility(boolean visibility){
        for(ImageView imageView : imageViewArrayList){

            if(visibility){
                imageView.setVisibility(View.VISIBLE);
            }else
                imageView.setVisibility(View.GONE);

        }
    }



    private void loadCharacterStats(){

        textView_strength.setText("" + character.buildStats.getStrength());

        textView_agility.setText("" +character.buildStats.getAgility());

        textView_intelligence.setText("" +character.buildStats.getIntelligence());

        textView_stamina.setText("" +character.buildStats.getStamina());

        textView_luck.setText("" +character.buildStats.getLuck());

        textView_will.setText("" +character.buildStats.getWill());

    }

    private void loadCharacterCommon(){

        textView_class.setText(character.getGameClass().toString());

        textView_name.setText(" " + character.getName());

        textView_lvl.setText(" " + character.getLvl());

    }

}
