package kom.hikeside.libgdx;

import com.badlogic.gdx.physics.box2d.World;

import kom.hikeside.Game.Objects.GameClasses.GameCharacter;
import kom.hikeside.Game.Objects.GameClasses.GameClass;
import kom.hikeside.libgdx.Entities.TexturedBody;
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
    @Deprecated
    LibraryObjects(World world){
        this.world = world;
    }
    public LibraryObjects(){

    }

    public static GameCharacter getGameCharacter(GameClass gameClass){
        GameCharacter gameCharacter = null;
        switch(gameClass){
            case archer:
                gameCharacter = new GameCharacter("Archer", 1, gameClass, 5, 10, 5, 7, 7, 5);
                break;
            case warrior:
                gameCharacter = new GameCharacter("Warrior", 1, gameClass, 10, 5, 5, 5, 5, 10);
                break;
            case knight:
                gameCharacter = new GameCharacter("Knight", 1, gameClass, 10, 5, 5, 5, 7, 7);
                break;
            case mage:
                gameCharacter = new GameCharacter("Mage", 1, gameClass, 5, 5, 10, 5, 5, 10);
                break;
            case priest:
                gameCharacter = new GameCharacter("Priest", 1, gameClass, 5, 5, 10, 5, 7, 7);
                break;
        }

        return gameCharacter;
    }

    public static Enemy getEnemy(String name){
        Enemy enemy = new Enemy(getEnemyModel("model_1"), "monster_1", getAttackModel("attackModel_1"));

        switch(name){
            case "monster_1":
                enemy = new Enemy(getEnemyModel("model_1"), "monster_1", getAttackModel("attackModel_1"));
            break;
            case "monster_2":
                enemy = new Enemy(getEnemyModel("model_2"), "monster_2", getAttackModel("attackModel_2"));
                break;
            case "monster_3":
                enemy = new Enemy(getEnemyModel("model_3"), "monster_3", getAttackModel("attackModel_1"));
                break;
            case "monster_4":
                enemy = new Enemy(getEnemyModel("model_4"), "monster_4", getAttackModel("attackModel_2"));
                break;
            case "monster_5":
                enemy = new Enemy(getEnemyModel("model_5"), "monster_5", getAttackModel("attackModel_5"));
                break;
            case "monster_6":
                enemy = new Enemy(getEnemyModel("model_6"), "monster_6", getAttackModel("attackModel_4"));
                break;
            case "monster_7":
                enemy = new Enemy(getEnemyModel("model_7"), "monster_7", getAttackModel("attackModel_4"));
                break;
            case "monster_8":
                enemy = new Enemy(getEnemyModel("model_8"), "monster_8", getAttackModel("attackModel_5"));
                break;
        }
        return enemy;
    }

    public static EnemyModel getEnemyModel(String name){
        EnemyModel enemy = new EnemyModel(100, 1, 50, "Sample", "For some reason not initialized", getAbilityModel("ability_1"));

        switch(name){
            case "model_1":
                enemy = new EnemyModel(150, 2, 50, "Golem", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_2":
                enemy = new EnemyModel(40, 1, 0, "Wolf", "Random wolf from forest", getAbilityModel("ability_1"));
                break;
            case "model_3":
                enemy = new EnemyModel(70, 2, 25, "Goblin", "Random goblin from internet", getAbilityModel("ability_1"));
                break;
            case "model_4":
                enemy = new EnemyModel(120, 3, 50, "Lizard", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_5":
                enemy = new EnemyModel(300, 5, 50, "Demon", "666", getAbilityModel("ability_1"));
                break;
            case "model_6":
                enemy = new EnemyModel(100, 2, 50, "Giant Bee", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_7":
                enemy = new EnemyModel(150, 3, 50, "Giant Viper", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_8":
                enemy = new EnemyModel(200, 4, 50, "Fucking Crab", "Insane project of mad wizard", getAbilityModel("ability_1"));
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

    @Deprecated//нужно вводить коорды при создании. это - треш
    public TexturedBody getGameObjectView(String name){
        TexturedBody gameObjectView = null;
        BodyBuilder bodyBuilder = new BodyBuilder(GAME_WIDTH, GAME_HEIGHT, this.world);

        switch(name){
            case "view_1":

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
            case "attackModel_2"://точные но слабые удары
                attackModel = new AttackModel(5, 15, false, 0.9f);
                break;
            case "attackModel_3"://точные но слабые пробивные удары
                attackModel = new AttackModel(5, 15, true, 0.9f);
                break;
            case "attackModel_4"://сильные и редкие удары
                attackModel = new AttackModel(20, 40, false, 0.3f);
                break;
            case "attackModel_5"://пиздецки сильно
                attackModel = new AttackModel(20, 40, true, 0.9f);
                break;
        }
        return attackModel;
    }

}
