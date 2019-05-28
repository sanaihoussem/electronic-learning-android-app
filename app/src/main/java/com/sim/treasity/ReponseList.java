package com.sim.treasity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.treasity.Adapter.ListViewAdapterReponse;
import com.sim.treasity.Models.Reponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReponseList extends AppCompatActivity {

    ListView listView;

    Button button;

    // Server Http URL
    //String HTTP_URL = "http://172.19.2.68/api/getQuestion";

    // String to hold complete JSON response object.
    String FinalJSonObject ;
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reponse_list);

        listView = (ListView) findViewById(R.id.listView1);
        getSupportActionBar().setTitle("Reponses");




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ReponseList.this);
                alert.setTitle("REPONSE");
                alert.setMessage("Ajouter votre reponse ici");

                // Create EditText for entry
                final EditText input = new EditText(ReponseList.this);
                alert.setView(input);

                // Make an "OK" button to save the name
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        // Grab the EditText's input
                        String reponseText = input.getText().toString();



                        String ADD_REPONSE_URL = "http://"+getString(R.string.ipAdresse)+"/api/createReponse";
                        Intent myIntent = getIntent(); // gets the previously created intent
                        String idQuestion = myIntent.getStringExtra("idQuestion");
                        register(ADD_REPONSE_URL, reponseText, idQuestion);
                        }
                });

                // Make a "Cancel" button
                // that simply dismisses the alert
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {}
                });

                alert.show();
            }
        });


        //////////////////////////////////

        String HTTP_URL = "http://"+getString(R.string.ipAdresse)+"/api/getReponseAndroid";
        // Creating StringRequest and set the JSON server URL in here.
        Intent myIntent = getIntent(); // gets the previously created intent
        String idQuestion = myIntent.getStringExtra("idQuestion");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, HTTP_URL+"?idQuestion="+idQuestion,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        FinalJSonObject = response ;

                        // Calling method to parse JSON object.
                        new ReponseList.ParseJSonDataClassReponse(ReponseList.this).execute();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(ReponseList.this,"here",Toast.LENGTH_LONG).show();



                    }
                });





        // Creating String Request Object.
        RequestQueue requestQueue = Volley.newRequestQueue(ReponseList.this);

        // Passing String request into RequestQueue.
        requestQueue.add(stringRequest);
        //////////////////////////////////
    }


    private class ParseJSonDataClassReponse extends AsyncTask<Void, Void, Void> {

        public Context context;

        // Creating List of Subject class.
        List<Reponse> CustomSubjectNamesList;

        public ParseJSonDataClassReponse(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {

                // Checking whether FinalJSonObject is not equals to null.
                if (FinalJSonObject != null) {

                    // Creating and setting up JSON array as null.
                    JSONArray jsonArray = null;
                    try {

                        // Adding JSON response object into JSON array.
                        jsonArray = new JSONArray(FinalJSonObject);

                        // Creating JSON Object.
                        JSONObject jsonObject;

                        // Creating Subject class object.
                        Reponse subject;

                        // Defining CustomSubjectNamesList AS Array List.
                        CustomSubjectNamesList = new ArrayList<Reponse>();

                        for (int i = 0; i < jsonArray.length(); i++) {

                            subject = new Reponse();

                            jsonObject = jsonArray.getJSONObject(i);

                            //Storing ID into subject list.
                            subject.Reponse_ID = jsonObject.getString("id");
                            subject.Reponse_Body = jsonObject.getString("body");
                            subject.Reponse_Date_ajout = jsonObject.getString("date_ajout");
                            subject.id_User = jsonObject.getString("user_username");
                            subject.nbr_vote = jsonObject.getString("nbr_vote");
                            subject.User_Image = jsonObject.getString("user_image");



                            // Adding subject list object into CustomSubjectNamesList.
                            CustomSubjectNamesList.add(subject);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
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
            final ListViewAdapterReponse adapter = new ListViewAdapterReponse(CustomSubjectNamesList, context);

            // Setting up all data into ListView.
            listView.setAdapter(adapter);
           /* ReponseList.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.refreshEvents(CustomSubjectNamesList);
                }});
*/
           // adapter.notifyDataSetChanged();

        }
    }




    private void register(String ADD_REPONSE_URL, final String getReponse, final String getIdQuestion) {



        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest  =new StringRequest(Request.Method.POST, ADD_REPONSE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAndRemoveTask();
                }
                startActivity(getIntent());

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();


            }
        })
        {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String>
                        param = new HashMap<String, String>();

                param.put("body",getReponse);
                param.put("question_id",getIdQuestion);
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                String tokenuser = ((Constants) ReponseList.this.getApplication()).getTokenUser();
                String token= tokenuser;
                String bearer = "Bearer ".concat(token);
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<String, String>();
                headersSys.remove("Authorization");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }





}
