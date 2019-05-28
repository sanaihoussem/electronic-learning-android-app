package com.sim.treasity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.sim.treasity.fragment.quizFragment;

public class QuizActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        String categorie= intent.getStringExtra("categorie");
        Fragment quizFragment = new quizFragment().newInstance(categorie,0,0);
        replaceFragment(quizFragment);

    }

    public void replaceFragment(Fragment fragment){

        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,fragment).commit();
    }


}



