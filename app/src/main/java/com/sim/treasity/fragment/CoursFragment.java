package com.sim.treasity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewSwitcher;

import com.sim.treasity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoursFragment extends Fragment {

    ViewSwitcher viewSwitcher;
    View myFirstView,mySecondView;
    Button button1;


    public CoursFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cours, container, false);

        viewSwitcher =    view.findViewById(R.id.viewSwitcher1);
        myFirstView = view.findViewById(R.id.view1);
        mySecondView = view.findViewById(R.id.view2);
        button1 = view.findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewSwitcher.getCurrentView() != myFirstView){

                    viewSwitcher.showPrevious();
                } else if (viewSwitcher.getCurrentView() != mySecondView){

                    viewSwitcher.showNext();
                }

            }
        });

        return view;
    }

}
