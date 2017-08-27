package kom.hikeside.Custom;

/**
 * Created by Koma on 19.08.2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kom.hikeside.R;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> implements View.OnClickListener {

    private List<ModelView> items;
    private OnRecyclerViewItemClickListener<ModelView> itemClickListener;
    private int itemLayout;


    public MyRecyclerAdapter(List<ModelView> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(final ViewHolder holder, int position) {
        final ModelView item = items.get(position);
        holder.itemView.setTag(item);
        holder.text.setText(item.getName());



    }

    @Override public int getItemCount() {
        return items.size();
    }

    @Override public void onClick(View view) {
        if (itemClickListener != null) {
            ModelView model = (ModelView) view.getTag();
            itemClickListener.onItemClick(view, model);
        }
    }

    public void add(ModelView item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(ModelView item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<ModelView> listener) {
        this.itemClickListener = listener;
    }

    private static int setColorAlpha(int color, int alpha) {
        return (alpha << 24) | (color & 0x00ffffff);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView_build);
            text = (TextView) itemView.findViewById(R.id.textView_name_build);
        }


    }
}
