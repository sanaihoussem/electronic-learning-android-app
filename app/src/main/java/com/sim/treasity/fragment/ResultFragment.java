package com.sim.treasity.fragment;


import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sim.treasity.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

    static String score,scoreResistance,scoreOhm,scoreCondensateurs,scoreTransformateurs,scoreCourant;

    Button btnRedoQ1,btnRedoQ2,btnRedoQ3,btnRedoQ4,btnRedoQ5,btnReset,btnHome;
    ProgressBar progressBarResistance,progressBarOhm,progressBarCondensateurs,progressBarTransformateurs,progressBarCourant;
    TextView tvScore;

    SharedPreferences results;


    public ResultFragment() {
        // Required empty public constructor
    }

    public static ResultFragment newInstance(String param1) {
        ResultFragment fragment = new ResultFragment();

        score = param1;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        progressBarResistance = view.findViewById(R.id.progressBarQ1);
        progressBarOhm = view.findViewById(R.id.progressBarQ2);
        progressBarCondensateurs = view.findViewById(R.id.progressBarQ3);
        progressBarTransformateurs = view.findViewById(R.id.progressBarQ4);
        progressBarCourant = view.findViewById(R.id.progressBarQ5);


        btnRedoQ1 = view.findViewById(R.id.redoQ1);
        btnRedoQ2 = view.findViewById(R.id.redoQ2);
        btnRedoQ3 = view.findViewById(R.id.redoQ3);
        btnRedoQ4 = view.findViewById(R.id.redoQ4);
        btnRedoQ5 = view.findViewById(R.id.redoQ5);
        btnReset = view.findViewById(R.id.btnReset);
        btnHome = view.findViewById(R.id.btnHome);
        tvScore = view.findViewById(R.id.tvScore);


        results = getActivity().getSharedPreferences("results", MODE_PRIVATE);
        String resultResistanceQuiz = results.getString("resultresistanceQuiz", null);
        String resultOhmQuiz = results.getString("resultohmQuiz", null);
        String resultCondensateursQuiz = results.getString("resultcondensateursQuiz", null);
        String resultTransformateursQuiz = results.getString("resulttransformersQuiz", null);
        String resultCourantQuiz = results.getString("resulttensioncourantQuiz", null);

        if (resultResistanceQuiz != null){ scoreResistance = resultResistanceQuiz; } else { scoreResistance = "0" ;  }
        if (resultOhmQuiz != null){ scoreOhm = resultOhmQuiz; } else { scoreOhm = "0" ;  }
        if (resultCondensateursQuiz != null){ scoreCondensateurs = resultCondensateursQuiz; } else { scoreCondensateurs = "0" ;  }
        if (resultTransformateursQuiz != null){ scoreTransformateurs = resultTransformateursQuiz; } else { scoreTransformateurs = "0" ;  }
        if (resultCourantQuiz != null){ scoreCourant = resultCourantQuiz; } else { scoreCourant = "0" ;  }

        progressBarResistance.setProgress(Integer.parseInt(scoreResistance));
        progressBarOhm.setProgress(Integer.parseInt(scoreOhm));
        progressBarCondensateurs.setProgress(Integer.parseInt(scoreCondensateurs));
        progressBarTransformateurs.setProgress(Integer.parseInt(scoreTransformateurs));
        progressBarCourant.setProgress(Integer.parseInt(scoreCourant));



        btnRedoQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              quizFragment  fragment = new quizFragment().newInstance("resistance",0,0);
              replaceFragment(fragment);
            }
        });

        btnRedoQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizFragment  fragment = new quizFragment().newInstance("ohm",0,0);
                replaceFragment(fragment);
            }
        });
        btnRedoQ3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizFragment  fragment = new quizFragment().newInstance("condensateurs",0,0);
                replaceFragment(fragment);            }
        });
        btnRedoQ4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizFragment  fragment = new quizFragment().newInstance("transformers",0,0);
                replaceFragment(fragment);            }
        });
        btnRedoQ5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizFragment  fragment = new quizFragment().newInstance("tensioncourant",0,0);
                replaceFragment(fragment);            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = results.edit();
                editor.putString("resultresistanceQuiz", "0");
                editor.putString("resultohmQuiz" , "0");
                editor.putString("resultcondensateursQuiz" , "0");
                editor.putString("resulttransformersQuiz" , "0");
                editor.putString("resulttensioncourantQuiz" , "0");
                editor.apply();
                Toast.makeText(getActivity(),"Resultats remis à zero",Toast.LENGTH_SHORT).show();


                replaceFragment(new ResultFragment());

            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getActivity().finishAndRemoveTask();
                }
                replaceFragment(new ResultFragment());
            }
        });

        tvScore.setText("Score ==> "+score +" points");


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBarResistance.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.background), PorterDuff.Mode.SRC_IN );

    }

    private void replaceFragment(Fragment fragment){
        getFragmentManager().beginTransaction().replace(R.id.contentFragment,fragment).commit();
    }
}
