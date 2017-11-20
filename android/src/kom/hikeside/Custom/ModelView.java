package kom.hikeside.Custom;

import android.graphics.drawable.Drawable;

import kom.hikeside.Models.MainItemType;

/**
 * Created by Koma on 17.08.2017.
 */

public class ModelView {//View&model object
    String itemModelId;

    public String concreteType;
    public MainItemType mainItemType;
    Drawable icon;
    int amount;


    public ModelView(String itemModelId, String concreteType, int amount, Drawable icon, MainItemType mainType) {
        this.itemModelId = itemModelId;
        this.concreteType = concreteType;
        this.amount = amount;
        this.mainItemType = mainType;
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

    public String getConcreteType() {
        return concreteType;
    }

    public void setConcreteType(String name) {
        this.concreteType = name;
    }
}
