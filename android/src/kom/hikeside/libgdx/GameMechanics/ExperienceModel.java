package kom.hikeside.libgdx.GameMechanics;

import java.util.ArrayList;

import kom.hikeside.libgdx.GameMechanics.EnemyModel;

/**
 * Created by Koma on 07.09.2017.
 */

public class ExperienceModel {//опыт начисляется после битвы и рахдается участникам

    int numberOfPlayers;
    ArrayList<EnemyModel> listOfEnemies;



    public int giveExpFromAll(){
        int sum = 0;
        for(EnemyModel object : listOfEnemies)
            sum += object.getExperience();

        sum /= numberOfPlayers;

        return sum;
    }
}
