package kom.hikeside.libgdx.GameObjects;

import kom.hikeside.libgdx.Entities.TexturedBody;
import kom.hikeside.libgdx.GameMechanics.AttackModel;
import kom.hikeside.libgdx.GameMechanics.EnemyModel;

/**
 * Created by Koma on 08.09.2017.
 */

public class Enemy extends GameObject{

    public EnemyModel enemyModel;


    public Enemy(EnemyModel enemyModel, String basicTexture,

                 AttackModel attackModel// TODO переместить в enemyModel
                 //создать класс attackView
    ) {
        this.enemyModel = enemyModel;
        setCurrentHp(enemyModel.getMaxHp());
        setMaxHp(enemyModel.getMaxHp());
        this.attackModel = attackModel;
        this.basicTexture = basicTexture;
    }
    public void setGameObjectView(TexturedBody gameObjectView){
        this.view = gameObjectView;
    }


    public Enemy(EnemyModel enemyModel, TexturedBody gameObjectView, AttackModel attackModel) {
        this.enemyModel = enemyModel;
        this.view = gameObjectView;
        setCurrentHp(enemyModel.getMaxHp());
        setMaxHp(enemyModel.getMaxHp());
        this.attackModel = attackModel;
    }


}
