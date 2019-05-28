package com.sim.treasity.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.sim.treasity.Constants;
import com.sim.treasity.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfilFragment extends Fragment {
    Button btnEdit;
    EditText email,numTel,username,nom,prenom,birthday;
    Spinner sexe;

    Calendar myCalendar = Calendar.getInstance();


    public EditProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_edit_profil, container, false);

        btnEdit = view.findViewById(R.id.btnEdit);
        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id.email);
        numTel = view.findViewById(R.id.numTel);
        nom = view.findViewById(R.id.nom);
        prenom = view.findViewById(R.id.prenom);
        birthday = view.findViewById(R.id.birthday);
        sexe = view.findViewById(R.id.sexe);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sexe, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        sexe.setAdapter(adapter);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthday.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = username.getText().toString();
                String userEmail = email.getText().toString();
                String userMobile = numTel.getText().toString();
                String userPrenom = prenom.getText().toString();
                String userNom = nom.getText().toString();
                String DateDeNaissance = birthday.getText().toString();

                String userSexe = sexe.getSelectedItem().toString();

                String SIGNUP_URL = "http://"+getString(R.string.ipAdresse)+"/api/updateUserAndroid";


                updateUser(SIGNUP_URL, userName, userEmail,userMobile,userPrenom,userNom,DateDeNaissance,userSexe);
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });
    }


    private void updateUser(String signupUrl, final String getuserName, final String getuserEmail, final String getuserMobile, final String getuserPrenom,
                            final String getuserNom, final String userDateDeNaissance, final String userSexe) {



        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest  =new StringRequest(Request.Method.POST, signupUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){


                        Toast.makeText(getActivity(),"user updated",Toast.LENGTH_LONG).show();
                ProfilFragment fragment2 = new ProfilFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment2);
                fragmentTransaction.commit();

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



                param.put("username",getuserName);
                param.put("email", getuserEmail);
                param.put("numtel", getuserMobile);
                param.put("nom", getuserNom);
                param.put("prenom", getuserPrenom);
                param.put("dateDeNaissance", userDateDeNaissance);
                param.put("sexe", userSexe);
                return param;
            }

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


}
