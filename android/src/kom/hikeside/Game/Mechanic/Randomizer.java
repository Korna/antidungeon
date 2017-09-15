package kom.hikeside.Game.Mechanic;

import android.util.Log;

import com.badlogic.gdx.utils.Array;

import kom.hikeside.Content.LibraryMonsters;
import kom.hikeside.Game.MapView;
import kom.hikeside.Game.Objects.MapViewPriority;
import kom.hikeside.Game.Objects.Inventory.ObjList;
import kom.hikeside.libgdx.GameMechanics.AttackModel;
import kom.hikeside.Content.LibraryObjects;

import static com.badlogic.gdx.math.MathUtils.random;
import static kom.hikeside.Constants.BOSSES_NUMBERS;
import static kom.hikeside.Constants.OBJECT_ATTACK;
import static kom.hikeside.Constants.OBJECT_DEFENCE;
import static kom.hikeside.Constants.OBJECT_HEAL;

/**
 * Created by Koma on 04.09.2017.
 */

public class Randomizer {

    public static MapView getSimpleObject(){
        int randomIndex = random.nextInt(MapView.values().length);

        return MapView.values() [randomIndex];
    }

    public static MapView getLayerObject(int layerLevel){
        Array<MapView> list = new MapViewPriority().priority_1();

        int randomIndex = random.nextInt(list.size);


        return list.get(randomIndex);
    }


    public static ObjList.inventory getSimpleItem(){
        int randomIndex = random.nextInt(ObjList.inventory.values().length);

        return ObjList.inventory.values() [randomIndex];
    }

    public static int getAttackValue(AttackModel model){
        float chance = random.nextFloat();
        if(chance < model.chanceToHit)
            return random.nextInt(model.highestDamage - model.lowestDamage) + model.lowestDamage;
        else
            return 0;
    }

    public static String[] battleFieldTexture(){
        int randomNumber = random.nextInt(9) + 1;
        switch(randomNumber){
            case 1:
                return new String[]{"grass_1", "wall"};
            case 2:
                return new String[]{"brown_stone", "orange_brick"};
            case 3:
                return new String[]{"orange_brick", "brown_stone"};
            case 4:
                return new String[]{"village_1_down", "village_1_up"};
            case 5:
                return new String[]{"grass_2", "castle"};
            case 6:
                return new String[]{"brown_stone", "dungeon"};
            case 7:
                return new String[]{"village_1_down", "village_1_up"};
            case 8:
                return new String[]{"village_2_down", "village_2_up"};
            case 9:
                return new String[]{"mountains_1_down", "mountains_1_up"};
            default:
                return new String[]{"grass", "forest"};
        }


    }

    public static LibraryMonsters simpleMonster(){
        int randomIndex = random.nextInt(LibraryMonsters.values().length);

        return LibraryMonsters.values() [randomIndex];
    }

    public static String simpleBoss(){//TODO FIX THIS TOO
        int[] bosses = BOSSES_NUMBERS;
        int size = bosses.length;
        int index = random.nextInt(size);
        return "monster_" + bosses[index];
    }

    public static String action(){
        String action = "";

        int NUMBER_OF_CASES = 3;
        int agressivePoints = 5;//число псевдовариантов // attack

        int randomNumber = random.nextInt(NUMBER_OF_CASES + agressivePoints) + 1;

        if(randomNumber > NUMBER_OF_CASES){
            Log.d("randomizer", "agressive attack");
            action = OBJECT_ATTACK;//используется как agressive attack
            return action;
        }

        switch(randomNumber){
            case 1:
                action = OBJECT_ATTACK;
                break;
            case 2:
                action = OBJECT_HEAL;
                break;
            case 3:
                action = OBJECT_DEFENCE;
                break;
            default:
                Log.w("Action", " not fiund");
                break;
        }
        Log.d("randomizer:", action);
        return action;
    }

}
