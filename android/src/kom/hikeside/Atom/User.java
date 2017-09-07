package kom.hikeside.Atom;

/**
 * Created by Koma on 13.08.2017.
 */

public class User {
    String uid;//secondary key

    int money;

    int rank;

    int dailyBonus;//счетчик обнуляется каждый день в 00 00
    //подсчитывает число взятых ивентов

    public User(String uid, int money, int rank, int dailyBonus) {
        this.uid = uid;
        this.money = money;
        this.rank = rank;
        this.dailyBonus = dailyBonus;
    }

    public int getDailyBonus() {
        return dailyBonus;
    }

    public void setDailyBonus(int dailyBonus) {
        this.dailyBonus = dailyBonus;
    }
}
