package com.sim.treasity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.treasity.Adapter.ListViewAdapter;
import com.sim.treasity.Models.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionList extends AppCompatActivity {

    ListView listView;

    Button button;

    // Server Http URL
    //String HTTP_URL = "http://172.19.2.68/api/getQuestion";

    // String to hold complete JSON response object.
    String FinalJSonObject ;
    String nomUser="test";
    final String idQuestion="zero";


    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);


        listView = (ListView) findViewById(R.id.listView1);
        getSupportActionBar().setTitle("Questions '"+((Constants) QuestionList.this.getApplication()).getidQuestionSaved()+"'");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QuestionList.this, AddQuestionActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAndRemoveTask();
                }
                startActivity(intent);
            }
        });

        Intent myIntent = getIntent(); // gets the previously created intent
        String categorie = myIntent.getStringExtra("categorie");

        //////////////////////////////////

        String HTTP_URL = "http://"+getString(R.string.ipAdresse)+"/api/getQuestionAndroid";
        // Creating StringRequest and set the JSON server URL in here.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, HTTP_URL+"?categorie="+categorie,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("data",response.toString());

                        FinalJSonObject = response ;
                        // Calling method to parse JSON object.
                        new QuestionList.ParseJSonDataClass(QuestionList.this).execute();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(QuestionList.this,"pas de question",Toast.LENGTH_LONG).show();



                    }
                });





        // Creating String Request Object.
        RequestQueue requestQueue = Volley.newRequestQueue(QuestionList.this);

        // Passing String request into RequestQueue.
        requestQueue.add(stringRequest);
        //////////////////////////////////






    }


    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {

        public Context context;

        // Creating List of Subject class.
        List<Question> CustomSubjectNamesList;

        public ParseJSonDataClass(Context context) {

            this.context = context;
        }



        @Override
        protected Void doInBackground(Void... arg0) {

            try {

                // Checking whether FinalJSonObject is not equals to null.
                if (FinalJSonObject != null) {

                    // Creating and setting up JSON array as null.
                    JSONArray jsonArray = null;
                    try {   Log.d("data",FinalJSonObject.toString());
                        // Adding JSON response object into JSON array.
                        jsonArray = new JSONArray(FinalJSonObject);

                        // Creating JSON Object.
                        JSONObject jsonObject;

                        // Creating Subject class object.
                        Question subject;

                        // Defining CustomSubjectNamesList AS Array List.
                        CustomSubjectNamesList = new ArrayList<Question>();

                        for (int i = 0; i < jsonArray.length(); i++) {

                            subject = new Question();

                            jsonObject = jsonArray.getJSONObject(i);

                            //Storing ID into subject list.
                            subject.Question_ID = jsonObject.getString("id");
                            subject.Question_Titre = jsonObject.getString("titre");
                            subject.Question_Categorie = jsonObject.getString("categorie");
                            subject.Question_Description = jsonObject.getString("description");
                            subject.Question_Date_ajout = jsonObject.getString("date_ajout");
                            subject.id_User = jsonObject.getString("user_username");
                            subject.User_Image = jsonObject.getString("user_image");



                            // Adding subject list object into CustomSubjectNamesList.
                            CustomSubjectNamesList.add(subject);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();

                        // the problem is that reading the data received from the server is causing a problem
                        // which prevents the customSubjectNamesList from being initialized
                        CustomSubjectNamesList = new ArrayList<>();
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {


            // After all done loading set complete CustomSubjectNamesList with application context to ListView adapter.
            ListViewAdapter adapter = new ListViewAdapter(CustomSubjectNamesList, context);

            // Setting up all data into ListView.
            listView.setAdapter(adapter);





        }
    }

}
