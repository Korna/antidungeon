package kom.hikeside.libgdx.GameObjects;

import java.util.concurrent.TimeUnit;

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

    @Override
    public void ActionMove(){
        final float speed = -100;
        final int delay = 300;
        new Thread()
        {
            public void run() {
                view.getBody().setLinearVelocity(speed, 0);
                try {
                    TimeUnit.MILLISECONDS.sleep(delay);
                }catch(InterruptedException e){

                }
                view.getBody().setLinearVelocity(-speed, 0);
                try {
                    TimeUnit.MILLISECONDS.sleep(delay);
                }catch(InterruptedException e){

                }
                view.getBody().setLinearVelocity(0,0);
                this.interrupt();
            }
        }.start();


    }


}
