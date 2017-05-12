package com.example.sammy.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sammy on 4/14/2017.
 */


public class UserAdapter extends ArrayAdapter {

    public UserAdapter(Context context, ArrayList<String[]> users) {
        super(context, 0, users);
    }

    /**
     * Creates an adapter for the listview to interpret.
     * The listview only shows the title of a given event.
     * @param position
     * @param convertView
     * @param parent
     * @return convertView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String[] user = (String[]) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_data, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.list_item_event);
        // Populate the data into the template view using the data object
        tvName.setText(user[0]);
        // Return the completed view to render on screen
        return convertView;
    }

}
