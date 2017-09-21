package kom.hikeside.Game.Map;

import java.util.ArrayList;

/**
 * Created by Koma on 19.09.2017.
 */

public class MapHandler {



    public static boolean includesId(String id, ArrayList<String> list){
        for(String record : list){
            if(record.equals(id))
                return true;
        }

        return false;
    }

}
