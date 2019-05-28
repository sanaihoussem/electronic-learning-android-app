package com.sim.treasity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class AddQuestionActivity extends AppCompatActivity {



    EditText titre,description;
    String titre2,description2;
    TextView cat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        titre = (EditText) findViewById(R.id.EtTitre);
        description = (EditText) findViewById(R.id.EtDescription);
        cat = (TextView) findViewById(R.id.tv_categorieQuestion);
        cat.setText(((Constants) AddQuestionActivity.this.getApplication()).getidQuestionSaved());
        getSupportActionBar().setTitle("Ajouter une question");

    }



    public void addAction(View view) {
        titre2 = titre.getText().toString();
        description2 = description.getText().toString();

        String ADD_QUESTION_URL = "http://"+getString(R.string.ipAdresse)+"/api/createQuestion";


            if (titre2.equalsIgnoreCase("")){

                titre.setError("Titre obligatoire");
            }
            else if(description2.equalsIgnoreCase("")){

                description.setError("Description obligatoire");
            }


        else{

                register(ADD_QUESTION_URL, titre2, description2);
        }


    }

        private void register(String ADD_QUESTION_URL, final String getTitre, final String getDescription) {



            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest stringRequest  =new StringRequest(Request.Method.POST, ADD_QUESTION_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response){


                            Toast.makeText(getApplicationContext(),"added",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(AddQuestionActivity.this, QuestionList.class);
                    intent.putExtra("categorie",((Constants) AddQuestionActivity.this.getApplication()).getidQuestionSaved());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finishAndRemoveTask();
                    }
                    startActivity(intent);



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

                    Charset charset = Charset.forName("UTF-8");


                    param.put("titre",charset.decode(charset.encode(getTitre)).toString());
                    param.put("description",charset.decode(charset.encode(getDescription)).toString());
                    param.put("categorie", ((Constants) AddQuestionActivity.this.getApplication()).getidQuestionSaved());
                    return param;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    String tokenuser = ((Constants) AddQuestionActivity.this.getApplication()).getTokenUser();
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

