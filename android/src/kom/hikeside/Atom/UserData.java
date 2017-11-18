package kom.hikeside.Atom;

import java.util.ArrayList;

import kom.hikeside.Game.Map.Quest;

/**
 * Created by Koma on 13.08.2017.
 */

public class UserData {
    public String uid;//secondary key

    int money;
    String currentCharacter = null;
    int rank;
    ArrayList<Quest> AcceptedQuests = new ArrayList<>();

    int dailyBonus;//счетчик обнуляется каждый день в 00 00
    //подсчитывает число взятых ивентов
    public UserData(){

    }

    public UserData(String uid, int money, int rank, int dailyBonus) {
        this.uid = uid;
        this.money = money;
        this.rank = rank;
        this.dailyBonus = dailyBonus;
    }

    public String getCurrentCharacter() {
        return currentCharacter;
    }

    public void setCurrentCharacter(String currentCharacter) {
        this.currentCharacter = currentCharacter;
    }

    public int getDailyBonus() {
        return dailyBonus;
    }

    public void setDailyBonus(int dailyBonus) {
        this.dailyBonus = dailyBonus;
    }
}
