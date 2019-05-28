package com.sim.treasity.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sim.treasity.ComposantsListActivity;
import com.sim.treasity.DrawerActivity;
import com.sim.treasity.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuPrincipalFragment extends Fragment {


    public static final int REQUEST_LOCATION_CODE = 99;



    public MenuPrincipalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_principal,
                container, false);

        checkLocationPermission();
        Button button = (Button) view.findViewById(R.id.cable);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(), ComposantsListActivity.class);
                intent.putExtra("categorie","Composants internes");
                startActivity(intent);
            }
        });

        Button button2 = (Button) view.findViewById(R.id.btn2);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(), ComposantsListActivity.class);
                intent.putExtra("categorie","Entrees et commande");
                startActivity(intent);
            }
        });

        Button button3 = (Button) view.findViewById(R.id.btn3);
        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(), ComposantsListActivity.class);
                intent.putExtra("categorie","Affichages et son");
                startActivity(intent);
            }
        });

        Button button4 = (Button) view.findViewById(R.id.btn4);
        button4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(), ComposantsListActivity.class);
                intent.putExtra("categorie","Senseurs, capteurs, détecteurs");
                startActivity(intent);
            }
        });

        Button button5 = (Button) view.findViewById(R.id.btn5);
        button5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(), ComposantsListActivity.class);
                intent.putExtra("categorie","Moteurs");
                startActivity(intent);
            }
        });
        Button button6 = (Button) view.findViewById(R.id.btn6);
        button6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(), ComposantsListActivity.class);
                intent.putExtra("categorie","Communication");
                startActivity(intent);
            }
        });
        Button button7 = (Button) view.findViewById(R.id.btn7);
        button7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(), ComposantsListActivity.class);
                intent.putExtra("categorie","Circuits intégrés");
                startActivity(intent);
            }
        });
        Button button8 = (Button) view.findViewById(R.id.btn8);
        button8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(), ComposantsListActivity.class);
                intent.putExtra("categorie","Cartes");
                startActivity(intent);
            }
        });
        ((DrawerActivity) getActivity())
                .setActionBarTitle("COMPOSANTS");

        return view;
    }

    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }

            return false;

        }
        else
            return true;
    }


}
