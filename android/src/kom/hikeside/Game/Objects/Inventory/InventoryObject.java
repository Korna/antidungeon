package kom.hikeside.Game.Objects.Inventory;

/**
 * Created by Koma on 16.08.2017.
 */

public class InventoryObject {
    String name;
    String description;
   // int weight;
    int levelRequered;
    ObjList.inventory type;

    public InventoryObject(){}

    public InventoryObject(String name, String description, int levelRequered, ObjList.inventory type) {
        this.name = name;
        this.description = description;
        //this.weight = weight;
        this.levelRequered = levelRequered;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String image;//не надо здесь вообще, но пусть будет. поскольку уникальность ограничивается более широким классом. т.е нет смысла держать это поле тут. и даже вообще в этих типах.
    // что то типа линкера потом ввести при отображении, который по enum всё определит

}
