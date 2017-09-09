package kom.hikeside.libgdx;

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

    private boolean isCoop = false;

    public ArrayList<GameCharacter> gameCharacters = new ArrayList<>();
    public ArrayList<EnemyModel> enemyModels = new ArrayList<>();

    public BundleToLib(){
        Singleton instance = Singleton.getInstance();
        UserDataFBHandler FBHandler = new UserDataFBHandler(instance.user.getUid());
        //TODO deal with it gameCharacters.add(instance.currentGameCharacter);


    }

    public void initialization(boolean isCoop, ArrayList<GameCharacter> gameCharacters, ArrayList<EnemyModel> enemyModels) {
        this.isCoop = isCoop;
        this.gameCharacters = gameCharacters;
        this.enemyModels = enemyModels;
    }




}
