package kom.hikeside.libgdx.GameObjects;

import kom.hikeside.libgdx.Entities.GameObjectView;
import kom.hikeside.libgdx.GameMechanics.AttackModel;
import kom.hikeside.libgdx.GameMechanics.EnemyModel;

/**
 * Created by Koma on 08.09.2017.
 */

public class Enemy {
    public boolean turn = true;
    EnemyModel enemyModel;
    GameObjectView gameObjectView;

    int currentHp;
    int currentMp;
    int currentStamina;

    AttackModel attackModel;

    public Enemy(EnemyModel enemyModel, GameObjectView gameObjectView) {
        this.enemyModel = enemyModel;
        this.gameObjectView = gameObjectView;
    }
}
