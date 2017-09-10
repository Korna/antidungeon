package kom.hikeside.libgdx;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import kom.hikeside.libgdx.Entities.GameObjectView;
import kom.hikeside.libgdx.GameMechanics.AbilityModel;
import kom.hikeside.libgdx.GameMechanics.AttackModel;
import kom.hikeside.libgdx.GameMechanics.BodyBuilder;
import kom.hikeside.libgdx.GameMechanics.EnemyModel;
import kom.hikeside.libgdx.GameObjects.Enemy;

import static kom.warside.LibgdxGame.GAME_HEIGHT;
import static kom.warside.LibgdxGame.GAME_WIDTH;
/**
 * Created by Koma on 10.09.2017.
 */

public class LibraryObjects {
    World world;
    LibraryObjects(World world){
        this.world = world;
    }

    public static Enemy getEnemy(String name){
        Enemy enemy = new Enemy(getEnemyModel("model_1"), "monster_1", getAttackModel("attackModel_1"));

        switch(name){
            case "monster_1":
                enemy = new Enemy(getEnemyModel("model_1"), "monster_1", getAttackModel("attackModel_1"));
            break;
        }
        return enemy;
    }

    public static EnemyModel getEnemyModel(String name){
        EnemyModel enemy = new EnemyModel(100, 1, 50, "Sample", "For some reason not initialized", getAbilityModel("ability_1"));

        switch(name){
            case "model_1":
                enemy = new EnemyModel(100, 1, 50, "Golem", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
        }
        return enemy;

    }

    public static AbilityModel getAbilityModel(String name){
        AbilityModel ability = new AbilityModel();

        switch(name){
            case "ability_1":
                ability = new AbilityModel();
                break;
        }
        return ability;
    }

    public GameObjectView getGameObjectView(String name){
        GameObjectView gameObjectView = null;
        BodyBuilder bodyBuilder = new BodyBuilder(GAME_WIDTH, GAME_HEIGHT, this.world);

        switch(name){
            case "ability_1":

                break;
        }

        return  gameObjectView;
    }

    public static AttackModel getAttackModel(String name){
        AttackModel attackModel = new AttackModel(10, 15, false, 0.5f);
        switch(name){
            case "attackModel_1":
                attackModel = new AttackModel(10, 15, false, 0.5f);
                break;
            case "attackModel_2":
                attackModel = new AttackModel(5, 15, false, 0.9f);
                break;
            case "attackModel_3":
                attackModel = new AttackModel(20, 40, false, 0.3f);
                break;
            case "attackModel_4":
                attackModel = new AttackModel(20, 40, false, 0.9f);
                break;
        }
        return attackModel;
    }

}
