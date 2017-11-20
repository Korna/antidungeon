package kom.hikeside.Models;

import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import kom.hikeside.Game.Objects.BuildStats;
import kom.hikeside.Game.Objects.GameCharacter;
import kom.hikeside.Game.Objects.Inventory.ItemTypesClassess.Armour;

import kom.hikeside.Game.Objects.Inventory.ItemLists.ItemArmor;
import kom.hikeside.Game.libgdx.Entities.TexturedBody;
import kom.hikeside.Game.libgdx.GameMechanics.AbilityModel;
import kom.hikeside.Game.libgdx.GameMechanics.AttackModel;
import kom.hikeside.Game.libgdx.GameMechanics.BodyBuilder;
import kom.hikeside.Game.libgdx.GameMechanics.EnemyModel;
import kom.hikeside.Game.libgdx.GameObjects.Enemy;

import static com.badlogic.gdx.math.MathUtils.random;
import static kom.hikeside.Constants.BOSSES_NUMBERS;

import static kom.hikeside.Models.AttackModels.*;
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
            case Archer:
                gameCharacter = new GameCharacter(gameClass.name(), 1, gameClass, new BuildStats(5, 10, 5, 7, 7, 5));
                break;
            case Warrior:
                gameCharacter = new GameCharacter(gameClass.name(), 1, gameClass, new BuildStats(10, 5, 5, 5, 5, 10));
                break;
            case Knight:
                gameCharacter = new GameCharacter(gameClass.name(), 1, gameClass, new BuildStats(10, 5, 5, 5, 7, 7));
                break;
            case Mage:
                gameCharacter = new GameCharacter(gameClass.name(), 1, gameClass, new BuildStats(5, 5, 10, 5, 5, 10));
                break;
            case Priest:
                gameCharacter = new GameCharacter(gameClass.name(), 1, gameClass, new BuildStats(5, 5, 10, 5, 7, 7));
                break;
        }

        return gameCharacter;
    }
    //есть слабые, средние и сильные монсты
    //слабых можно легко убить
    //средних - с усилиями и без глупостей
    //сильные - только при наличии скилла игрока или крутого перса

    //лвл для них играет роль модификатора характеристик

    public static Enemy getEnemy(LibraryMonsters monsterName){
        String name = monsterName.name();
        Enemy enemy = new Enemy(getEnemyModel("model_1"), "monster_1", getAttackModel(weak_accurate));

        switch(monsterName){
            case Golem:
                enemy = new Enemy(getEnemyModel("model_1"), name, getAttackModel(weak_accurate));
                break;
            case Wolf:
                enemy = new Enemy(getEnemyModel("model_2"), name, getAttackModel(weak_loose_ranged));
                break;
            case Goblin:
                enemy = new Enemy(getEnemyModel("model_3"), name, getAttackModel(weak_accurate));
                break;
            case Lizard:
                enemy = new Enemy(getEnemyModel("model_4"), name, getAttackModel(weak_accurate));
                break;
            case Demon:
                enemy = new Enemy(getEnemyModel("model_5"), name, getAttackModel(strong_accurate));
                break;
            case Bee:
                enemy = new Enemy(getEnemyModel("model_6"), name, getAttackModel(medium_accurate));
                break;
            case Viper:
                enemy = new Enemy(getEnemyModel("model_7"), name, getAttackModel(medium_accurate));
                break;
            case Crab:
                enemy = new Enemy(getEnemyModel("model_8"), name, getAttackModel(strong_accurate));
                break;
            case Griffon:
                enemy = new Enemy(getEnemyModel("model_9"), name, getAttackModel(medium_loose));
                break;
            case Necromancer:
                enemy = new Enemy(getEnemyModel("model_10"), name, getAttackModel(medium_loose));
                break;
            case Elephant:
                enemy = new Enemy(getEnemyModel("model_11"), name, getAttackModel(weak_accurate));
                break;
            case War_Knight:
                enemy = new Enemy(getEnemyModel("model_12"), name, getAttackModel(weak_loose_ranged));
                break;
            case War_Goblin:
                enemy = new Enemy(getEnemyModel("model_13"), name, getAttackModel(weak_accurate));
                break;
            case Dwarf:
                enemy = new Enemy(getEnemyModel("model_14"), name, getAttackModel(medium_loose));
                break;
            case War_Elephant:
                enemy = new Enemy(getEnemyModel("model_15"), name, getAttackModel(medium_accurate));
                break;
            case Ghost:
                enemy = new Enemy(getEnemyModel("model_16"), name, getAttackModel(magic_weak_loose));
                break;
            case Diamond_Golem:
                enemy = new Enemy(getEnemyModel("model_17"), name, getAttackModel(tremendous_accurate));
                break;
            case Skeleton:
                enemy = new Enemy(getEnemyModel("model_18"), name, getAttackModel(weak_accurate));
                break;
            case Imp:
                enemy = new Enemy(getEnemyModel("model_19"), name, getAttackModel(weak_accurate));
                break;
            case Undead_Paladin:
                enemy = new Enemy(getEnemyModel("model_20"), name, getAttackModel(tremendous_accurate));
                break;
            case Mummy:
                enemy = new Enemy(getEnemyModel("model_21"), name, getAttackModel(weak_loose_ranged));
                break;
            case Elvish_Ghost:
                enemy = new Enemy(getEnemyModel("model_22"), name, getAttackModel(magic_medium_accurate));
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
                enemy = new EnemyModel(40, 1, 50, "Wolf", "Random wolf from forest", getAbilityModel("ability_1"));
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
                enemy = new EnemyModel(100, 2, 50, "Bee", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_7":
                enemy = new EnemyModel(150, 3, 50, "Viper", "Insane project of mad wizard", getAbilityModel("ability_1"));
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
                enemy = new EnemyModel(100, 2, 50, "War_Knight", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_13":
                enemy = new EnemyModel(70, 1, 50, "War Goblin", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_14":
                enemy = new EnemyModel(250, 2, 50, "Dwarf", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_15":
                enemy = new EnemyModel(150, 2, 50, "War Elephant", "Insane project of mad wizard", getAbilityModel("ability_1"));
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
                enemy = new EnemyModel(50, 1, 50, "Mummy", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
            case "model_22":
                enemy = new EnemyModel(200, 2, 50, "Elvish Ghost", "Insane project of mad wizard", getAbilityModel("ability_1"));
                break;
        }
        return enemy;

    }
    public static boolean isBoss(LibraryMonsters monster){
        int[] bosses = BOSSES_NUMBERS;
        /*//TODO fix this plox
        int size = bosses.length;
        for(int i = 0; i < size; ++i)
            if(monster.equals("monster_" + bosses[i]))
                return true;
        */
        return false;
    }

    public static AbilityModel getAbilityModel(String name){
        AbilityModel ability = null;// = new AbilityModel();
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

    public static AttackModel getAttackModel(AttackModels name){
        AttackModel attackModel = new AttackModel(10, 15, false, 0.5f);
        switch(name){
            case weak_accurate:
                attackModel = new AttackModel(10, 15, false, 0.7f);
                break;
            case weak_loose_ranged:
                attackModel = new AttackModel(5, 20, false, 0.4f);
                break;
            case medium_accurate:
                attackModel = new AttackModel(20, 40, false, 0.8f);
                break;
            case medium_loose:
                attackModel = new AttackModel(20, 40, false, 0.3f);
                break;
            case strong_accurate:
                attackModel = new AttackModel(20, 40, true, 0.9f);
                break;
            case strong_inaccurate:
                attackModel = new AttackModel(20, 40, true, 0.6f);
                break;
            case strong_accurate_ranged://пиздецки сильно 2
                attackModel = new AttackModel(10, 50, true, 0.9f);
                break;
            case tremendous_accurate://пиздецки сильно
                attackModel = new AttackModel(40, 60, true, 0.8f);
                break;
            case magic_weak_loose://точные но слабые пробивные удары
                attackModel = new AttackModel(10, 20, true, 0.9f);
                break;
            case magic_medium_accurate:
                attackModel = new AttackModel(20, 35, true, 0.9f);
                break;
        }
        return attackModel;
    }

    public static Armour getArmour(String name){
        ArrayList<Armour.ArmorEffect> armorEffects = new ArrayList<>();
        armorEffects.add(Armour.ArmorEffect.reflectAttackEffectToSomeone);
        armorEffects.add(Armour.ArmorEffect.useMpInsteadOfHp);
        Armour armour = new Armour(5, 1, 1, armorEffects);
        switch(name){
            case "armour_1":
                armour = new Armour(ItemArmor.Leather.name(), "dud", 1, MainItemType.Armour, 1, 0, 0.05f, armorEffects);
                break;
            case "armour_2":
                armour = new Armour("Improved Leather", "dud", 1, MainItemType.Armour, 2, 0, 0.05f, armorEffects);
                break;

        }
        return armour;
    }

    //ranged - разброс дисперсии
    //magic - пробивает броню, игнорирует её
    //weak
    //medium
    //strong
    //tremendous
}