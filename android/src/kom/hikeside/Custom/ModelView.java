package kom.hikeside.Custom;

/**
 * Created by Koma on 17.08.2017.
 */

public class ModelView {//View&model object
    String itemModelId;

    String name;
    int amount;

    String picture;

    public ModelView(String itemModelId, String name, int amount) {
        this.itemModelId = itemModelId;
        this.name = name;
        this.amount = amount;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(String itemModelId) {
        this.itemModelId = itemModelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
