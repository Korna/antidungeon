package kom.hikeside.libgdx;

import android.content.Context;

import java.util.ArrayList;

import kom.hikeside.Game.Objects.GameClasses.GameCharacter;
import kom.hikeside.Singleton;
import kom.hikeside.FBDBHandler.UserDataFBHandler;
import kom.hikeside.libgdx.GameMechanics.EnemyModel;

/**
 * Created by Koma on 08.09.2017.
 */

public class BundleToLib {
    //singleton pattern
    private static volatile BundleToLib instance = new BundleToLib();
    public static BundleToLib getInstance(){
        return instance;
    }

    //input
    private boolean isCoop = false;
    private boolean isDuel = false;

    //output
    private boolean playerIsDead = false;
    private boolean enemyIsDead = false;


    public ArrayList<GameCharacter> gameCharacters = new ArrayList<>();
    //public ArrayList<EnemyModel> enemyModels = new ArrayList<>();
    public ArrayList<String> enemyNames = new ArrayList<>();

    public BundleToLib(){
        Singleton instance = Singleton.getInstance();
        UserDataFBHandler FBHandler = new UserDataFBHandler(instance.user.getUid());
        //TODO deal with it gameCharacters.add(instance.currentGameCharacter);


    }

    public void initialization(boolean isCoop, ArrayList<GameCharacter> gameCharacters, ArrayList<EnemyModel> enemyModels) {
        this.isCoop = isCoop;
        this.gameCharacters = gameCharacters;
      //  this.enemyModels = enemyModels;
    }




}
