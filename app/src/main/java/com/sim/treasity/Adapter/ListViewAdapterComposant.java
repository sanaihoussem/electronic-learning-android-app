package com.sim.treasity.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sim.treasity.Models.Composant;
import com.sim.treasity.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by bechirbelkahla on 12/5/17.
 */

public class ListViewAdapterComposant extends BaseAdapter {

    Context context;

    List<Composant> TempSubjectList;

    public ListViewAdapterComposant(List<Composant> listValue, Context context)
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
        ViewItemm viewItem = null;

        if(convertView == null)
        {
            viewItem = new ViewItemm();

            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInfiater.inflate(R.layout.item_composant, null);



            viewItem.NameTextView = (TextView)convertView.findViewById(R.id.Titre);
            viewItem.image = (ImageView)convertView.findViewById(R.id.imageC);
            viewItem.description = (TextView) convertView.findViewById(R.id.description);

            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItemm) convertView.getTag();
        }



        viewItem.NameTextView.setText(TempSubjectList.get(position).titreComposant);
        viewItem.description.setText(TempSubjectList.get(position).descriptionComposant);

        String urlImage = TempSubjectList.get(position).imageComposant.toString()
                .replace("127.0.0.1",context.getString(R.string.ipAdresseFolder));
        Picasso.with(context).load(urlImage)
                .placeholder(R.drawable.loading)
            .error(R.drawable.loading).into(viewItem.image);

        return convertView;
    }



}

class ViewItemm
{
    ImageView image;
    TextView NameTextView;
    TextView description;
}


