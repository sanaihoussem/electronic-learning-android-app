package com.sim.treasity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sim.treasity.DrawerActivity;
import com.sim.treasity.QuizActivity;
import com.sim.treasity.R;

public class HomeQuizFragment extends Fragment {

    Button btnCat1,btnCat2,btnCat3,btnCat4,btnCat5;



    public HomeQuizFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_quiz, container, false);

        btnCat1 = view.findViewById(R.id.btnCat1);
        btnCat2 = view.findViewById(R.id.btnCat2);
        btnCat3 = view.findViewById(R.id.btnCat3);
        btnCat4 = view.findViewById(R.id.btnCat4);
        btnCat5 = view.findViewById(R.id.btnCat5);

        btnCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("categorie","resistance");
                startActivity(intent);
            }
        });

        btnCat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("categorie","ohm");
                startActivity(intent);
            }
        });

        btnCat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("categorie","condensateurs");
                startActivity(intent);
            }
        });

        btnCat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("categorie","transformers");
                startActivity(intent);
            }
        });

        btnCat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("categorie","tensioncourant");
                startActivity(intent);
            }
        });

        ((DrawerActivity) getActivity())
                .setActionBarTitle("QUIZ");

        return view;
    }




}
