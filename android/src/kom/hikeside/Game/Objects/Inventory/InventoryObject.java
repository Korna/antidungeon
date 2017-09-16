package kom.hikeside.Game.Objects.Inventory;

import kom.hikeside.Content.MainItemType;

/**
 * Created by Koma on 16.08.2017.
 */

public class InventoryObject {//TODO: организовать FB названия в виде названий объектов, а не ключей
    String concreteType;//конкретный объект
    MainItemType mainType;//тип объекта
    String modifiedKey;//если есть модификация (на будущее)

    //в рантайме мы будем проверять, не является ли обьект instanceof(armour, weapon итд), чтоб совершать в интерфейсе какие то с ним действия



    public InventoryObject(){}

    public InventoryObject(String name, MainItemType type) {
        this.concreteType = name;
      //  this.description = description;
        //this.weight = weight;

        this.mainType = type;
    }

    public String getConcreteType() {
        return concreteType;
    }

    public void setConcreteType(String name) {
        this.concreteType = name;
    }

    public MainItemType getMainType() {
        return mainType;
    }

    public void setMainType(MainItemType mainType) {
        this.mainType = mainType;
    }
}
