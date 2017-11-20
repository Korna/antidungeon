package kom.hikeside.Game.libgdx;

import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.ArrayList;

import kom.hikeside.Models.LibraryMonsters;
import kom.hikeside.Game.Objects.GameCharacter;
import kom.hikeside.Game.Objects.Inventory.InventoryObject;
import kom.hikeside.Service.Singleton;
import kom.hikeside.Service.UserDataFBHandler;

/**
 * Created by Koma on 08.09.2017.
 */

public class BundleToLib {
    //singleton pattern
    private static volatile BundleToLib instance = new BundleToLib();// TODO look for error descr java.lang.ExceptionInInitializerError
    public static BundleToLib getInstance(){
        return instance;
    }

    //input
    private boolean isCoop = false;
    private boolean isDuel = false;

    //output
    private boolean playerIsDead = false;
    private boolean victory = false;
    private int experience = 0;
    public TaskCompletionSource taskCompletionSource = null;


    public ArrayList<GameCharacter> gameCharacters = new ArrayList<>();

    public ArrayList<LibraryMonsters> enemyNames = new ArrayList<>();

    public ArrayList<InventoryObject> inventoryObjectArrayList = new ArrayList<>();


    public BundleToLib(){
        Singleton instance = Singleton.getInstance();
        UserDataFBHandler FBHandler = new UserDataFBHandler(instance.user.getUid());
        //TODO deal with it gameCharacters.add(instance.currentGameCharacter);


    }




    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public void setNewTask(){
        taskCompletionSource = new TaskCompletionSource();
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
