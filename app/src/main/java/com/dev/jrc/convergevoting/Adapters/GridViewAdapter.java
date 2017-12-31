package com.dev.jrc.convergevoting.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.jrc.convergevoting.Models.ListTopics;
import com.dev.jrc.convergevoting.R;

import java.util.List;

/**
 * Created by JohnReinel on 12/31/2017.
 */

public class GridViewAdapter extends ArrayAdapter<ListTopics> {
    public GridViewAdapter(Context context, int resource, List<ListTopics> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(null == v) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_item, null);
        }
        ListTopics product = getItem(position);
        ImageView img = (ImageView) v.findViewById(R.id.topicsImage);
        TextView txtTitle = (TextView) v.findViewById(R.id.topicsTitle);
        TextView txtDescription = (TextView) v.findViewById(R.id.topicsDesc);

        img.setImageResource(product.getImageId());
        txtTitle.setText(product.getTitle());
        txtDescription.setText(product.getDescription());

        return v;
    }
}
