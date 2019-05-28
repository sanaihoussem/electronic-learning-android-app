package com.sim.treasity.Adapter;

/**
 * Created by Juned on 6/17/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sim.treasity.Models.Question;
import com.sim.treasity.R;
import com.sim.treasity.ReponseList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter
{
    Context context  ;
    List<Question> TempSubjectList = new ArrayList<>();
    String idQuestion="zero";


    public ListViewAdapter(List<Question> listValue, Context context)
    {
        this.context = context;
        this.TempSubjectList = listValue;


    }

    @Override
    public int getCount()
    {
            return this.TempSubjectList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.TempSubjectList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewItem viewItem = null;

        if(convertView == null)
        {
            viewItem = new ViewItem();

            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInfiater.inflate(R.layout.item, null);

            viewItem.IdTextView = (TextView)convertView.findViewById(R.id.ID);
            viewItem.NameTextView = (TextView)convertView.findViewById(R.id.Titre);
            viewItem.DescriptionView = (TextView)convertView.findViewById(R.id.textView2);
            viewItem.DateAjoutTextView = (TextView)convertView.findViewById(R.id.txtDate);
            viewItem.userName = (TextView)convertView.findViewById(R.id.userName);
            viewItem.linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout);
            viewItem.profile_image = (ImageView) convertView.findViewById(R.id.profile_image);




            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }
        final Question q=TempSubjectList.get(position);
        viewItem.IdTextView.setText(TempSubjectList.get(position).Question_ID);
        idQuestion = TempSubjectList.get(position).Question_ID;
        viewItem.NameTextView.setText(TempSubjectList.get(position).Question_Titre);
        viewItem.DescriptionView.setText(TempSubjectList.get(position).Question_Description);
        viewItem.DateAjoutTextView.setText(TempSubjectList.get(position).Question_Date_ajout);
        viewItem.userName.setText(TempSubjectList.get(position).id_User);

        String urlImage = TempSubjectList.get(position).User_Image.replace("127.0.0.1",context.getString(R.string.ipAdresseFolder));
        Picasso.with(context).load(urlImage).placeholder(R.drawable.avatar)
                .error(R.drawable.avatar).into(viewItem.profile_image);



        viewItem.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, ReponseList.class);
                intent.putExtra("idQuestion",q.Question_ID);
                context.startActivity(intent);

            }
        });



        return convertView;
    }


}

class ViewItem
{
    TextView IdTextView;
    TextView NameTextView;
    TextView DescriptionView;
    TextView DateAjoutTextView;
    TextView userName;
    ImageView profile_image;
    LinearLayout linearLayout;
}

