package kom.hikeside.Custom.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import kom.hikeside.Atom.Place;
import kom.hikeside.R;

/**
 * Created by Koma on 14.08.2017.
 */

public class MarkAdapter extends ArrayAdapter<Place> {

    private Activity context;
    public List<Place> markList;

    public MarkAdapter(Activity context, List<Place> markList){
        super(context, R.layout.mark_row_list, markList);
        this.context = context;
        this.markList = markList;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View row = inflater.inflate(R.layout.mark_row_list, null, true);

        TextView textViewName = (TextView) row.findViewById(R.id.text_mark_row_name);
        TextView textViewDesc = (TextView) row.findViewById(R.id.text_mark_row_desc);


        Place mark = markList.get(position);

        textViewName.setText(mark.getName());
        textViewDesc.setText(mark.getDescription());

        return row;
    }



}