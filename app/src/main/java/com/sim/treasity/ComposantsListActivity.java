package com.sim.treasity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sim.treasity.Adapter.ListViewAdapterComposant;
import com.sim.treasity.Models.Composant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;






public class ComposantsListActivity extends AppCompatActivity {

    ListView listView;
        // Will show the string "data" that holds the results
        TextView results;
    public Context context= this;

        // URL of object to be parsed
        // This string will hold the results
        String data = "";
        // Defining the Volley request queue that handles the URL request concurrently
        RequestQueue requestQueue;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_composants_list);
            // Creates the Volley request queue
            requestQueue = Volley.newRequestQueue(this);
            Intent myIntent = getIntent(); // gets the previously created intent
            String categorie = myIntent.getStringExtra("categorie");
            getSupportActionBar().setTitle(categorie);

            // Casts resuèlts into the TextView found within the main layout XML with id jsonData

            listView = (ListView) findViewById(R.id.listView1);

            // Creating the JsonArrayRequest class called arrayreq, passing the required parameters
            //JsonURL is the URL to be fetched from
                String JsonURL = "http://41.226.11.243:10080/treasity/data/data.json";

                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, JsonURL,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                JSONArray users = null;
                                try {
                                    Intent myIntent = getIntent(); // gets the previously created intent
                                    String categorie = myIntent.getStringExtra("categorie");
                                    users = response.getJSONArray(categorie);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                List<Composant> userList = new ArrayList<Composant>();
                                for (int i = 0; i < users.length(); i++) {
                                    try {

                                        JSONObject obj = users.getJSONObject(i);
                                        Composant user = new Composant();
                                        user.titreComposant=obj.getString("titre");
                                    user.descriptionComposant=obj.getString("description");
                                    user.imageComposant=obj.getString("image");

                                    // adding movie to movies array

                                    userList.add(user);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            ListViewAdapterComposant adapter = new ListViewAdapterComposant(userList,context);

                            // Setting up all data into ListView.
                            listView.setAdapter(adapter);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(ComposantsListActivity.this);

            // Passing String request into RequestQueue.
            requestQueue.add(req);

        }






    }