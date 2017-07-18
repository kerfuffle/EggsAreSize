package net.kerfuffle.eggsaresize;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by rdavis on 7/16/2017.
 */

public class DailyDetailFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ScrollView scroll = new ScrollView(getActivity());



        return inflater.inflate(R.layout.main, container, false);
    }


}
