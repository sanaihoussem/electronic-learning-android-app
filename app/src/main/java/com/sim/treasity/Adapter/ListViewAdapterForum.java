package com.sim.treasity.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sim.treasity.Constants;
import com.sim.treasity.Models.Categorie;
import com.sim.treasity.QuestionList;
import com.sim.treasity.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by bechirbelkahla on 12/5/17.
 */

public class ListViewAdapterForum extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Categorie> data= Collections.emptyList();
    Categorie current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public ListViewAdapterForum(Context context, List<Categorie> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_list_forum, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        final Categorie current=data.get(position);
        myHolder.textFishName.setText(current.nomCategorie);
        if(current.urlImage.equals("electronique"))
            myHolder.imageTopic.setImageResource(R.drawable.electronique);
        if(current.urlImage.equals("robotique"))
            myHolder.imageTopic.setImageResource(R.drawable.robotique);
        if(current.urlImage.equals("domotique"))
            myHolder.imageTopic.setImageResource(R.drawable.domotique);
        if(current.urlImage.equals("code"))
            myHolder.imageTopic.setImageResource(R.drawable.code);
        if(current.urlImage.equals("logiciels"))
            myHolder.imageTopic.setImageResource(R.drawable.logiciels);

        // load image into imageview using glide

        ((MyHolder) holder).textFishName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, current.nomCategorie.toString() , Toast.LENGTH_SHORT).show();

            }
        });

        ((MyHolder) holder).imageTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Constants) ((Activity) context).getApplication()).setidQuestionSaved(current.nomCategorie.toString());
                Intent intent=new Intent(context, QuestionList.class);
                intent.putExtra("categorie",current.nomCategorie.toString());
                context.startActivity(intent);

            }
        });

    }



    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView textFishName;
        ImageView imageTopic;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textFishName= (TextView) itemView.findViewById(R.id.ListNom);
            imageTopic= (ImageView) itemView.findViewById(R.id.imageTopic);
        }




    }


}
