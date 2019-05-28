package com.sim.treasity.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sim.treasity.Adapter.ListViewAdapterDictio;
import com.sim.treasity.DrawerActivity;
import com.sim.treasity.Models.Dictio;
import com.sim.treasity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DictionFragment extends Fragment{

    RequestQueue requestQueue;
    ListView listView;
    Button btnA,btnB,btnC,btnD,btnE,btnF,btnG,btnH,btnI,btnJ,btnK,btnL,btnM,btnN,btnO,btnP,btnQ
            ,btnR,btnS,btnT,btnU,btnV,btnW,btnX,btnY,btnZ;


    public DictionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_diction, container, false);

        listView = view.findViewById(R.id.listView2);
        btnA = view.findViewById(R.id.btnA);
        btnB = view.findViewById(R.id.btnB);
        btnC = view.findViewById(R.id.btnC);
        btnD = view.findViewById(R.id.btnD);
        btnE = view.findViewById(R.id.btnE);
        btnF = view.findViewById(R.id.btnF);
        btnG = view.findViewById(R.id.btnG);
        btnH = view.findViewById(R.id.btnH);

        btnI = view.findViewById(R.id.btnI);
        btnJ = view.findViewById(R.id.btnJ);
        btnL = view.findViewById(R.id.btnL);
        btnM = view.findViewById(R.id.btnM);
        btnN = view.findViewById(R.id.btnN);
        btnO = view.findViewById(R.id.btnO);
        btnP = view.findViewById(R.id.btnP);
        btnQ = view.findViewById(R.id.btnQ);
        btnR = view.findViewById(R.id.btnR);
        btnS = view.findViewById(R.id.btnS);

        btnT = view.findViewById(R.id.btnT);
        btnU = view.findViewById(R.id.btnU);
        btnV = view.findViewById(R.id.btnV);
        btnK = view.findViewById(R.id.btnK);
        btnX = view.findViewById(R.id.btnX);
        btnY = view.findViewById(R.id.btnY);
        btnZ = view.findViewById(R.id.btnZ);
        btnW = view.findViewById(R.id.btnW);


        ((DrawerActivity) getActivity())
                .setActionBarTitle("DICTIONNAIRE ANG-FR");

        getDictio("A");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnA.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("A"); } });
        btnB.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("B"); } });
        btnC.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("C"); } });
        btnD.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("D"); } });
        btnE.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("E"); } });
        btnF.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("F"); } });
        btnG.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("G"); } });
        btnH.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("H"); } });

        btnI.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("I"); } });
        btnJ.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("J"); } });
        btnK.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("K"); } });
        btnL.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("L"); } });
        btnM.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("M"); } });
        btnN.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("N"); } });
        btnO.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("O"); } });

        btnP.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("P"); } });
        btnQ.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("Q"); } });
        btnR.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("R"); } });
        btnS.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("S"); } });
        btnT.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("T"); } });
        btnU.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("U"); } });
        btnV.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("V"); } });

        btnW.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("W"); } });
        btnX.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("X"); } });
        btnY.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("Y"); } });
        btnZ.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { getDictio("Z"); } });




    }



    public void getDictio(final String A){

        requestQueue = Volley.newRequestQueue(getActivity());

        String JsonURL = "http://41.226.11.243:10080/treasity/data/dictio.json";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, JsonURL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray users = null;
                        try {

                            users = response.getJSONArray(A);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        List<Dictio> userList = new ArrayList<Dictio>();
                        for (int i = 0; i < users.length(); i++) {
                            try {

                                JSONObject obj = users.getJSONObject(i);
                                Dictio user = new Dictio();
                                user.texte1=obj.getString("texte1");
                                user.texte2=obj.getString("texte2");
                                // adding movie to movies array

                                userList.add(user);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        ListViewAdapterDictio adapter = new ListViewAdapterDictio(userList, getContext());

                        // Setting up all data into ListView.
                        listView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Passing String request into RequestQueue.
        requestQueue.add(req);

    }

}



