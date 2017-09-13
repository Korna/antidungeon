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

import static com.badlogic.gdx.math.MathUtils.random;
import static kom.hikeside.Constants.BOSSES_NUMBERS;
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
                enemy = new Enemy(getEnemyModel("model_1"), name, getAttackModel("attackModel_1"));
            break;
            case "monster_2":
                enemy = new Enemy(getEnemyModel("model_2"), name, getAttackModel("attackModel_2"));
                break;
            case "monster_3":
                enemy = new Enemy(getEnemyModel("model_3"), name, getAttackModel("attackModel_1"));
                break;
            case "monster_4":
                enemy = new Enemy(getEnemyModel("model_4"), name, getAttackModel("attackModel_2"));
                break;
            case "monster_5":
                enemy = new Enemy(getEnemyModel("model_5"), name, getAttackModel("attackModel_5"));
                break;
            case "monster_6":
                enemy = new Enemy(getEnemyModel("model_6"), name, getAttackModel("attackModel_4"));
                break;
            case "monster_7":
                enemy = new Enemy(getEnemyModel("model_7"), name, getAttackModel("attackModel_4"));
                break;
            case "monster_8":
                enemy = new Enemy(getEnemyModel("model_8"), name, getAttackModel("attackModel_5"));
                break;
            case "monster_9":
                enemy = new Enemy(getEnemyModel("model_9"), name, getAttackModel("attackModel_3"));
                break;
            case "monster_10":
                enemy = new Enemy(getEnemyModel("model_10"), name, getAttackModel("attackModel_2"));
                break;
            case "monster_11":
                enemy = new Enemy(getEnemyModel("model_11"), name, getAttackModel("attackModel_1"));
                break;
            case "monster_12":
                enemy = new Enemy(getEnemyModel("model_12"), name, getAttackModel("attackModel_2"));
                break;
            case "monster_13":
                enemy = new Enemy(getEnemyModel("model_13"), name, getAttackModel("attackModel_1"));
                break;
            case "monster_14":
                enemy = new Enemy(getEnemyModel("model_14"), name, getAttackModel("attackModel_2"));
                break;
            case "monster_15":
                enemy = new Enemy(getEnemyModel("model_15"), name, getAttackModel("attackModel_3"));
                break;
            case "monster_16":
                enemy = new Enemy(getEnemyModel("model_16"), name, getAttackModel("attackModel_4"));
                break;
            case "monster_17":
                enemy = new Enemy(getEnemyModel("model_17"), name, getAttackModel("attackModel_7"));
                break;
            case "monster_18":
                enemy = new Enemy(getEnemyModel("model_18"), name, getAttackModel("attackModel_2"));
                break;
            case "monster_19":
                enemy = new Enemy(getEnemyModel("model_19"), name, getAttackModel("attackModel_1"));
                break;
            case "monster_20":
                enemy = new Enemy(getEnemyModel("model_20"), name, getAttackModel("attackModel_7"));
                break;
            case "monster_21":
                enemy = new Enemy(getEnemyModel("model_21"), name, getAttackModel("attackModel_2"));
                break;
            case "monster_22":
                enemy = new Enemy(getEnemyModel("model_22"), name, getAttackModel("attackModel_6"));
                break;
        }
        return enemy;
    }

    public static EnemyModel getEnemyModel(String name){
        EnemyModel enemy = new EnemyModel(100, 1, 50, "Sample", "For some reason not initialized", getAbilityModel("ability_1"));

        switch(name){
            case "model_1":
                enemy = new EnemyModel(125, 2, 50, "Golem", "Insane project of mad wizard", getAbilityModel("ability_1"));
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
            case "model_9":
                enemy = new EnemyModel(150, 4, 50, "Griffon", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_10":
                enemy = new EnemyModel(200, 4, 50, "Necromancer", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_11":
                enemy = new EnemyModel(70, 2, 50, "Elephant", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_12":
                enemy = new EnemyModel(100, 2, 50, "Mad Knight", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_13":
                enemy = new EnemyModel(70, 1, 50, "Goblin", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_14":
                enemy = new EnemyModel(250, 2, 50, "Mad Dwarf", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_15":
                enemy = new EnemyModel(150, 2, 50, "Mad Elephant", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_16":
                enemy = new EnemyModel(200, 2, 50, "Ghost", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_17":
                enemy = new EnemyModel(500, 8, 50, "Diamond Golem", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_18":
                enemy = new EnemyModel(50, 1, 50, "Skeleton", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_19":
                enemy = new EnemyModel(66, 1, 50, "Imp", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_20":
                enemy = new EnemyModel(666, 10, 50, "Undead Paladin King", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_21":
                enemy = new EnemyModel(50, 1, 150, "Walking Flesh", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_22":
                enemy = new EnemyModel(200, 2, 50, "Elvish Ghost", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
        }
        return enemy;

    }
    public static boolean isBoss(String monster){
        int[] bosses = BOSSES_NUMBERS;

        int size = bosses.length;
        for(int i = 0; i < size; ++i)
            if(monster.equals("monster_" + bosses[i]))
                return true;
        return false;
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
                attackModel = new AttackModel(5, 15, false, 0.8f);
                break;
            case "attackModel_3"://точные но слабые пробивные удары
                attackModel = new AttackModel(10, 20, true, 0.9f);
                break;
            case "attackModel_4"://сильные и редкие удары
                attackModel = new AttackModel(20, 40, false, 0.3f);
                break;
            case "attackModel_5"://пиздецки сильно
                attackModel = new AttackModel(20, 40, true, 0.9f);
                break;
            case "attackModel_6"://пиздецки сильно 2
                attackModel = new AttackModel(10, 50, true, 0.9f);
                break;
            case "attackModel_7"://пиздецки сильно
                attackModel = new AttackModel(40, 60, true, 0.8f);
                break;
        }
        return attackModel;
    }


}
