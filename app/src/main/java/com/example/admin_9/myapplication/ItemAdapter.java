package com.example.admin_9.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class ItemAdapter extends ArrayAdapter
{
    private Context context;
    private List items;
    private LayoutInflater inflater;


    public ItemAdapter(@NonNull Context context,  @NonNull List items) {
        super(context, 0, items);
        this.context=context;
        this.items=items;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v=inflater.inflate(R.layout.list_item,null);

        TextView name=v.findViewById(R.id.name);
        TextView id=v.findViewById(R.id.id);
        TextView color=v.findViewById(R.id.color);

        Map map= (Map) items.get(position);
        name.setText(map.get("name").toString());
        id.setText(map.get("id").toString());
        color.setText(map.get("color").toString());

        return v;

    }
}
