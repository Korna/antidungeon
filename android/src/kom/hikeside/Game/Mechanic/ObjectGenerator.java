package kom.hikeside.Game.Mechanic;

/**
 * Created by Koma on 31.08.2017.
 */

public class ObjectGenerator {//а надо ли отдельно создавать класс для отправки запросов в functions?
    static final double INTERACT_RADIUS = 0.0001;

    MapObjInteracter interacter = new MapObjInteracter(INTERACT_RADIUS * 100);


    public void loadData(){// зависимость от лвла, вещей, предыдущей активности, местоположения итд, а также, находится ли около выбранного дома юзер

    }

    public void initGenerate(){//генерация при загрузке приложения(по идее надо делать server push, чтоб лишь раз в сутки можно было)
        // отправляет айди юзера и время

    }
}
