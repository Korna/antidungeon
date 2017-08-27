package kom.hikeside.Atom;

/**
 * Created by Koma on 13.08.2017.
 */

public class User {
    String uid;//secondary key

    int money;

    int level;

    public User(String uid, int money, int level) {
        this.uid = uid;
        this.money = money;
        this.level = level;
    }
}
