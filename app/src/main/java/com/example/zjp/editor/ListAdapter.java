package com.example.zjp.editor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zjp on 15-10-13.
 */
public class ListAdapter extends ArrayAdapter<Listitem>{
    private int resourceId;
    public ListAdapter(Context context,int textViewResourceId,List<Listitem> objects){
        super(context,textViewResourceId, objects);
        resourceId=textViewResourceId;
    }
    public View getView(int position,View convertView,ViewGroup parent){
        Listitem item=getItem(position);
        View view;
        if(convertView == null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);

        } else{
            view=convertView;
        }
        TextView textView=(TextView)view.findViewById(R.id.item);
        textView.setText(item.getContent());
        return view;
    }
}
