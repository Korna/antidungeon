package kom.hikeside.Custom;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import kom.hikeside.R;
import kom.hikeside.Singleton;

/**
 * Created by Koma on 17.08.2017.
 */

public class InventoryAdapter extends ArrayAdapter<ModelView> {

    Activity context;
    public List<ModelView> itemList;

    public InventoryAdapter(Activity context, List<ModelView> itemList) {
        super(context, R.layout.item_inventory, itemList);
        this.context = context;
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View row = inflater.inflate(R.layout.item_inventory, null, true);

        TextView textViewName = (TextView) row.findViewById(R.id.textView_name_build);
        TextView textViewAmount = (TextView) row.findViewById(R.id.textView_item_amount);


        ModelView item = itemList.get(position);

        textViewName.setText(item.getName() + "");
        textViewAmount.setText(item.getAmount() + "");

        ImageView image = (ImageView) row.findViewById(R.id.imageView_item) ;


        try {
            // get input stream
            InputStream ims = getContext().getAssets().open("items/gold.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            image.setImageDrawable(d);
        }
        catch(IOException ex) {
            Log.e("assets", ex.toString());
        }

        return row;
    }

}