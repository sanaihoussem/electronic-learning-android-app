package com.sim.treasity.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.treasity.Models.Reponse;
import com.sim.treasity.R;
import com.sim.treasity.ReponseList;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by bechirbelkahla on 12/9/17.
 */

public class ListViewAdapterReponse extends BaseAdapter {
    Context context  ;
    List<Reponse> TempSubjectList;
    String nomUser="test";
    RequestQueue requestQueue;


    public ListViewAdapterReponse(List<Reponse> listValue, Context context)
    {
        this.context = context;
        this.TempSubjectList = listValue;
        requestQueue = Volley.newRequestQueue(this.context);


    }

    @Override
    public int getCount()
    {
        return this.TempSubjectList.size();
    }

    public void refreshEvents(List<Reponse> TempSubjectList) {
        this.TempSubjectList.clear();
        this.TempSubjectList.addAll(TempSubjectList);
        notifyDataSetChanged();
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
        ViewItemReponse viewItem = null;

        if(convertView == null)
        {
            viewItem = new ViewItemReponse();

            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInfiater.inflate(R.layout.item_reponse, null);

            viewItem.IdTextView = (TextView)convertView.findViewById(R.id.ID);
            viewItem.NameTextView = (TextView)convertView.findViewById(R.id.Titre);
            viewItem.DateAjoutTextView = (TextView)convertView.findViewById(R.id.txtDate);
            viewItem.userName = (TextView)convertView.findViewById(R.id.userName);
            viewItem.voteImg = (ImageView) convertView.findViewById(R.id.voteImg);
            viewItem.profile_image = (ImageView) convertView.findViewById(R.id.profile_imagee);



            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItemReponse) convertView.getTag();
        }
        final Reponse q=TempSubjectList.get(position);
        viewItem.IdTextView.setText(q.Reponse_ID);
        viewItem.NameTextView.setText(q.Reponse_Body);
        viewItem.DateAjoutTextView.setText(q.Reponse_Date_ajout);
        viewItem.userName.setText(q.id_User);


        String urlImage = TempSubjectList.get(position).User_Image.
                replace("127.0.0.1",context.getString(R.string.ipAdresseFolder));
        Picasso.with(context).load(urlImage).placeholder(R.drawable.avatar)
                .error(R.drawable.avatar).into(viewItem.profile_image);


        if(q.nbr_vote.equals("1"))
        {
            viewItem.voteImg.setImageResource(R.drawable.checkvert);

        }
        else
            viewItem.voteImg.setImageResource(R.drawable.checkgrey);


        viewItem.voteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////////////////////////////////

                String HTTP_URL = "http://"+context.getString(R.string.ipAdresse)+"/api/updateReponse";
                // Creating StringRequest and set the JSON server URL in here.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, HTTP_URL+"?id="+q.Reponse_ID+"&nbr_vote="+1,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                Intent myIntent = ((Activity) context).getIntent(); // gets the previously created intent
                                String idQuestion = myIntent.getStringExtra("idQuestion");
                                Intent intent=new Intent(context, ReponseList.class);
                                intent.putExtra("idQuestion",idQuestion);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    ((Activity) context).finishAndRemoveTask();
                                }
                                context.startActivity(intent);





                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                // Showing error message if something goes wrong.
                                Toast.makeText(context,"here",Toast.LENGTH_LONG).show();



                            }
                        });





                // Creating String Request Object.
                RequestQueue requestQueue = Volley.newRequestQueue(context);

                // Passing String request into RequestQueue.
                requestQueue.add(stringRequest);
                //////////////////////////////////

            }
        });


        return convertView;
    }


}

class ViewItemReponse
{
    TextView IdTextView;
    TextView NameTextView;
    TextView DateAjoutTextView;
    TextView userName;
    ImageView voteImg;
    ImageView profile_image;
}

