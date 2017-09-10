package kom.hikeside.libgdx.GameObjects;

import kom.hikeside.libgdx.Entities.GameObjectView;
import kom.hikeside.libgdx.GameMechanics.AttackModel;
import kom.hikeside.libgdx.GameMechanics.EnemyModel;

/**
 * Created by Koma on 08.09.2017.
 */

public class Enemy extends GameObject{

    public EnemyModel enemyModel;


    public Enemy(EnemyModel enemyModel, String basicTesture, AttackModel attackModel) {
        this.enemyModel = enemyModel;
        setCurrentHp(enemyModel.getMaxHp());
        setMaxHp(enemyModel.getMaxHp());
        this.attackModel = attackModel;
    }


    public Enemy(EnemyModel enemyModel, GameObjectView gameObjectView, AttackModel attackModel) {
        this.enemyModel = enemyModel;
        this.view = gameObjectView;
        setCurrentHp(enemyModel.getMaxHp());
        setMaxHp(enemyModel.getMaxHp());
        this.attackModel = attackModel;
    }


}
