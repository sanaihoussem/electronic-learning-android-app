package com.sim.treasity.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.treasity.Constants;
import com.sim.treasity.DrawerActivity;
import com.sim.treasity.Models.Question;
import com.sim.treasity.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {



    TextView username,email,phone,birthdate,gender,nom,prenom;
    Button btnEdit,btnUpload,btnSelect;
    String FinalJSonObject ;
    ImageView profile_image;
    private Bitmap bitmap;






    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profil, container, false);




        username = view.findViewById(R.id.txtUsername);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        birthdate = view.findViewById(R.id.birthdate);
        gender = view.findViewById(R.id.gender);
        nom = view.findViewById(R.id.nom);
        prenom = view.findViewById(R.id.prenom);
        profile_image = view.findViewById(R.id.profile_image);

        ((DrawerActivity) getActivity())
                .setActionBarTitle("PROFIL");
        btnEdit = view.findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfilFragment fragment2 = new EditProfilFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment2);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getProfil();
    }

    public void getProfil(){

        //////////////////////////////////

        String HTTP_URL = "http://"+getString(R.string.ipAdresse)+"/api/getUserPost";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest  =new StringRequest(Request.Method.POST, HTTP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){


                FinalJSonObject = response ;

                // Calling method to parse JSON object.
                new ProfilFragment.ParseJSonDataClass(getContext()).execute();


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();


            }
        })
        {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                String tokenuser = ((Constants) getActivity().getApplication()).getTokenUser();
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





    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {

        public Context context;

        // Creating List of Subject class.
        List<Question> CustomSubjectNamesList;

        public ParseJSonDataClass(Context context) {

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
                       // Question subject;

                        // Defining CustomSubjectNamesList AS Array List.
                        CustomSubjectNamesList = new ArrayList<Question>();
                        for (int i = 0; i < jsonArray.length(); i++) {



                            jsonObject = jsonArray.getJSONObject(i);

                            //Storing ID into subject list.
                            final String usernamee = jsonObject.getString("username");
                            final String emaill = jsonObject.getString("email");
                            final String phonee = jsonObject.getString("numtel");
                            final String birthdatee = jsonObject.getString("dateDeNaissnce");
                            final String genderr = jsonObject.getString("sexe");
                            final String imageprofile = jsonObject.getString("image");
                            final String nomprofile = jsonObject.getString("nom");
                            final String prenomprofile = jsonObject.getString("prenom");


                            getActivity().runOnUiThread(new Runnable()
                            {
                                public void run()
                                {
                                    username.setText(usernamee);
                                    email.setText(emaill);
                                    phone.setText(phonee);
                                    birthdate.setText(birthdatee);
                                    gender.setText(genderr);
                                    nom.setText(nomprofile);
                                    prenom.setText(prenomprofile);
                                    String newUrl = imageprofile.replace("127.0.0.1",getString(R.string.ipAdresseFolder));
                                    Picasso.with(context).load(newUrl).networkPolicy(NetworkPolicy.NO_CACHE)
                                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                                            .placeholder(R.drawable.avatar).error(R.drawable.avatar)
                                            .into(profile_image);

                                }

                            });

                        }


                        //username.setText(usernamee);
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

    }




}
