package kom.hikeside.Game.Map;

import java.util.ArrayList;

import kom.hikeside.Atom.WebData;
import kom.hikeside.Game.Objects.Inventory.InventoryObject;

/**
 * Created by Koma on 18.09.2017.
 */

public class Quest extends WebData{
    String name;
    String keyOfMark = null;//если это завязано на уничтожении врага
    boolean isCompleted;
    ArrayList<String>  rewardItemsList = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyOfMark() {
        return keyOfMark;
    }

    public void setKeyOfMark(String keyOfMark) {
        this.keyOfMark = keyOfMark;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public ArrayList<String> getRewardItemsList() {
        return rewardItemsList;
    }

    public void setRewardItemsList(ArrayList<String> rewardItemsList) {
        this.rewardItemsList = rewardItemsList;
    }
}
