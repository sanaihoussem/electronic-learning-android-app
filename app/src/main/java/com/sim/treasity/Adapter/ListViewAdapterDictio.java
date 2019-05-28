package com.sim.treasity.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sim.treasity.Models.Dictio;
import com.sim.treasity.R;

import java.util.List;

/**
 * Created by bechirbelkahla on 12/28/17.
 */

public class ListViewAdapterDictio extends BaseAdapter {

    Context context;

    List<Dictio> TempSubjectList;

    public ListViewAdapterDictio(List<Dictio> listValue, Context context)
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
        ViewItemmm viewItem = null;

        if(convertView == null)
        {
            viewItem = new ViewItemmm();

            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInfiater.inflate(R.layout.item_diction, null);



            viewItem.text1 = (TextView)convertView.findViewById(R.id.txt1);
            viewItem.text2 = (TextView)convertView.findViewById(R.id.txt2);

            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItemmm) convertView.getTag();
        }



        viewItem.text1.setText(TempSubjectList.get(position).texte1);
        viewItem.text2.setText(TempSubjectList.get(position).texte2);


        return convertView;
    }
}

class ViewItemmm
{
    TextView text1;
    TextView text2;
}

