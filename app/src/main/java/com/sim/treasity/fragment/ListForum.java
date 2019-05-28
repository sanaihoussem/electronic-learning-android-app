package com.sim.treasity.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sim.treasity.Adapter.ListViewAdapterForum;
import com.sim.treasity.DrawerActivity;
import com.sim.treasity.Models.Categorie;
import com.sim.treasity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListForum extends Fragment {

    private RecyclerView mRVFishPrice;
    private ListViewAdapterForum mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<Categorie> data = new ArrayList<>();
    public Categorie categorie = new Categorie();
    public Categorie categorie2 = new Categorie();
    public Categorie categorie3 = new Categorie();
    public Categorie categorie4 = new Categorie();
    public Categorie categorie5 = new Categorie();
    public Categorie categorie6 = new Categorie();

    public ListForum() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_forum,
                container, false);
        mRecyclerView = view.findViewById(R.id.fishPriceList);
        mLayoutManager = new LinearLayoutManager(getActivity());





        categorie.nomCategorie = ("electronique");
        categorie.urlImage = ("electronique");
        categorie2.nomCategorie = ("robotique");
        categorie2.urlImage = ("robotique");
        categorie3.nomCategorie = ("domotique");
        categorie3.urlImage = ("domotique");
        categorie4.nomCategorie = ("code");
        categorie4.urlImage = ("code");
        categorie5.nomCategorie = ("logiciels");
        categorie5.urlImage = ("logiciels");
        data.add(categorie);
        data.add(categorie2);
        data.add(categorie3);
        data.add(categorie4);
        data.add(categorie5);
        int x=data.size();


//        use this in case of gridlayoutmanager
//        mLayoutManager = new GridLayoutManager(this,2);

//        use this in case of Staggered GridLayoutManager
//        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);


        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new ListViewAdapterForum(getContext(), data);
        mRecyclerView.setAdapter(mAdapter);
        //mRVFishPrice.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        ((DrawerActivity) getActivity())
                .setActionBarTitle("FORUM");






        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter.notifyDataSetChanged();


    }




}
