package kom.hikeside.Custom.MarkerInfoWindows;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import kom.hikeside.R;

/**
 * Created by Koma on 29.08.2017.
 */

public class FightFragment extends Fragment {
    TextView textViewName;
    TextView textViewType;
    TextView textViewLvl;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.window_fight, container, false);



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Well I don't have any...", Toast.LENGTH_SHORT).show();
            }
        };

        view.findViewById(R.id.tv_button).setOnClickListener(onClickListener);

        textViewName = (TextView) view.findViewById(R.id.tv_lat);
        textViewLvl = (TextView) view.findViewById(R.id.tv_lng);

        fragmentInfo("SampleTitle", "SampleText");
    }

    public void fragmentInfo(String title, String text){

        textViewName.setText(title);
        textViewLvl.setText(text);

    }
}