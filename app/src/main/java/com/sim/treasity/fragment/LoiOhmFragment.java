package com.sim.treasity.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.sim.treasity.DrawerActivity;
import com.sim.treasity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoiOhmFragment extends Fragment {


    private ViewFlipper mViewFlipper;
    private Context mContext;
    private float initialX;

    public LoiOhmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loi_ohm, container, false);

        mViewFlipper =  view.findViewById(R.id.view_flipper);
        ((DrawerActivity) getActivity())
                .setActionBarTitle("COURS");

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {




                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = event.getX();
                        return true;

                    case MotionEvent.ACTION_UP:
                        float finalX = event.getX();
                        if (initialX - finalX>300) {
                            if (mViewFlipper.getDisplayedChild() == 2)
                                break;

                            mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.in_from_left));
                            mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.out_from_left));
                            mViewFlipper.showNext();
                        } else if (finalX - initialX>300) {
                            if (mViewFlipper.getDisplayedChild() == 0)
                                break;

                            mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.in_from_right));
                            mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.out_from_right));
                            mViewFlipper.showPrevious();
                        }
                        break;
                }


                return false;
            }
        });

        return view;
    }






}
