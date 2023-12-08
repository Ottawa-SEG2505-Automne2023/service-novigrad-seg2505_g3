package com.example.projetseg2505;

import android.app.Activity;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class LocationListAdapter extends ArrayAdapter<Location> {

    private Activity context;
    private SparseBooleanArray selectedItems;
    List<Location> locations;
    public LocationListAdapter(Activity context, List<Location> locations){
        //constructor
        super(context, R.layout.layout_service_list, locations);
        this.context = context;
        this.locations = locations;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_account_list, null, true);

        TextView textViewUsername = (TextView) listViewItem.findViewById(R.id.textViewUsername);

        Location location = locations.get(position);
        textViewUsername.setText(location.getName());
        return listViewItem;

    }
}

