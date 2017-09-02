package kom.hikeside.Atom;

/**
 * Created by Koma on 13.08.2017.
 */

public class User {
    String uid;//secondary key

    int money;

    int level;

    int dailyBonus;//счетчик обнуляется каждый день в 00 00
    //подсчитывает число взятых ивентов

    public User(String uid, int money, int level, int dailyBonus) {
        this.uid = uid;
        this.money = money;
        this.level = level;
        this.dailyBonus = dailyBonus;
    }

    public int getDailyBonus() {
        return dailyBonus;
    }

    public void setDailyBonus(int dailyBonus) {
        this.dailyBonus = dailyBonus;
    }
}
