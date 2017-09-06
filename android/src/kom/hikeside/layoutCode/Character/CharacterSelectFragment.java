package kom.hikeside.layoutCode.Character;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kom.hikeside.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharacterSelectFragment extends Fragment {


    public CharacterSelectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_character_select, container, false);




        return v;
    }

}
