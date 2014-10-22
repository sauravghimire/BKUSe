package com.example.saurav.bkuse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Saurav on 10/22/2014.
 */
public class CustomArrayAdapter extends ArrayAdapter<CustomData> {
    private LayoutInflater mInflater;

    public CustomArrayAdapter(Context context, CustomData[] values) {
        super(context, R.layout.custom_data_view, values);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            // Inflate the view since it does not exist
            convertView = mInflater.inflate(R.layout.custom_data_view, parent, false);

            // Create and save off the holder in the tag so we get quick access to inner fields
            // This must be done for performance reasons
            holder = new Holder();
            holder.textView = (TextView) convertView.findViewById(R.id.tvPriceText);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // Populate the text
        holder.textView.setText(getItem(position).getText());

        // Set the color
        convertView.setBackgroundColor(getItem(position).getBackgroundColor());

        return convertView;
    }

    /** View holder for the views we need access to */
    private static class Holder {
        public TextView textView;
    }
}
