package com.sim.treasity.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sim.treasity.Models.quiz;
import com.sim.treasity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link quizFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link quizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class quizFragment extends Fragment {

    public List<quiz> quizs = new ArrayList<>();
    RequestQueue rq;
    static Integer questionIndex =0;
    static Integer score=0;
    static String categorieQuestion="";

    Button btnQuestionNumber,btnQuestionNote,btnQuestionDiff,btnAnswer1,btnAnswer2,btnAnswer3,btnAnswer4,btnNext;
    TextView tvQuestion;

    SharedPreferences results;

    public quizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment quizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static quizFragment newInstance(String param1, Integer param2,Integer param3) {
        quizFragment fragment = new quizFragment();

        categorieQuestion=param1;
        questionIndex = param2;
        score = param3;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_quiz, container, false);

        btnQuestionNumber = view.findViewById(R.id.btnQuestion);
        btnQuestionNote = view.findViewById(R.id.btnNote);
        btnQuestionDiff = view.findViewById(R.id.btnDiff);
        btnAnswer1 = view.findViewById(R.id.btnReponse1);
        btnAnswer2 = view.findViewById(R.id.btnReponse2);
        btnAnswer3 = view.findViewById(R.id.btnReponse3);
        btnAnswer4 = view.findViewById(R.id.btnReponse4);
        tvQuestion = view.findViewById(R.id.tvQuestion);
        btnNext = view.findViewById(R.id.btnNext);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment= null;

                if (questionIndex>= 5){
                   updateProgressBars(categorieQuestion);

                    fragment = new ResultFragment().newInstance(String.valueOf(score));
                }else{
                    fragment = new quizFragment().newInstance(categorieQuestion,questionIndex+1,score);
                }
                replaceFragment(fragment);
            }

            private void updateProgressBars(String categorieQuestion) {
                String  StorageVar="resultresistanceQuiz",newResultResistanceQuiz="0",newResultOhmQuiz="0",newResultCondensateursQuiz="0",
                        newResultTransformateursQuiz="0",newResultCourantQuiz="0";
                switch (categorieQuestion){
                    case "resistance":
                        StorageVar="resultresistanceQuiz";
                        break;
                    case "ohm":
                        StorageVar="resultohmQuiz";
                        break;
                    case "condensateurs":
                        StorageVar="resultcondensateursQuiz";
                        break;
                    case "transformers":
                        StorageVar="resulttransformersQuiz";
                        break;
                    case "tensioncourant":
                        StorageVar="resulttensioncourantQuiz";
                        break;
                }
                //Save result
                results = getActivity().getSharedPreferences("results", MODE_PRIVATE);
                String resultResistanceQuiz = results.getString(StorageVar, null);
                if (resultResistanceQuiz != null){
                    if (Integer.parseInt(resultResistanceQuiz) < score){
                        results.edit().putString(StorageVar,String.valueOf(score)).commit();
                    }
                }else
                {
                    results.edit().putString(StorageVar,String.valueOf(score)).commit();
                }

            }
        });




        btnAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAnswer1.setBackgroundColor(checkAnswer("1"));
                disableButtons();
            }
        });
        btnAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAnswer2.setBackgroundColor(checkAnswer("2"));
                disableButtons();
            }
        });
        btnAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAnswer3.setBackgroundColor(checkAnswer("3"));
                disableButtons();
            }
        });
        btnAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAnswer4.setBackgroundColor(checkAnswer("4"));
                disableButtons();
            }
        });




        return view;
    }

    public int checkAnswer(String answer){
        if (quizs.get(questionIndex).getBonneReponse().equals(answer)){
            score = score + Integer.parseInt(quizs.get(questionIndex).getNote() );
            return Color.GREEN;
        }
        else {
            Toast.makeText(getActivity(),"La bonne réponse est : "+quizs.get(questionIndex).getBonneReponse(),Toast.LENGTH_LONG).show();
            return Color.RED;
        }
    }

    public void disableButtons(){
        btnAnswer1.setClickable(false);
        btnAnswer2.setClickable(false);
        btnAnswer3.setClickable(false);
        btnAnswer4.setClickable(false);
    }

    private void replaceFragment(Fragment fragment){
        getFragmentManager().beginTransaction().replace(R.id.contentFragment,fragment).commit();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getQuestion();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getQuestion()
    {
        rq = Volley.newRequestQueue(this.getContext());
        final List<quiz> quizs2 = new ArrayList<>();


        String JsonURL = "http://41.226.11.243:10080/treasity/data/quiz.json";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, JsonURL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray users = null;
                        try {

                            users = response.getJSONArray(categorieQuestion);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < users.length(); i++) {
                            try {

                                JSONObject obj = users.getJSONObject(i);
                                quiz quizz = new quiz();
                                quizz.setQuestion(obj.getString("question"));
                                quizz.setReponse1(obj.getString("reponse1"));
                                quizz.setReponse2(obj.getString("reponse2"));
                                quizz.setReponse3(obj.getString("reponse3"));
                                quizz.setReponse4(obj.getString("reponse4"));
                                quizz.setBonneReponse(obj.getString("bonnereponse"));
                                quizz.setNote(obj.getString("note"));
                                quizz.setDifficulte(obj.getString("difficulte"));
                                //("here",obj.getString("difficulte"));



                                quizs.add(quizz);




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        btnQuestionNumber.setText("QUESTION "+String.valueOf(questionIndex+1));
                        btnQuestionNote.setText("Noté / "+ quizs.get(questionIndex).getNote());
                        btnQuestionDiff.setText(quizs.get(questionIndex).getDifficulte());
                        btnAnswer1.setText(quizs.get(questionIndex).getReponse1());
                        btnAnswer2.setText(quizs.get(questionIndex).getReponse2());
                        btnAnswer3.setText(quizs.get(questionIndex).getReponse3());
                        btnAnswer4.setText(quizs.get(questionIndex).getReponse4());
                        tvQuestion.setText(quizs.get(questionIndex).getQuestion());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(req);

    }
}
